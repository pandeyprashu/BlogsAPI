package com.example.BlogsAPI.services.impl;

import com.example.BlogsAPI.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
       String name=file.getOriginalFilename();
       String filePath=path+ File.separator+name;
       File f=new File(path);
       if(!f.exists()){
           f.mkdir();
       }

        Files.copy(file.getInputStream(), Paths.get(filePath));


        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;

        return new FileInputStream(fullPath);
    }


}
