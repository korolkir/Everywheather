package com.example.korolkir.everywheatherdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by korolkir on 25.07.16.
 */
public class Weather {

    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;


    /**
     *
     *
     * @return
     * The main
     */
    public String getMain() {
        return main;
    }

    /**
     *
     * @param main
     * The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}

