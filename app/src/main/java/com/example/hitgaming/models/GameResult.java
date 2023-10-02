package com.example.hitgaming.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GameResult {

    @SerializedName("aliases")
    @Expose
    private Object aliases;
    @SerializedName("api_detail_url")
    @Expose
    private String apiDetailUrl;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("date_last_updated")
    @Expose
    private String dateLastUpdated;
    @SerializedName("deck")
    @Expose
    private Object deck;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("expected_release_day")
    @Expose
    private Object expectedReleaseDay;
    @SerializedName("expected_release_month")
    @Expose
    private Object expectedReleaseMonth;
    @SerializedName("expected_release_quarter")
    @Expose
    private Object expectedReleaseQuarter;
    @SerializedName("expected_release_year")
    @Expose
    private Object expectedReleaseYear;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image")
    @Expose
    private Image image;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number_of_user_reviews")
    @Expose
    private Integer numberOfUserReviews;
    @SerializedName("original_game_rating")
    @Expose
    private Object originalGameRating;
    @SerializedName("original_release_date")
    @Expose
    private String originalReleaseDate;
    @SerializedName("site_detail_url")
    @Expose
    private String siteDetailUrl;

    public Object getAliases() {
        return aliases;
    }

    public void setAliases(Object aliases) {
        this.aliases = aliases;
    }

    public String getApiDetailUrl() {
        return apiDetailUrl;
    }

    public void setApiDetailUrl(String apiDetailUrl) {
        this.apiDetailUrl = apiDetailUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(String dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public Object getDeck() {
        return deck;
    }

    public void setDeck(Object deck) {
        this.deck = deck;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getExpectedReleaseDay() {
        return expectedReleaseDay;
    }

    public void setExpectedReleaseDay(Object expectedReleaseDay) {
        this.expectedReleaseDay = expectedReleaseDay;
    }

    public Object getExpectedReleaseMonth() {
        return expectedReleaseMonth;
    }

    public void setExpectedReleaseMonth(Object expectedReleaseMonth) {
        this.expectedReleaseMonth = expectedReleaseMonth;
    }

    public Object getExpectedReleaseQuarter() {
        return expectedReleaseQuarter;
    }

    public void setExpectedReleaseQuarter(Object expectedReleaseQuarter) {
        this.expectedReleaseQuarter = expectedReleaseQuarter;
    }

    public Object getExpectedReleaseYear() {
        return expectedReleaseYear;
    }

    public void setExpectedReleaseYear(Object expectedReleaseYear) {
        this.expectedReleaseYear = expectedReleaseYear;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfUserReviews() {
        return numberOfUserReviews;
    }

    public void setNumberOfUserReviews(Integer numberOfUserReviews) {
        this.numberOfUserReviews = numberOfUserReviews;
    }

    public Object getOriginalGameRating() {
        return originalGameRating;
    }

    public void setOriginalGameRating(Object originalGameRating) {
        this.originalGameRating = originalGameRating;
    }

    public String getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    public void setOriginalReleaseDate(String originalReleaseDate) {
        this.originalReleaseDate = originalReleaseDate;
    }


    public String getSiteDetailUrl() {
        return siteDetailUrl;
    }

    public void setSiteDetailUrl(String siteDetailUrl) {
        this.siteDetailUrl = siteDetailUrl;
    }

}