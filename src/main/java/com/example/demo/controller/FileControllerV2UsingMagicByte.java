package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/v2/file")
public class FileControllerV2UsingMagicByte {

  @PostMapping("/validate")
  public boolean validateFile(@RequestParam("file") MultipartFile file) {
    if (file == null || file.isEmpty()) {
      return false;
    }
    try {
      byte[] magicNumber = getFileMagicNumber(file.getInputStream());
      return isExcel(magicNumber) || isJPEG(magicNumber);
    } catch (IOException e) {
      return false;
    }
  }

  private byte[] getFileMagicNumber(InputStream inputStream) throws IOException {
    byte[] magicNumber = new byte[4];
    int bytesRead = inputStream.read(magicNumber, 0, 4);
    if (bytesRead != 4) {
      throw new IOException("Failed to read file magic number");
    }
    return magicNumber;
  }

  private boolean isExcel(byte[] magicNumber) {
    return magicNumber[0] == 0x50
        && magicNumber[1] == 0x4B
        && magicNumber[2] == 0x03
        && magicNumber[3] == 0x04;
  }

  private boolean isJPEG(byte[] magicNumber) {
    return magicNumber[0] == (byte) 0xFF && magicNumber[1] == (byte) 0xD8;
  }
}
