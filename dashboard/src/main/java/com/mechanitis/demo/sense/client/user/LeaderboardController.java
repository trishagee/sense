package com.mechanitis.demo.sense.client.user;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class LeaderboardController implements UserListener{
    @FXML private TableView<LoudMouth> leaders;

    @Override
    public void onMessage(String message) {
        LoudMouth tweeter = new LoudMouth("tweeter");
        tweeter.setLocation(message);
        leaders.getItems().add(tweeter);
    }
}
