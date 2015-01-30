package com.mechanitis.demo.sense.client.user;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LoudMouth {
    private final SimpleStringProperty twitterHandle = new SimpleStringProperty();
    private final SimpleIntegerProperty tweets = new SimpleIntegerProperty(0);
    private final SimpleStringProperty location = new SimpleStringProperty();

    public LoudMouth(String twitterHandle) {
        this.twitterHandle.set(twitterHandle);
    }

    public void setLocation(String userLocation) {
        location.set(userLocation);
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle.set(twitterHandle);
    }

    public String getTwitterHandle() {
        return twitterHandle.get();
    }

    public String getLocation() {
        return location.get();
    }
}
