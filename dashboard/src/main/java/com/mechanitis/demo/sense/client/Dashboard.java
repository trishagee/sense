package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.client.mood.OverallMood;
import com.mechanitis.demo.sense.client.sockets.MoodSocketClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MoodSocketClient moodSocketClient = new MoodSocketClient();
        OverallMood listener = new OverallMood();
        moodSocketClient.addListener(listener);
        moodSocketClient.connectToWebSocket();



        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();
        DashboardController controller = loader.getController();
        MoodController moodController = controller.getMoodController();
        moodController.setOverallMood(listener);

        primaryStage.setTitle("Twitter Dashboard");
        Scene scene = new Scene(root, 1024, 1024);
        scene.getStylesheets().add("com/mechanitis/demo/sense/client/dashboard.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
