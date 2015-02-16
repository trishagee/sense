package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.client.mood.MoodChartData;
import com.mechanitis.demo.sense.client.mood.MoodSocketClient;
import com.mechanitis.demo.sense.client.user.LeaderboardData;
import com.mechanitis.demo.sense.client.user.UserSocketClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MoodSocketClient moodSocketClient = new MoodSocketClient();
        moodSocketClient.connectToWebSocket();

        UserSocketClient userSocketClient = new UserSocketClient();
        userSocketClient.connectToWebSocket();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();
        DashboardController controller = loader.getController();

        wireUpMoodModelToController(moodSocketClient, controller);
        wireUpUserModelToController(userSocketClient, controller);

        primaryStage.setTitle("Twitter Dashboard");
        Scene scene = new Scene(root, 1024, 1024);
        scene.getStylesheets().add("dashboard.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void wireUpUserModelToController(UserSocketClient userSocketClient, DashboardController controller) {
        LeaderboardData leaderboardData = new LeaderboardData();
        userSocketClient.addListener(leaderboardData);
        controller.getLeaderboardController().setData(leaderboardData);
    }

    private void wireUpMoodModelToController(MoodSocketClient moodSocketClient, DashboardController controller) {
        MoodChartData moodData = new MoodChartData();
        moodSocketClient.addListener(moodData);
        controller.getMoodController().setData(moodData);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
