package mobile.nhatcuong.animal;

import java.io.Serializable;

public class Animal implements Serializable {
    private String id;
    private String name;
    private int Image;
    private int animalVoice = 0;
    private int humanVoice = 0;
    private String animalVoiceURL= "";
    private String humanVoiceURL = "";
    private String imageURL = "";

    public Animal(String id, String name, String animalVoiceURL, String humanVoiceURL, String imageURL) {
        this.id = id;
        this.name = name;
        this.animalVoiceURL = animalVoiceURL;
        this.humanVoiceURL = humanVoiceURL;
        this.imageURL = imageURL;
    }

    public Animal(String id, String name, int animalVoice, int humanVoice, String imageURL) {
        this.id = id;
        this.name = name;
        this.animalVoice = animalVoice;
        this.humanVoice = humanVoice;
        this.imageURL = imageURL;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnimalVoiceURL() {
        return animalVoiceURL;
    }

    public void setAnimalVoiceURL(String animalVoiceURL) {
        this.animalVoiceURL = animalVoiceURL;
    }

    public String getHumanVoiceURL() {
        return humanVoiceURL;
    }

    public void setHumanVoiceURL(String humanVoiceURL) {
        this.humanVoiceURL = humanVoiceURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getNameHumanAudio(){
        String[] fileName =  getHumanVoiceURL().split("/");
        return fileName[fileName.length -1];

    }
    public String getNameAnimalAudio(){
        String[] fileName =  getAnimalVoiceURL().split("/");
        return fileName[fileName.length -1];

    }
}
