package com.example.softwareengineering.statistics;

public class Score implements Comparable<Score> {

    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public void addScore() { score += 10; }

    @Override
    public int compareTo(Score o) {
        if (this.score > o.score) {
            return -1;
        } else {
            return 1;
        }
    }
}
