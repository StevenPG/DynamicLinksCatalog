package com.stevengantz;

/**
 * Created by Steven Gantz on 2/15/2017.
 *
 * This file is a model for the JSON reply object
 * which contains a specific configuration that the
 * React JS client will use to dynamically generate
 * a page of links.
 */
public class LinksConfiguration {

    /**
     * The title along the top of the web page
     */
    private String title;

    /**
     * Generic constructor
     */
    public LinksConfiguration() {
        this.title = "title";
    }

    /**
     * Retrieve title
     * @return currently stored title
     */
    public String getTitle(){
        return this.title;
    }
}
