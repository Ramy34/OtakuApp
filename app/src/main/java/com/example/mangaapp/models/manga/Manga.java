package com.example.mangaapp.models.manga;

import com.example.mangaapp.models.generic.LinkSelf;

import java.io.Serializable;

public class Manga implements Serializable {
    private int id;
    private String type;
    private LinkSelf links;
    private MangaAttributes attributes;
    private MangaRelationships relationships;

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

    public MangaAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(MangaAttributes attributes) {
        this.attributes = attributes;
    }

    public MangaRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(MangaRelationships relationships) {
        this.relationships = relationships;
    }
}