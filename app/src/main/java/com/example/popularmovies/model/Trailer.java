package com.example.popularmovies.model;

public class Trailer {
    private String name;
    private String site;
    private String key;
    private String url;
    private String type;

    public Trailer(String name, String site,String key,String type){
        this.name = name;
        this.site = site;
        this.key = key;
        this.url = "https://www.youtube.com/watch?v=" + key;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }

    public String getType(){
        return type;
    }
}

