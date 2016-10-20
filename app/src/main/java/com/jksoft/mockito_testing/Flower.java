package com.jksoft.mockito_testing;

/**
 * Created by Jirka on 20.10.2016.
 */

public class Flower {
    private String mColor;
    private String mName;
    private int mNumberOfLeafs;


    public Flower(String color, String name) {
        this.mColor = color;
        this.mName = name;
        this.mNumberOfLeafs = 10;
    }

    public void sleep () {
        System.out.println("Flower " + mName + " is sleeping");
    }

    public Flower(String color, String name, int numberOfLeafs) {
        this.mColor = color;
        this.mName = name;
        this.mNumberOfLeafs = numberOfLeafs;
    }

    /**
     * returns true if  ((numberOfLeafs > 5) && nameContains.contains("asparag") && color.equalsIgnoreCase("red"))
     *
     * @param numberOfLeafs
     * @param nameContains
     * @param color
     * @return
     */
    public boolean smellsEstimate (int numberOfLeafs, String nameContains, String color) {
        nameContains = nameContains.toLowerCase();
        if ((numberOfLeafs > 5) && nameContains.contains("asparag") && color.equalsIgnoreCase("red")) {
            return true;
        }
        return false;
    }


    public String getColor() {
        return mColor;
    }

    public void setColor(String Color) {
        this.mColor = Color;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String Name) {
        this.mName = Name;
    }

    public String sayColor () {
        return mColor;
    }

    public String sayName () {
        return mName;
    }

    public int getNumberOfLeafs() {
        return mNumberOfLeafs;
    }

    public void setNumberOfLeafs(int numberOfLeafs) {
        this.mNumberOfLeafs = numberOfLeafs;
    }


}
