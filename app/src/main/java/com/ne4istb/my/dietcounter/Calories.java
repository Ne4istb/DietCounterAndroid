package com.ne4istb.my.dietcounter;

public class Calories {

    private Float carbohydrates;
    private Float proteins;
    private Float fat;

    public Calories(Float carbohydrates, Float proteins, Float fat) {
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fat = fat;
    }

    public Float getCarbohydrates() {
        return carbohydrates;
    }

    public Float getProteins() {
        return proteins;
    }

    public Float getFat() {
        return fat;
    }
}
