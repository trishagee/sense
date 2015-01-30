package com.mechanitis.demo.sense.mood;

enum Mood {
    SAD, HAPPY;

    @Override
    public String toString() {
        return "\""+this.name()+"\"";
    }
}
