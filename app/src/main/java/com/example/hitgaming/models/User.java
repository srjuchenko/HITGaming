package com.example.hitgaming.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userEmail;
    private String userId;
    private List<Game> favoriteGames;

    public User(String userEmail, String userId) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.favoriteGames = new ArrayList<>();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Game> getFavoriteGames() {
        return favoriteGames;
    }

    public void setFavoriteGames(List<Game> favoriteGames) {
        this.favoriteGames = favoriteGames;
    }

    public void addGameToFavorites(Game game) {
        this.favoriteGames.add(game);
    }

    @Override
    public String toString() {
        return "User{" +
                "userEmail='" + userEmail + '\'' +
                ", userId='" + userId + '\'' +
                ", favoriteGames=" + favoriteGames +
                '}';
    }
}
