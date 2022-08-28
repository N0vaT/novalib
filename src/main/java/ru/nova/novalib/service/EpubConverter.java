package ru.nova.novalib.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nova.novalib.domain.Book;

import java.io.*;
import java.util.Optional;
import java.util.UUID;
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
    private static final String TEG_TITLE = "dc:title";
    private static final String TEG_PUBLISHER = "dc:publisher";
    private static final String TEG_AUTHOR = "dc:creator";
    private static final String TEG_DESCRIPTION = "dc:description";

    public Book converter(Book book) {
        String pathEpub = uploadPath + book.getFileName();
        String pathPoster = uploadPosterFilePath;

        ZipFile zf = null;
        try {
            zf = new ZipFile(pathEpub); // Получаем zip-file(epub)
        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<? extends ZipEntry> zipEntriesHtml = zf.stream().filter(e -> e.getName().endsWith(".html")).toList();
        Optional<? extends ZipEntry> zipEntryOpf = zf.stream().filter(e -> e.getName().endsWith(OPF)).findAny(); // Достаём файл с расширением .opf
        Optional<? extends ZipEntry> zipEntryJpg = zf.stream().filter(e -> e.getName().endsWith(".jpg")).findFirst(); // Достаём файл с расширением .jpg
        String opf = addString(zipEntryOpf, zf); // Получаем текстовое представление файла

        String title = searchByTags(opf, TEG_TITLE); // Ищем текст по тегам
        String author = searchByTags(opf, TEG_AUTHOR);
        String publisher = searchByTags(opf, TEG_PUBLISHER);
        String description = searchByTags(opf, TEG_DESCRIPTION);
        log.info("Epub converter - title: {}; author: {}; publisher: {}; description{} ",title, author, publisher, description);

        book.setTitle(title);
        book.setPublisher(publisher);
        book.setDescription(description);
        if(!author.isEmpty()){
            book.addAuthor(authorService.existByName(author));
        }
        if(zipEntryJpg.isPresent()){
            File uploadDir = new File(uploadPosterFilePath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String posterName = write(zf, zipEntryJpg, pathPoster); // Сохраняем изображение в каталоге постеров и возвращаем полное имя
            book.setPosterName(posterName);
        }
        return book;
    }

    public String addString(Optional<? extends ZipEntry> zipEntry, ZipFile zf){
        StringBuffer line = new StringBuffer();

        if(!zipEntry.isEmpty()) {
            try (InputStream is = zf.getInputStream(zipEntry.get());
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                while (reader.ready()){
                    line.append(reader.readLine()).append("\n");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return line.toString();
    }

    private String searchByTags(String text, String teg) {
        Document doc = Jsoup.parse(text);
        return doc.getElementsByTag(teg).text();
    }

    private static String write(ZipFile zf, Optional<? extends ZipEntry> zipEntry, String pathPoster)  {
        ZipEntry ze = zipEntry.get();
        File f = new File(zipEntry.get().getName());
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

}
