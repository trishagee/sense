package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.client.user.LeaderboardController;
import javafx.fxml.FXML;

public class DashboardController {
    @FXML private LeaderboardController leaderboardController;

    public LeaderboardController getLeaderboardController() {
        return leaderboardController;
    }
}
