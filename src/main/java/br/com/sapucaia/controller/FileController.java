package br.com.sapucaia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.sapucaia.armazen.dropbox.DropBoxService;
import jakarta.servlet.http.HttpServletRequest;


@RequestMapping("/api/file")
@CrossOrigin("*")
@Controller
@RestController
public class FileController {
	
	@Autowired
    private DropBoxService dropboxService;

	@PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            dropboxService.uploadFile(file.getOriginalFilename(), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/" + file.getOriginalFilename();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filePath) {
        try {
            byte[] fileBytes = dropboxService.downloadFile(filePath);
            ByteArrayResource resource = new ByteArrayResource(fileBytes);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(fileBytes.length)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public List<String> listFiles() {
        List<String> fileNames = dropboxService.listFiles();
        return fileNames;
    }
}
