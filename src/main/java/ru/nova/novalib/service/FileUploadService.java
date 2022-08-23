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

    public void saveFile(MultipartFile multipartFile){
        if(multipartFile != null){
            String resultFilename =  UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
            try {
                File file = new File(uploadPath + "/" + resultFilename);
                if(!file.exists()){
                    file.mkdir();
                }
                multipartFile.transferTo(file);
                Book book = new Book();
                book.setFileName(resultFilename);
                epubConverter.converter(book);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
