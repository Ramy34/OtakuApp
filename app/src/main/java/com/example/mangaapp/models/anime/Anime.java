package com.example.mangaapp.models.anime;

import com.example.mangaapp.models.generic.LinkSelf;

public class Anime {

    private int id;
    private String type;
    private LinkSelf links;
    private AnimeAttributes attributes;
    private AnimeRelationships relationships;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinkSelf getLinks() {
        return links;
    }

    public void setLinks(LinkSelf links) {
        this.links = links;
    }

    public AnimeAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(AnimeAttributes attributes) {
        this.attributes = attributes;
    }

    public AnimeRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(AnimeRelationships relationships) {
        this.relationships = relationships;
    }
}
