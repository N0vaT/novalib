package ru.nova.novalib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nova.novalib.domain.Book;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {

    @Autowired
    private EpubConverter epubConverter;

    @Value("${upload.path}")
    private String uploadPath;

    public Book saveFile(MultipartFile multipartFile){
        Book book = new Book();
        if(multipartFile != null) {
            saveAndConvertInBook(multipartFile, book);
        }else{
            //TODO Exeption
        }

        return book;
    }

    private void saveAndConvertInBook(MultipartFile multipartFile, Book book) {
            String resultFilename =  UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
            try {
                File file = new File(uploadPath + "/" + resultFilename);
                if(!file.exists()){
                    file.mkdir();
                }
                multipartFile.transferTo(file);
                book.setFileName(resultFilename);
                epubConverter.converter(book);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}