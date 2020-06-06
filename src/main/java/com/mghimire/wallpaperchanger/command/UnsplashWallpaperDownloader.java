package com.mghimire.wallpaperchanger.command;

import com.google.gson.Gson;
import com.mghimire.wallpaperchanger.model.Wallpaper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public final class UnsplashWallpaperDownloader implements Downloader {

  private static final String UNSPLASH_API_URL = "https://api.unsplash.com";
  private static final String UNSPLASH_RANDOM_URL = "/photos/random";
  private static final String IMG_FILE_PREFIX = "UnsplashWall_";
  private static final String IMG_FILE_SUFFIX = ".jpg";
  private static final String CONTENT_QUERY = "?content_filter=high&featured&orientation=landscape";

  private final String apiKey;
  private final OkHttpClient client = new OkHttpClient();
  private final Gson gson = new Gson();

  private Wallpaper wallpaper;

  public UnsplashWallpaperDownloader(String apiKey) {
    if (apiKey == null || apiKey.isEmpty())
      throw new IllegalArgumentException("API KEY cannot be empty or null");

    this.apiKey = apiKey;
  }

  @Override
  public Wallpaper download() {
    try {
      wallpaper = getWallpaper();
      File rawWallpaperFile = getRawWallpaper(wallpaper);
      wallpaper.setLocalFile(rawWallpaperFile);

      return wallpaper;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }

  @Override
  public Wallpaper getDownload() {
    return wallpaper;
  }

  private File getRawWallpaper(Wallpaper wallpaper) throws Exception {
      if (wallpaper == null) {
          throw new IllegalArgumentException("Wallpaper cannot be null");
      }

    String rawWallpaperLocation = wallpaper.getUrlLocation("regular");

    Request request = new Request.Builder()
        .url(rawWallpaperLocation)
        .build();

    try (Response response = client.newCall(request).execute()) {
      ResponseBody responseBody = response.body();
      assert (responseBody != null);
      try (InputStream inputStream = responseBody.byteStream()) {
        return convertInputStreamToFile(inputStream);
      }
    }
  }

  private File convertInputStreamToFile(InputStream inputStream) throws IOException {
    File tempFile = File.createTempFile(IMG_FILE_PREFIX, IMG_FILE_SUFFIX);

    try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
      int read;
      byte[] bytes = new byte[1024];

      while ((read = inputStream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }
    }
    return tempFile;
  }

  private Wallpaper getWallpaper() throws Exception {
    Request request = new Request.Builder()
        .url(UNSPLASH_API_URL + UNSPLASH_RANDOM_URL + CONTENT_QUERY)
        .header("Authorization", "Client-ID " + apiKey)
        .build();

    Wallpaper wallpaper;

    try (Response response = client.newCall(request).execute()) {
      ResponseBody responseBody = response.body();

        if (responseBody == null) {
            throw new Exception("response body is null");
        }
      wallpaper = gson.fromJson(responseBody.string(), Wallpaper.class);
    }

    return wallpaper;
  }

}
