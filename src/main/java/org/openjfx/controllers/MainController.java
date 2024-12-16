package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private TabPane tabPane; // The TabPane containing all tabs

    @FXML
    private Tab homeTab; // Tab for Home
    @FXML
    private Tab trackTab; // Tab for Tracker

    public void initialize() {
        // Load content for the initially selected tab
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        loadContent(selectedTab);

        // Listener to detect tab switching
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            loadContent(newTab);
        });
    }

    private void loadContent(Tab tab) {
        try {
            // Determine the FXML to load based on the tab
            String fxmlPath = null;
            fxmlPath="/org/openjfx/"+tab.getText()+".fxml";

            // If we have an FXML file to load for this tab
            if (fxmlPath != null) {
                // Check if content is already loaded to avoid reloading


                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                // Wrap the root node in a StackPane and set it as the tab content
                StackPane container = new StackPane();
                container.getChildren().add(root);
                tab.setContent(container);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
