package ru.nsu.fit.golikov.lab2_2.openStreetMap;

import lombok.Getter;

@Getter
public class User {
    private String name;
    private int changeCount;

    public User(String name, int changeCount) {
        this.name = name;
        this.changeCount = changeCount;
    }

    @Override
    public String toString() {
        return name + " " + changeCount;
    }
}
