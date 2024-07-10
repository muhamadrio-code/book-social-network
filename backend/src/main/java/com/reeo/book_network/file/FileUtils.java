package com.reeo.book_network.file;

import ch.qos.logback.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {


  public static byte[] readFileFromLocation(String fileUrl) {
    if (StringUtil.isNullOrEmpty(fileUrl)) {
      return null;
    }

    String path = new File(fileUrl).getPath();
    try {
      return Files.readAllBytes(Path.of(path));
    } catch (IOException e) {
      log.error("No file found in the path {}", fileUrl);
    }

    return null;
  }
}
