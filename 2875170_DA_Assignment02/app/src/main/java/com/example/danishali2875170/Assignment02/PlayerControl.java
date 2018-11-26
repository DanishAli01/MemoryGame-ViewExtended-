package com.example.danishali2875170.Assignment02;
/**
 * This is a hepler Player class associated with Player class.
 * @author Danish Ali
 * @studentNumber 2875170
 * @since 2018-11-02
 * @version 1.0
 */

public class PlayerControl {

    private Player playerone;
    private Player playertwo;
    private Player Onstrike;
    /**
     * Constructor initializes instance variables
     */
    public PlayerControl() {

        playerone = new Player(MainActvity.et_player1);
        playertwo = new Player(MainActvity.et_player2);
        Onstrike = playerone;
    }

    /**
     * Updaptes Score of Onstrick player
     * @param p
     * @param s
     */

    public void UpdateScore(Player p, int s) {
        p.setScore(s);
    }

    /**
     * Switch player turns
     * @return boolean
     */
    public boolean SwitchPlayer() {
        if (Onstrike == playerone) {
            Onstrike = playertwo;
            return true;
        }
        if (Onstrike == playertwo) {
            Onstrike = playerone;
            return true;
        } else {
            return false;
        }
    }
    /**
     * Reset player Clciks to 2
     * @param p
     */

    public void ResetClicks(Player p) {
        p.setClicks(2);
    }
    /**
     * get player Onstrick
     * @return Player
     */
    public Player getPlayer() {
        return Onstrike;
    }
}
