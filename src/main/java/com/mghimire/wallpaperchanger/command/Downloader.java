package com.mghimire.wallpaperchanger.command;

import com.mghimire.wallpaperchanger.model.Wallpaper;

public interface Downloader {

  Wallpaper download();

  Wallpaper getDownload();
}

