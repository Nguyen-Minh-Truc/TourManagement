package com.J2EE.TourManagement.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
  @Value("${mt.upload-file.base-uri}") private String basePath;

  public void createDirectory(String folder) throws URISyntaxException {
    URI uri = new URI(folder);
    Path path = Paths.get(uri);
    File tmpDir = new File(path.toString());
    if (!tmpDir.isDirectory()) {
      try {
        Files.createDirectory(tmpDir.toPath());
        System.out.println(">>> CREATE NEW DIRECTORY SUCCESSFUL, PATH = " +
                           folder);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println(">>> SKIP MAKING DIRECTORY, ALREADY EXISTS");
    }
  }

  public String store(MultipartFile file, String folder)
      throws URISyntaxException, IOException {
    // create unique filename
    String finalName =
        System.currentTimeMillis() + "-" + file.getOriginalFilename();

    URI uri = new URI(basePath + folder + "/" + finalName);
    Path path = Paths.get(uri);
    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
    }
    return finalName;
  }

  // Xoá file theo folder + fileName
  public boolean deleteFile(String folder, String fileName)
      throws IOException, URISyntaxException {
    Path filePath =
        Paths.get(new URI(basePath)).resolve(folder).resolve(fileName);
    System.out.println("Xoá file tại: " + filePath.toAbsolutePath());

    if (Files.exists(filePath)) {
      return Files.deleteIfExists(filePath);
    } else {
      System.out.println("File không tồn tại");
      return false;
    }
  }

  // Xoá file theo URL upload
  public boolean deleteFileByUrl(String fileUrl) throws IOException, URISyntaxException {
    URI uri = URI.create(fileUrl);
    String path = uri.getPath(); // ví dụ: /storage/tour/1686054321-file.pdf
    String[] parts = path.split("/");

    if (parts.length < 3)
      throw new IOException("URL file không hợp lệ");

    String folder = parts[2];   // tour
    String fileName = parts[3]; // 1686054321-file.pdf

    return deleteFile(folder, fileName);
  }
}
