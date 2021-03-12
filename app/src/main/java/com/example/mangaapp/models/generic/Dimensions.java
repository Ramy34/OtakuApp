package com.example.mangaapp.models.generic;

public class Dimensions {
    private Tamano tiny;
    private Tamano small;
    private Tamano medium;
    private Tamano large;

    public Tamano getTiny() {
        return tiny;
    }

    public void setTiny(Tamano tiny) {
        this.tiny = tiny;
    }

    public Tamano getSmall() {
        return small;
    }

    public void setSmall(Tamano small) {
        this.small = small;
    }

    public Tamano getMedium() {
        return medium;
    }

    public void setMedium(Tamano medium) {
        this.medium = medium;
    }

    public Tamano getLarge() {
        return large;
    }

    public void setLarge(Tamano large) {
        this.large = large;
    }
}