package com.example.hitgaming.models;

public class Game {
    private String gameName;
    private String releaseDate;
    private String imageURL;
    private float rating;

    public Game(String gameName, String releaseDate, String imageURL, float rating) {
        this.gameName = gameName;
        this.releaseDate = releaseDate;
        this.imageURL = imageURL;
        this.rating = rating;
    }

    public String getGameName() {
        return gameName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameName='" + gameName + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", rating=" + rating +
                '}';
    }
}
