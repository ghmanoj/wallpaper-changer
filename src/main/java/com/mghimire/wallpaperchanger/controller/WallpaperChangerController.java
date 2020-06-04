package com.mghimire.wallpaperchanger.controller;

import com.mghimire.wallpaperchanger.model.Wallpaper;
import com.mghimire.wallpaperchanger.tasks.BackgroundTask;
import com.mghimire.wallpaperchanger.tasks.TaskCompleteCallback;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class WallpaperChangerController implements TaskCompleteCallback {

  private static final Pattern integerPattern = Pattern.compile("^[0-9]+$");
  private static final int IMG_CONTAINER_WIDTH = 168;
  private static final int IMG_CONTAINER_HEIGHT = 168;
  private static final String DARK_THEME_CSS = "/css/dark-theme.css";
  private static final String LIGHT_THEME_CSS = "/css/light-theme.css";

  private final BackgroundTask backgroundTask;
  private final Stage primaryStage;

  @FXML
  private TextField updateIntervalTextField;
  @FXML
  private Label currentUpdateIntervalLabel;
  @FXML
  private Label errorLabel;
  @FXML
  private Button setUpdateIntervalButton;
  @FXML
  private Button hideWindowButton;
  @FXML
  private ImageView wallpaperImageView;
  @FXML
  private Slider themeToggleSlider;

  public WallpaperChangerController(Stage primaryStage, BackgroundTask backgroundTask) {
    this.primaryStage = primaryStage;
    this.backgroundTask = backgroundTask;
  }

  @FXML
  private void initialize() {
    setUpdateIntervalButton.setOnAction(this::handleSetUpdateIntervalButton);
    hideWindowButton.setOnAction(this::handleHideWindowButton);
    themeToggleSlider.setOnMouseClicked(this::handleThemeUpdateToggle);
  }

  private void handleHideWindowButton(ActionEvent actionEvent) {
    primaryStage.setIconified(true);
  }

  private void handleSetUpdateIntervalButton(ActionEvent actionEvent) {
    errorLabel.setText(null);
    if (isUpdateIntervalValid()) {
      int minutes = Integer.parseInt(updateIntervalTextField.getText());
      backgroundTask.startUpdatingEvery(this, minutes);
      String updateIntervalText = String.format("Updating every %s minutes", minutes);
      currentUpdateIntervalLabel.setText(updateIntervalText);
    } else {
      //set error label
      errorLabel.setText("Hmm.. The interval does not look valid.");
    }
  }

  private void handleThemeUpdateToggle(MouseEvent mouseEvent) {
    int value = (int) themeToggleSlider.getValue();

    if (value == 0) {
      setLightTheme();
    } else {
      setDarkTheme();
    }
  }

  private void setLightTheme() {
    Scene scene = primaryStage.getScene();
    ObservableList<String> stylesheets = scene.getStylesheets();
    stylesheets.clear();
    stylesheets.add(LIGHT_THEME_CSS);
  }

  private void setDarkTheme() {
    Scene scene = primaryStage.getScene();
    ObservableList<String> stylesheets = scene.getStylesheets();
    stylesheets.clear();
    stylesheets.add(DARK_THEME_CSS);
  }

  private boolean isUpdateIntervalValid() {
    String interval = updateIntervalTextField.getText();
      if (interval == null || interval.isEmpty()) {
          return false;
      }

    Matcher matcher = WallpaperChangerController.integerPattern.matcher(interval);
    return matcher.matches();
  }

  @Override
  public void onTaskComplete(Wallpaper wallpaper) {
    // set wallpaper image here
    final File wallpaperFile = wallpaper.getLocalFile();
    Platform.runLater(() ->
    {
      try (FileInputStream fip = new FileInputStream(wallpaperFile)) {
        Image image = new Image(fip, IMG_CONTAINER_WIDTH, IMG_CONTAINER_HEIGHT, false, false);
        wallpaperImageView.setImage(image);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
  }
}
