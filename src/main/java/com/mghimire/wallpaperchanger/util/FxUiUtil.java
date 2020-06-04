package com.mghimire.wallpaperchanger.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxUiUtil
{
    private static final String MAIN_UI_FXML = "/fxml/wallpaper-changer.fxml";

    private final Object controller;

    public FxUiUtil(Object controller)
    {
        this.controller = controller;
    }
    public Pane getMainUi()
    {
        URL mainUiURL = getClass().getResource(MAIN_UI_FXML);
        return loadFxml(controller, mainUiURL);
    }

    private static Pane loadFxml(Object controller, URL fxmlUrl)
    {
        try
        {
           FXMLLoader loader = new FXMLLoader(fxmlUrl);
           loader.setController(controller);
           return loader.load();
        }
        catch (Exception ex)
        {
            throw new IllegalArgumentException("Fxml file "
                    + fxmlUrl + " does not exist in resource");
        }
    }
}
