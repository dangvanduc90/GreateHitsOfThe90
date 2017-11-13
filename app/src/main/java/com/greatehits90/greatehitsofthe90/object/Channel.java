package com.greatehits90.greatehitsofthe90.object;

public class Channel {
    private String title;
    private String artist;
    private String image;
    private String url;

    public Channel(String title, String artist, String image, String url) {
        this.title = title;
        this.artist = artist;
        this.image = image;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
