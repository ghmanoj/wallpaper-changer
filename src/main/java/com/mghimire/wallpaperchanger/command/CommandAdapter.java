package com.mghimire.wallpaperchanger.command;

public final class CommandAdapter {

  private CommandAdapter() {
  }

  public static Command getWallpaperCommandBasedOnOs(Downloader downloader) {
    String os = System.getProperty("os.name").toLowerCase();

    if (os.contains("mac")) {
      return new MacWallpaperSetCommand(downloader);
    } else if (os.contains("microsoft")) {
      return new WinWallpaperSetCommand(downloader);
    } else if (os.contains("linux")) {
      return new LinuxWallpaperSetCommand(downloader);
    } else {
      String errorMessage = String.format("Unsupported operating system: %s", os);
      System.out.println(errorMessage);
      throw new RuntimeException(errorMessage);
    }
  }
}