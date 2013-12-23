/*
 * The MIT License
 *
 * Copyright 2013 robert.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.rbr8.script_runner.gui;

import org.rbr8.script_runner.util.ProcessHelper;
import java.io.File;
import java.io.IOException;
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
    private void handleProcessButtonAction(ActionEvent event) throws IOException {
//        System.out.println("You selected " + files.size() + " files to process.");
        ProcessHelper ph = new ProcessHelper();
        ph.startProcess("");
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
