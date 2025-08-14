package org.romain.demo2.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path publicFolder;

    public FileStorageService(@Value("${public.upload.folder}") String publicFolderPath) throws IOException {
        this.publicFolder = Paths.get(publicFolderPath).toAbsolutePath().normalize();
        Files.createDirectories(this.publicFolder); // crée le dossier s’il n’existe pas
    }

    // Sauvegarde un fichier et retourne sa clé technique (storageKey)
    public String saveFile(MultipartFile file, String subFolder) {
        try {
            String extension = "";
            String originalName = file.getOriginalFilename();
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID() + extension;
            Path targetFolder = publicFolder.resolve(subFolder);
            Files.createDirectories(targetFolder);

            Path targetFile = targetFolder.resolve(fileName);
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

            return subFolder + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l’enregistrement du fichier", e);
        }
    }

    // Charger un fichier en tant que ressource
    public Resource loadFileAsResource(String storageKey) {
        try {
            Path filePath = publicFolder.resolve(storageKey).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Fichier introuvable : " + storageKey);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier", e);
        }
    }
}
