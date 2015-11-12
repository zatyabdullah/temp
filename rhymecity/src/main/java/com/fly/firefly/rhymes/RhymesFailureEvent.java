package com.fly.firefly.rhymes;

public class RhymesFailureEvent {

    private final String word;

    public RhymesFailureEvent(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
