package ru.nova.novalib.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nova.novalib.domain.Author;
import ru.nova.novalib.domain.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
@Component
public class EpubConverter {

    @Autowired
    private AuthorService authorService;

    @Value("${upload.path}")
    private String uploadPath;
//    private static final String HTML = ".html";
    private static final String OPF = ".opf";
    private static final String TEG_TITLE = "dc:title";
    private static final String TEG_PUBLISHER = "dc:publisher";
    private static final String TEG_AUTHOR = "dc:creator";
    private static final String TEG_DESCRIPTION = "dc:description";

    public Book converter(Book book) {
        String path = uploadPath + "/" + book.getFileName();

        ZipFile zf = null;
        try {
            zf = new ZipFile(path); // Получаем zip-file(epub)
        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<? extends ZipEntry> zipEntriesHtml = zf.stream().filter(e -> e.getName().endsWith(".html")).toList();
        Optional<? extends ZipEntry> zipEntryOpf = zf.stream().filter(e -> e.getName().endsWith(OPF)).findAny(); // Достаём файл с расширением .opf
//        String nameOpf = zipEntryOpf.get().getName();
        String opf = addString(zipEntryOpf, zf); // Получаем текстовое представление файла
        Document doc = Jsoup.parse(opf);
        String title = getText(doc, TEG_TITLE);
        String author = getText(doc, TEG_AUTHOR);
        String publisher = getText(doc, TEG_PUBLISHER);
        String description = getText(doc, TEG_DESCRIPTION);
        log.info("title: {}; author: {}; publisher: {}; description{} ",title, author, publisher, description);

        book.setTitle(title);
        if(author!=null){
            book.addAuthor(authorService.existByName(author));
        }
        book.setPublisher(publisher);
        book.setDescription(description);

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

    private String getText(Document doc, String teg) {
        return doc.getElementsByTag(teg).text();
    }

}
