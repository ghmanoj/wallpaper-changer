package com.mghimire.wallpaperchanger.model;

import java.io.File;
import java.util.Map;

public class Wallpaper {

  private final Map<String, String> urls;

  private File localFile;

  public Wallpaper(Map<String, String> urls) {
    this.urls = urls;
  }

  public String getUrlLocation(String location) {
    return urls.get(location);
  }

  public File getLocalFile() {
    return localFile;
  }

  public void setLocalFile(File localFile) {
    this.localFile = localFile;
  }

  @Override
  public String toString() {
    return "Wallpaper{" + "urls=" + urls + '}';
  }
}
