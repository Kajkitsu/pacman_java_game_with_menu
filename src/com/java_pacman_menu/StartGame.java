package com.java_pacman_menu;

/**
 * Klasa uruchamiajaca gre
 */
public class StartGame {
    public static void main(String[] args) {

        if (args.length == 1) {
            Game game = new Game(args[0],"guest",null);
        }
        else {
            DMenu menu = new DMenu();
        }
    }
}
