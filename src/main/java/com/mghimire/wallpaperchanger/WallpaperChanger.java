package com.mghimire.wallpaperchanger;

import com.mghimire.wallpaperchanger.controller.WallpaperChangerController;
import com.mghimire.wallpaperchanger.tasks.BackgroundWallpaperChangerTask;
import com.mghimire.wallpaperchanger.util.ApiUtil;
import com.mghimire.wallpaperchanger.util.FxUiUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WallpaperChanger extends Application {

  private static final double WINDOW_HEIGHT = 371.0;
  private static final double WINDOW_WIDTH = 302.0;

  private static final String APP_ICON = "/icons/wallpaper.png";
  private static final String APP_TITLE = "Wallpaper Changer";
  private static final String DEFAULT_STYLE = "/css/light-theme.css";

  private BackgroundWallpaperChangerTask backgroundTask;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // check api key in environment variable
    ApiUtil.getApiKey();

    backgroundTask = new BackgroundWallpaperChangerTask();

    WallpaperChangerController controller = new WallpaperChangerController(primaryStage,
        backgroundTask);
    Pane node = new FxUiUtil(controller).getMainUi();

    Scene scene = new Scene(node, WINDOW_WIDTH, WINDOW_HEIGHT);

    scene.getStylesheets().add(DEFAULT_STYLE);

    setAppIcon(primaryStage);
    primaryStage.setScene(scene);
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    backgroundTask.terminateTask();
    super.stop();
  }

  private void setAppIcon(Stage primaryStage) {
    primaryStage.getIcons().add(new Image(APP_ICON));
  }
}
