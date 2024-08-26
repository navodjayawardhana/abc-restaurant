package com.abc.restaurant.model;

public class Promotion {
    private int id;
    private String title;
    private int prestige;
    private String description;
    private String imagePath;

    public Promotion() {}

    public Promotion(int id, String title, int prestige, String description, String imagePath) {
        this.id = id;
        this.title = title;
        this.prestige = prestige;
        this.description = description;
        this.imagePath = imagePath;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
