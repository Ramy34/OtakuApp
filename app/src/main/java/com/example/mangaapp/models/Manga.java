package com.example.mangaapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Manga implements Serializable {
    private int id;
    private String type;
    private LinkSelf links;
    private Attributes attributes;
    private Relationships relationships;

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

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Relationships getRelationships() {
        return relationships;
    }

    public void setRelationships(Relationships relationships) {
        this.relationships = relationships;
    }
}