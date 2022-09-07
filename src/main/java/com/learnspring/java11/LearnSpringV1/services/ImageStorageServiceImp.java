package com.learnspring.java11.LearnSpringV1.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service // danh dau day la 1 service
public class ImageStorageServiceImp implements IStorageService {

    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageServiceImp() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("cannot initialize storage folder", e);
        }
    }

    // true if this list contains the specified element
    private boolean isImageFile(MultipartFile file) {
        //install FileNameUtils - apache common io maven
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return List.of(new String[]{"png", "jpg", "jpeg", "bmp"}).contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {

        System.out.println("store file");

        // up file on server
//        Optional<Product> foundProduct = productRepository.findById(id);
//            if(foundProduct.isPresent()){
//                foundProduct.map(product -> {
//                   product.setUrl(generatedFileName);
//                   return product;
//                });
//            }

        // check this file empty?
        if (file.isEmpty()) {
            throw new RuntimeException("failed to store empty file.");
        }
        // check this file is image?
        if (!isImageFile(file)) {
            throw new RuntimeException("you can only upload image file.");
        }
        // check size image <= 5mb
        float fileSizeInMegaBytes = file.getSize() / 1_000_000.0f;
        if (fileSizeInMegaBytes > 5.0f) {
            throw new RuntimeException("you can only upload image file.");
        }
        // rename file with UUID
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generatedFileName = UUID.randomUUID().toString().replace("-", "");
        generatedFileName = generatedFileName + "." + fileExtension;

        Path destinationFilePath = this.storageFolder.resolve(
                Paths.get(generatedFileName))
                .normalize()
                .toAbsolutePath();

        System.out.println("generatedFileName" + generatedFileName);
        System.out.println("destinationFilePath" + destinationFilePath);

        // check destination file path
        if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
            throw new RuntimeException(
                    "cannot store file outside current directory."
            );
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            return generatedFileName;
        } catch (IOException e) {
            throw new RuntimeException("cannot initialize storage folder", e);
        }
    }

    @Override
    public Stream<Path> loadAllFileUpload() {
        try {
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        } catch (IOException e) {
            throw new RuntimeException("cannot load all file upload", e);
        }

    }

    @Override
    public byte[] readFileUpload(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            } else {
                throw new RuntimeException(
                        "Could not read file: " + fileName
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not read file: " + fileName, e
            );
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && !resource.isOpen()) {
                Files.delete(file);
            } else {
                throw new RuntimeException(
                        "file opened or not exists: " + fileName
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("cannot delete file", e);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
