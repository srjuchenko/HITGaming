package com.example.hitgaming.models;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Trailer {

    @SerializedName("480")
    @Expose
    private String _480;
    @SerializedName("max")
    @Expose
    private String max;

    public String get480() {
        return _480;
    }

    public void set480(String _480) {
        this._480 = _480;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

}