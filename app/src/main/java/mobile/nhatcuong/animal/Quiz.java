package mobile.nhatcuong.animal;

import java.util.ArrayList;

public class Quiz {
    private String question;
    private ArrayList<Animal> Anwsers;
    private Animal rightAnwsers;

    public Quiz( ArrayList<Animal> anwsers) {
        this.question = "Đố bé đây là con gì";
        Anwsers = anwsers;
        rightAnwsers = anwsers.get(0);
    }

    public boolean hanldeClick(String clickValue){
        if(clickValue.equals(rightAnwsers.getName())){
            return true;
        }else{
            return false;
        }
    }

    public Quiz(String question) {
        this.question = question;
    }

    public Quiz() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Animal> getAnwsers() {
        return Anwsers;
    }

    public void setAnwsers(ArrayList<Animal> anwsers) {
        Anwsers = anwsers;
    }

    public Animal getRightAnwsers() {
        return rightAnwsers;
    }

    public void setRightAnwsers(Animal rightAnwsers) {
        this.rightAnwsers = rightAnwsers;
    }
}
