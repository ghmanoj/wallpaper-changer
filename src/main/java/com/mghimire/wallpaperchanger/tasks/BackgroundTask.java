package com.mghimire.wallpaperchanger.tasks;

public interface BackgroundTask {

  void startUpdatingEvery(TaskCompleteCallback callback, int minutes);

  void terminateTask();
}
