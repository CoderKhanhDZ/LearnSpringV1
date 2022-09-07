package com.learnspring.java11.LearnSpringV1.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    String storeFile(MultipartFile file);

    Stream<Path> loadAllFileUpload(); // load all file inside a folder

    byte[] readFileUpload(String fileName);

    void deleteFile(String fileName);

    void deleteAllFiles();

}
