package com.java_pacman_menu;

/**
 * Klasa odpowidajaca za strukture punktow.
 */
public class Score {
    public String nickname;
    public String date;
    public String map;
    public int score;

    public int getScore(){
        return this.score;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDate() {
        return date;
    }

    public String getMap() {
        return map;
    }

    @Override
    public String toString() {
        return "Score{" +
                "nickname='" + nickname + '\'' +
                ", date='" + date + '\'' +
                ", map='" + map + '\'' +
                ", score=" + score +
                '}';
    }
}
