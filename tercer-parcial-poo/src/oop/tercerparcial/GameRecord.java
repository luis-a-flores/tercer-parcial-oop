package oop.tercerparcial;

import java.util.Objects;

public class GameRecord {
    //attributes
    private String name;
    private int score;

    //constructor
    public GameRecord()
    {

    }

    public GameRecord(int score, String name)
    {
        this.score = score;
        this.name = name;
    }


    //setters
    public void setPlayerName(String name)
    {

        this.name = name;

    }

    public void setScore(int score)
    {
        this.score = score;
    }

    //getters
    public int getScore()
    {
        return score;
    }

    public String getPlayerName()
    {
        return name;
    }

    //others
    @Override
    public String toString() {
        return "GameRecord{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRecord that = (GameRecord) o;
        return score == that.score &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score);
    }
}
