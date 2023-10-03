package com.example.hitgaming.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class APIResult {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<GameResult> results;
    @SerializedName("seo_title")
    @Expose
    private String seoTitle;
    @SerializedName("seo_description")
    @Expose
    private String seoDescription;
    @SerializedName("seo_keywords")
    @Expose
    private String seoKeywords;
    @SerializedName("seo_h1")
    @Expose
    private String seoH1;
    @SerializedName("noindex")
    @Expose
    private Boolean noindex;
    @SerializedName("nofollow")
    @Expose
    private Boolean nofollow;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("nofollow_collections")
    @Expose
    private List<String> nofollowCollections;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<GameResult> getResults() {
        return results;
    }

    public void setResults(List<GameResult> results) {
        this.results = results;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoH1() {
        return seoH1;
    }

    public void setSeoH1(String seoH1) {
        this.seoH1 = seoH1;
    }

    public Boolean getNoindex() {
        return noindex;
    }

    public void setNoindex(Boolean noindex) {
        this.noindex = noindex;
    }

    public Boolean getNofollow() {
        return nofollow;
    }

    public void setNofollow(Boolean nofollow) {
        this.nofollow = nofollow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<String> getNofollowCollections() {
        return nofollowCollections;
    }

    public void setNofollowCollections(List<String> nofollowCollections) {
        this.nofollowCollections = nofollowCollections;
    }

}