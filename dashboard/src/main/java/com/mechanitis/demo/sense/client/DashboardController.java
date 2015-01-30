package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.client.mood.HappinessController;
import com.mechanitis.demo.sense.client.mood.MoodController;
import javafx.fxml.FXML;

public class DashboardController {
    @FXML private MoodController moodController;
    @FXML private HappinessController happyController;

    public MoodController getMoodController() {
        return moodController;
    }

    public HappinessController getHappinessController() {
        return happyController;
    }

}
