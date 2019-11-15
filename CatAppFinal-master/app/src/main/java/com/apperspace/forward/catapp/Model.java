package com.apperspace.forward.catapp;

public class Model
{
    String id;
    String name;
    String imageUrl;
    String description;
    String weightImperial;
    String weightMetric;
    String Temperament;
    String origin;
    String lifeSpan;
    String wikiLink;
    int dogFrindlinesslevel;

    public Model()
    {
        this.imageUrl="";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeightImperial() {
        return weightImperial;
    }

    public void setWeightImperial(String weightImperial) {
        this.weightImperial = weightImperial;
    }

    public String getTemperament() {
        return Temperament;
    }

    public void setTemperament(String temperament) {
        Temperament = temperament;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public int getDogFrindlinesslevel() {
        return dogFrindlinesslevel;
    }

    public void setDogFrindlinesslevel(int dogFrindlinesslevel) {
        this.dogFrindlinesslevel = dogFrindlinesslevel;
    }

    public String getWeightMetric() {
        return weightMetric;
    }

    public void setWeightMetric(String weightMetric) {
        this.weightMetric = weightMetric;
    }
}
