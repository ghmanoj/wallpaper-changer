package com.mghimire.wallpaperchanger.util;

import java.util.concurrent.TimeUnit;

public class ProcessUtil {

  private ProcessUtil() {
  }

  public static void waitForProcess(Process process, long durationInSeconds) {
    try {
      boolean isCompleted = process.waitFor(durationInSeconds, TimeUnit.SECONDS);
      if (!isCompleted) {
        throw new RuntimeException("Process did not complete on time");
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
