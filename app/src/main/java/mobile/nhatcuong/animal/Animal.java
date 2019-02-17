package mobile.nhatcuong.animal;

import java.io.Serializable;

public class Animal implements Serializable {
    private String name;
    private int overViewImage;
    private int Image;
    private int animalVoice;
    private int humanVoice;

    public Animal(String name, int image) {
        this.name = name;
        Image = image;
    }

    public Animal(String name, int overViewImage, int image, int animalVoice, int humanVoice) {
        this.name = name;
        this.overViewImage = overViewImage;
        Image = image;
        this.animalVoice = animalVoice;
        this.humanVoice = humanVoice;
    }

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOverViewImage() {
        return overViewImage;
    }

    public void setOverViewImage(int overViewImage) {
        this.overViewImage = overViewImage;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getAnimalVoice() {
        return animalVoice;
    }

    public void setAnimalVoice(int animalVoice) {
        this.animalVoice = animalVoice;
    }

    public int getHumanVoice() {
        return humanVoice;
    }

    public void setHumanVoice(int humanVoice) {
        this.humanVoice = humanVoice;
    }
}
