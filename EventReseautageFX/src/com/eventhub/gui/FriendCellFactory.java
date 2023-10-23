package com.eventhub.gui;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class FriendCellFactory extends ListCell<String> {

    private HBox container;
    private Button addButton;
    private Button removeButton;

    public FriendCellFactory() {
        container = new HBox();
        addButton = new Button("Add");
        removeButton = new Button("Remove");

        container.getChildren().addAll(addButton, removeButton);
        container.setSpacing(10.0);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            setGraphic(container);
        }
    }
}