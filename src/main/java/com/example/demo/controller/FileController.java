package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.apache.tika.Tika;

@RestController
@RequestMapping("/v1/file")
public class FileController {

  @PostMapping("/validate")
  public boolean validateFile(@RequestParam("file") MultipartFile file) {
    try {
      return isExcel(file) || isJPEG(file);
    } catch (IOException e) {
      return false;
    }
  }

  private boolean isExcel(MultipartFile file) throws IOException {
    return getFileMimeType(file)
        .equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
  }

  private boolean isJPEG(MultipartFile file) throws IOException {
    String mimeType = getFileMimeType(file);
    return mimeType.equals("image/jpeg") || mimeType.equals("image/jpg");
  }

  private String getFileMimeType(MultipartFile file) throws IOException {
    Tika tika = new Tika();
    System.out.println("mime type v1 "+ tika.detect(file.getInputStream()));
    return tika.detect(file.getInputStream());
  }
}
