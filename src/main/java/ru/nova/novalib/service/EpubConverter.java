package ru.nova.novalib.service;

import antlr.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Chapter;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
@Component
public class EpubConverter {

    @Autowired
    private AuthorService authorService;

    @Value("${upload.path}")
    private String uploadPath;
    @Value("${upload.posterFile.path}")
    private String uploadPosterFilePath;

    private static final String OPF = ".opf";
    private static final String NCX = ".ncx";
    private static final String TEG_TITLE = "dc:title";
    private static final String TEG_PUBLISHER = "dc:publisher";
    private static final String TEG_AUTHOR = "dc:creator";
    private static final String TEG_DESCRIPTION = "dc:description";
    private static final String TEG_BODY = "body";

    public Book converter(Book book) {
        String pathEpub = uploadPath + book.getFileName();
        String pathPoster = uploadPosterFilePath;
        Map<String, String> chapterSrc;

        ZipFile zf = null;
        try {
            zf = new ZipFile(pathEpub); // Получаем zip-file(epub)
        } catch (IOException e) {
            e.printStackTrace();
        }
        ZipEntry zipEntryOpf = zf.stream().filter(e -> e.getName().endsWith(OPF)).findAny().orElse(null); // Достаём файл с расширением .opf
        ZipEntry zipEntryNCX = zf.stream().filter(e -> e.getName().endsWith(NCX)).findAny().orElse(null); // Достаём файл с расширением .ncx
        ZipEntry zipEntryJpg = zf.stream().filter(e -> e.getName().endsWith(".jpg")).findFirst().orElse(null); // Достаём файл с расширением .jpg
        String opf = addString(zf, zipEntryOpf); // Получаем текстовое представление файла

        String title = searchByTags(opf, TEG_TITLE); // Ищем текст по тегам
        String author = searchByTags(opf, TEG_AUTHOR);
        String publisher = searchByTags(opf, TEG_PUBLISHER);
        String description = searchByTags(opf, TEG_DESCRIPTION);

        book.setTitle(title);
        log.info("Epub converter - add title to the book: {}",title);
        book.setPublisher(publisher);
        log.info("Epub converter - add publisher to the book: {}", publisher);
        book.setDescription(description);
        log.info("Epub converter - add description to the book: {}", description);

        if(!author.isEmpty()){
            List<String> authorList = Arrays.stream(author.split(",")).toList();
            authorList.stream().forEach(a->book.addAuthor(authorService.existByName(a)));
            log.info("Epub converter - author: {}", authorList);
        }
        if(zipEntryJpg != null){
            File uploadDir = new File(uploadPosterFilePath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String posterName = write(zf, zipEntryJpg, pathPoster); // Сохраняем изображение в каталоге постеров и возвращаем полное имя
            book.setPosterName(posterName);

            chapterSrc = findChapterAndText(zf, zipEntryNCX); // Получение Map<String, String>(ChapterName , src)
            if(chapterSrc != null) {
                for (Map.Entry<String, String> entry : chapterSrc.entrySet()) { // Если Главы существуют, добавляем их в книгу
                    String chapterName = entry.getKey();
                    String src = entry.getValue();
                    String chapterText = addString(zf, zf.getEntry(src));
                    if(chapterText.isEmpty()) continue;
                    Chapter chapter = new Chapter();
                    chapter.setChapterName(chapterName);
                    chapter.setChapterText(searchByTags(chapterText, TEG_BODY));
                    book.addChapters(chapter);
                }
            }
            log.info("Epub converter - add chapters to the book: {}", book.getChapters().size());
        }

        return book;
    }

    private String addString(ZipFile zf, ZipEntry zipEntry){ // Получаем текстовое представление файла
        StringBuffer result = new StringBuffer();

        if(zipEntry != null) {
            try (InputStream is = zf.getInputStream(zipEntry);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                while (reader.ready()){
                    result.append(reader.readLine()).append("\n");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    private String searchByTags(String text, String teg) {
        Document doc = Jsoup.parse(text);
        return doc.getElementsByTag(teg).text();
    }

    private String write(ZipFile zf, ZipEntry zipEntry, String pathPoster)  {
        ZipEntry ze = zipEntry;
        File f = new File(zipEntry.getName());
        String name = UUID.randomUUID() + "_" + f.getName();
        try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(pathPoster, name)))){
            InputStream in = zf.getInputStream(ze);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) >= 0)
                out.write(buffer, 0, len);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return name;
    }

    private Map<String, String> findChapterAndText(ZipFile zf, ZipEntry zipEntry)  {
        Map<String, String> chapterSrc = new LinkedHashMap<>();
        String ncx =addString(zf, zipEntry);
        Document doc = Jsoup.parse(ncx);
        Elements navPoint = doc.select("navPoint");
        for(Element element: navPoint){
            String navLabel = element.getElementsByTag("navLabel").text();
            if(navLabel.length()>100) continue;
            String content = element.getElementsByTag("content").attr("src");
            if(!content.endsWith("html")&& content.length()>0) {
                content = content.replaceAll("html.*", "");
                content = content + "html";
            }
            if(navLabel.isEmpty() || content.isEmpty()) {
                continue;
            }
            chapterSrc.put(navLabel,content);
        }
        return chapterSrc;
    }

}
