package org.openjfx.controllers;

import javafx.stage.Stage;
import org.openjfx.view.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;
//main controller login
public class MainController {

    @FXML
    private TabPane tabPane; // The TabPane containing all tabs


    public void initialize() {
        // Load content for the initially selected tab
        //sees which tab is clicked
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
            //if the tab is sign out go clear the current stage and open the log in page
            if (Objects.equals(tab.getText(), "Sign out")) {
                if (PageController.showError("Are you sure ","sign out") )
                {
                    LoginController loginController = new LoginController();

                    loginController.stageClose();
                    Main main = new Main();
                    main.start(new Stage());
                }
                else{
                    tabPane.getSelectionModel().select(0);
                }




            }
            else{
                //go to the fxml file for that tab
                fxmlPath="/org/openjfx/"+tab.getText()+".fxml";
            }

            // If we have an FXML file to load for this tab
            if (fxmlPath != null) {
                // Check if content is already loaded to avoid reloading


                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                // Wrap the root node in a StackPane and set it as the tab content
                StackPane container = new StackPane();
                container.getChildren().add(root);
                //sets the content as the fxml file content
                tab.setContent(container);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
