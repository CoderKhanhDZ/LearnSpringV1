package com.learnspring.java11.LearnSpringV1.controller;

import com.learnspring.java11.LearnSpringV1.model.ResponseObject;
import com.learnspring.java11.LearnSpringV1.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * this is controller receive file/image from client
 */
@RestController
@RequestMapping(path = "/api/file", name = "FileUploadController")
public class FileUploadController {

    // inject storage service here
    @Autowired
    private IStorageService iStorageService;

    @PostMapping("/{id}/upload")
    ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
        try {
            // save file to folder
            String generatedFileName = iStorageService.storeFile(file);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "upload file success", generatedFileName));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject(HttpStatus.NOT_IMPLEMENTED.toString(), "upload file failed", ""));
        }

    }

    // get image's url
    @GetMapping("/{fileName:.+}")
    ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = iStorageService.readFileUpload(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);

        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }

    }

    // get image's url
    @DeleteMapping("/{fileName:.+}")
    ResponseEntity<ResponseObject> deleteFile(@PathVariable String fileName) {
        try {
            iStorageService.deleteFile(fileName);
            return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.toString(),
                    "delete image success", ""));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseObject(HttpStatus.NOT_IMPLEMENTED.toString(),
                    "not found image to delete", ""));
        }

    }

    // get all path file image
    @GetMapping("")
    ResponseEntity<ResponseObject> getAllUploadFile() {
        try {
            List<String> allUploadFile = iStorageService.loadAllFileUpload()
                    .map(path -> {
                        //convert fileName to url
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readDetailFile", path.getFileName().toString())
                                .build()
                                .toUri()
                                .toString();
                        return urlPath;
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(
                    new ResponseObject(HttpStatus.OK.toString(),
                            "get all upload file success", allUploadFile));

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ResponseObject(HttpStatus.NO_CONTENT.toString(),
                            "get all upload failed success", new List[]{}));
        }

    }
}
