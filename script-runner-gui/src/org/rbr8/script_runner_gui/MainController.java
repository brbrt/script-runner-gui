/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbr8.script_runner_gui;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 *
 * @author robert
 */
public class MainController implements Initializable {

    private ObservableList<File> files = FXCollections.observableArrayList();

    @FXML
    private Button processButton;

    @FXML
    private ListView filesListView;

    @FXML
    private void handleProcessButtonAction(ActionEvent event) {
        System.out.println("You selected " + files.size() + " files to process.");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupGestureTarget(filesListView);

        filesListView.setItems(files);
    }

    private void setupGestureTarget(final Control target) {

        target.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                
                // Accept only files.
                if (event.getGestureSource() != target && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.ANY);
                }

                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                
                boolean success = false;

                if (db != null && db.hasFiles()) {
                    List<File> droppedFiles = db.getFiles();

                    processDroppedFiles(droppedFiles);

                    success = true;
                }

                /* Let the source know whether the string was successfully transferred and used. */
                event.setDropCompleted(success);

                event.consume();
            }
        });

    }

    private void processDroppedFiles(List<File> droppedFiles) {

        for (File f : droppedFiles) {

            // If f is a directory, process it's files recursively.
            if (f.isDirectory()) {
                List<File> innerFiles = Arrays.asList(f.listFiles());

                processDroppedFiles(innerFiles);
            } else {
                files.add(f);
            }
        }
    }
}
