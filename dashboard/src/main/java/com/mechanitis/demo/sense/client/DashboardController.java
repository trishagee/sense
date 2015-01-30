package com.mechanitis.demo.sense.client;

import javafx.fxml.FXML;

public class DashboardController {
    @FXML private MoodController moodController;

    public MoodController getMoodController() {
        return moodController;
    }

}
