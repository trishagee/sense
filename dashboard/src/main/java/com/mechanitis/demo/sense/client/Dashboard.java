package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.client.mood.MoodController;
import com.mechanitis.demo.sense.client.mood.MoodData;
import com.mechanitis.demo.sense.client.mood.MoodSocketClient;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();
        DashboardController controller = loader.getController();

        wireUpMoodModelToController(moodSocketClient, controller);

        primaryStage.setTitle("Twitter Dashboard");
        Scene scene = new Scene(root, 1024, 1024);
        scene.getStylesheets().add("com/mechanitis/demo/sense/client/dashboard.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void wireUpMoodModelToController(MoodSocketClient moodSocketClient, DashboardController controller) {
        MoodData moodData = new MoodData();
        moodSocketClient.addListener(moodData);
        controller.getMoodController().setData(moodData);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
