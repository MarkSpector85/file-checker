package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/v3/file")
public class FileControllerV3 {

  @PostMapping("/validate")
  public boolean validateFile(@RequestParam("file") MultipartFile file) {
    if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
      return false;
    }

    try {
      String mimeType = Files.probeContentType(Path.of(file.getOriginalFilename()));
      System.out.println("mime type from v3 "+ mimeType);
      return isExcel(mimeType) || isJPEG(mimeType);
    } catch (IOException e) {
      return false;
    }
  }

  private boolean isExcel(String mimeType) {
    return mimeType != null
        && mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
  }

  private boolean isJPEG(String mimeType) {
    return mimeType != null && (mimeType.equals("image/jpeg") || mimeType.equals("image/jpg"));
  }
}
