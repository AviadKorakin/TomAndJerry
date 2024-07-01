package com.example.tomandjerry.GameUtilities;
public class MainCharacter {
    private int image;
    private int location;
    private final int length;


    public MainCharacter(int image, int location, int length) {
        this.image = image;
        this.location = location;
        this.length=length;
    }
    public int moveLeft()
    {
        return location==0? location=length-1 : --location;
    }
    public int moveRight()
    {
        return location==length-1? location=0 : ++location;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public int getLocation() {
        return location;
    }

}
