package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.client.mood.HappinessChartData;
import com.mechanitis.demo.sense.client.mood.MoodChartData;
import com.mechanitis.demo.sense.client.mood.MoodsParser;
import com.mechanitis.demo.sense.client.mood.TweetMood;
import com.mechanitis.demo.sense.client.user.LeaderboardData;
import com.mechanitis.demo.sense.infrastructure.ClientEndpoint;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // all models created in advance
        LeaderboardData leaderboardData = new LeaderboardData();
        MoodChartData moodChartData = new MoodChartData();
        HappinessChartData happinessChartData = new HappinessChartData();

        // wire up the models to the services they're getting the data from
        ClientEndpoint<String> clientEndpoint = ClientEndpoint.createPassthroughEndpoint("ws://localhost:8083/users/");
        clientEndpoint.addListener(leaderboardData);
        clientEndpoint.connect();

        ClientEndpoint<TweetMood> moodClientEndpoint = new ClientEndpoint<>("ws://localhost:8082/moods/", MoodsParser::parse);
        moodClientEndpoint.addListener(moodChartData);
        moodClientEndpoint.addListener(happinessChartData);
        moodClientEndpoint.connect();

        // initialise the UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        primaryStage.setTitle("Twitter Dashboard");
        Scene scene = new Scene(loader.load(), 1024, 1024);
        scene.getStylesheets().add("dashboard.css");

        // wire up the models to the controllers
        DashboardController dashboardController = loader.getController();
        dashboardController.getLeaderboardController().setData(leaderboardData);
        dashboardController.getMoodController().setData(moodChartData);
        dashboardController.getHappinessController().setData(happinessChartData);

        // let's go!
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
