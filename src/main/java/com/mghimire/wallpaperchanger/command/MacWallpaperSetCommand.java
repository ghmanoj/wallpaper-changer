package com.mghimire.wallpaperchanger.command;

import com.mghimire.wallpaperchanger.model.Wallpaper;
import com.mghimire.wallpaperchanger.util.ProcessUtil;
import java.io.File;
import java.io.IOException;

public final class MacWallpaperSetCommand implements Command {

  private static final String WALL_CH_COMMAND =
      "osascript -e 'tell application \"Finder\" to set desktop picture to %s as POSIX file'";
  private static final long PROCESS_WAIT_TIME_IN_SECONDS = 8;

  private final Downloader downloader;

  public MacWallpaperSetCommand(Downloader downloader) {
    this.downloader = downloader;
  }

  @Override
  public void execute() {
    setWallpaper();
  }

  private void setWallpaper() {
    Wallpaper wallpaper = downloader.download();
    setWallpaper(wallpaper);
  }

  private void setWallpaper(Wallpaper wallpaper) {
    try {
      File absFilePath = wallpaper.getLocalFile();

      // executeWallChCommand(absFilePath);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void executeWallChCommand(File absFilePath) throws IOException {
    String changeWallpaperCommand = String.format(WALL_CH_COMMAND, absFilePath);
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command(changeWallpaperCommand);
    Process process = processBuilder.start();
    ProcessUtil.waitForProcess(process, PROCESS_WAIT_TIME_IN_SECONDS);
  }
}