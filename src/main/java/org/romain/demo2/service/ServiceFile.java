package org.romain.demo2.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

 @Service
public class ServiceFile {

    @Value("${public.upload.folder}")
    private String publicUploadFolder;

    public void uploadToLocalFileSystem(MultipartFile file, String fileName) throws IOException {
        uploadToLocalFileSystem(file.getInputStream(), fileName);
    }

    public void uploadToLocalFileSystem(InputStream inputStream, String fileName) throws IOException {

        Path storageDirectory = Paths.get(publicUploadFolder);

        if(!Files.exists(storageDirectory)){
            try {
                Files.createDirectories(storageDirectory);
            }catch (Exception e){
                e.printStackTrace(); // droit ou pas de créer un dossier
            }
        }

        Path destination = Paths.get(storageDirectory.toString() + "/" + fileName);

        Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING); // copie du dossier temp au dossier final

    }
}
