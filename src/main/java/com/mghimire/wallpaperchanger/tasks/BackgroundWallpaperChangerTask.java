package com.mghimire.wallpaperchanger.tasks;

import com.mghimire.wallpaperchanger.command.Command;
import com.mghimire.wallpaperchanger.command.Downloader;
import com.mghimire.wallpaperchanger.command.UnsplashWallpaperDownloader;
import com.mghimire.wallpaperchanger.command.CommandAdapter;
import com.mghimire.wallpaperchanger.model.Wallpaper;
import com.mghimire.wallpaperchanger.util.ApiUtil;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class BackgroundWallpaperChangerTask implements BackgroundTask {

  private static final int MAX_INTERVAL_MINUTES = 24 * 60;
  private static final int MIN_INTERVAL_MINUTES = 1;

  private final AtomicBoolean isTaskRunning = new AtomicBoolean(false);
  private ScheduledExecutorService executorService;
  private TaskCompleteCallback callbackReference;

  private int minutes;

  @Override
  public void startUpdatingEvery(TaskCompleteCallback callback, int minutes) {
    this.minutes = minutes;
    this.callbackReference = callback;
    checkUpdateIntervalValid();
    startUpdateTask();
    System.out.println("Ok... starting to update every " + minutes + " minutes");
  }

  @Override
  public void terminateTask() {
    try {
      if (executorService != null && !executorService.isShutdown()) {
        executorService.shutdownNow();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void startUpdateTask() {
    if (isTaskRunning.get()) {
      terminateTask();
      isTaskRunning.set(false);
    }

    isTaskRunning.set(true);
    executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(this::doWallpaperFetchAndSet, 0, minutes, TimeUnit.MINUTES);
  }

  private void doWallpaperFetchAndSet() {
    String apiKey = ApiUtil.getApiKey();
    Downloader downloader = new UnsplashWallpaperDownloader(apiKey);
    Command wallpaperCommand = CommandAdapter.getWallpaperCommandBasedOnOs(downloader);
    wallpaperCommand.execute();
    Wallpaper wallpaper = downloader.getDownload();
    callbackReference.onTaskComplete(wallpaper);
  }

  private void checkUpdateIntervalValid() {
    if (minutes > MAX_INTERVAL_MINUTES || minutes < MIN_INTERVAL_MINUTES) {
      String format = "Given update interval %s is not valid. It should be between %s and %s minutes";
      String message = String.format(format, minutes, MIN_INTERVAL_MINUTES, MAX_INTERVAL_MINUTES);

      throw new IllegalArgumentException(message);
    }
  }

}
