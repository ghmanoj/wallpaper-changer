package com.mghimire.wallpaperchanger.command;

import com.mghimire.wallpaperchanger.model.Wallpaper;

public final class MacWallpaperSetCommand implements Command {
  private final Downloader downloader;

  public MacWallpaperSetCommand(Downloader downloader) {
    this.downloader = downloader;
  }

  @Override
  public void execute() {
    setWallpaper();
  }

  private void setWallpaper() {
    // execute set wallpaper command
    Wallpaper wallpaper = downloader.download();
    setWallpaper(wallpaper);
  }

  private void setWallpaper(Wallpaper wallpaper) {
    System.out.println("Wallpaper setting feature not implemented yet for macOS....");
  }
}