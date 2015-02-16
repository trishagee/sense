package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.client.mood.HappinessController;
import com.mechanitis.demo.sense.client.mood.MoodController;
import com.mechanitis.demo.sense.client.user.LeaderboardController;
import javafx.fxml.FXML;

public class DashboardController {
    @FXML private MoodController moodController;
    @FXML private HappinessController happyController;
    @FXML private LeaderboardController leaderboardController;

    public MoodController getMoodController() {
        return moodController;
    }

    public HappinessController getHappinessController() {
        return happyController;
    }

    public LeaderboardController getLeaderboardController() {
        return leaderboardController;
    }
}
