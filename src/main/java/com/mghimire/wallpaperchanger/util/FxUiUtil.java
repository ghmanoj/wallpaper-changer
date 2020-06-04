package com.mghimire.wallpaperchanger.util;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxUiUtil {

  private static final String MAIN_UI_FXML = "/fxml/wallpaper-changer.fxml";

  private final Object controller;

  public FxUiUtil(Object controller) {
    this.controller = controller;
  }

  private static Pane loadFxml(Object controller, URL fxmlUrl) {
    try {
      FXMLLoader loader = new FXMLLoader(fxmlUrl);
      loader.setController(controller);
      return loader.load();
    } catch (Exception ex) {
      throw new IllegalArgumentException("Fxml file "
          + fxmlUrl + " does not exist in resource");
    }
  }

  public Pane getMainUi() {
    URL mainUiURL = getClass().getResource(MAIN_UI_FXML);
    return loadFxml(controller, mainUiURL);
  }
}
