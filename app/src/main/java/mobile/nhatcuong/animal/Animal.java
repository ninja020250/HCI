package mobile.nhatcuong.animal;

import java.io.Serializable;

public class Animal implements Serializable {
    private String name;
    private int Image;
    private int animalVoice = 0;
    private int humanVoice = 0;

    public Animal(String name, int image, int animalVoice) {
        this.name = name;
        Image = image;
        this.animalVoice = animalVoice;
    }

    public Animal(String name, int image, int animalVoice, int humanVoice) {
        this.name = name;
        this.Image = image;
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
