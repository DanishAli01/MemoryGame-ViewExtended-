package com.example.danishali2875170.Assignment02;
/**
 * This is a hepler Player class associated with PlayerControl class.
 * @author Danish Ali
 * @studentNumber 2875170
 * @since 2018-11-02
 * @version 1.0
 */

public class Player {
    private String name;
    private int clicks;
    private int score;
    /**
     * Constructor initializes instance variables
     * @param name
     */
    public Player(String name) {
        this.name = name;
        clicks = 2;
        score = 0;
    }

    /**
     * Method Get Name of Player
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Method set Name of Player
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Method get clicks of Player
     * @return int
     */
    public int getClicks() {
        return clicks;
    }
    /**
     * Method set clicks of Player
     * @param clicks
     */
    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
    /**
     * Method get clicks of Player
     * @return int
     */
    public int getScore() {
        return score;
    }
    /**
     * Method set score of Player
     * @param score
     */
    public void setScore(int score) {
        this.score += score;
    }
    /**
     * Method reduce clicks of Player
     */
    public void reduceClicks(){
        this.clicks-=1;
    }


}
