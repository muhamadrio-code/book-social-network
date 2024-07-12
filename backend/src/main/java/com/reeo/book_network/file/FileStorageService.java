package com.reeo.book_network.file;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.io.File.separator;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

  @Value("${application.file.uploads.photos-output-path}")
  private String fileUploadPath;

  public String saveFile(
      @NonNull MultipartFile sourceFile,
      @NonNull Integer userId,
      @NonNull Integer bookId
  ) {
    String fileSubPathString = "users" + separator + userId;
    return saveFile(sourceFile, fileSubPathString);
  }

  private String saveFile(
      @NonNull MultipartFile sourceFile,
      @NonNull String fileSubPathString
  ) {
    String finalPath = fileUploadPath + separator + fileSubPathString;
    File targetFolder = new File(finalPath);

    if (!targetFolder.exists()) {
      boolean folderCreated = targetFolder.mkdirs();
      if (!folderCreated) {
        log.error("Failed to create the target folder: " + targetFolder);
        return null;
      }
    }

    final String fileExtension = getFileExtension(Objects.requireNonNull(sourceFile.getOriginalFilename()));
    final String targetFilePath = finalPath + separator + "." + fileExtension;
    final Path path = Paths.get(targetFilePath);

    try {
      Files.write(path, sourceFile.getBytes());
      log.warn("File is saved at:" + targetFilePath);
      return targetFilePath;
    } catch (IOException exception) {
      log.error("Failed to save file : {}", exception.getMessage());
      return null;
    }
  }

  private String getFileExtension(String originalFilename) {
    if (originalFilename.isEmpty()) {
      return "";
    }

    int lastIndexOf = originalFilename.lastIndexOf(".");
    if (lastIndexOf == -1) return "";
    return originalFilename.substring(lastIndexOf + 1).toLowerCase();
  }

}
