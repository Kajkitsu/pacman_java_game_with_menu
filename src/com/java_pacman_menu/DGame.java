package com.java_pacman_menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.*;

/**
 * Klasa odpowidajaca za interfejs graficzny aplikacji.
 */

public class DGame extends JPanel implements KeyListener, ActionListener {



    private BufferedImage pacmanIconImg = null;
    private Game game;
    private JFrame obj;

    private static final long serialVersionUID = 1L;

    /**
     * Konstruktor parametryczny.
     * @param game wskaznik na gre
     */

    public DGame(Game game) {
        this.game = game;

        obj = new JFrame();

        obj.setBounds(0,0,game.getPacmanMap().GetWidth()*19*2+20,game.getPacmanMap().GetHeight()*2*19+100);
        obj.setTitle("Pacman by Kajkitsu");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setLocationRelativeTo(null);
        obj.add(this);
        obj.setVisible(true);



        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        try {
            pacmanIconImg = ImageIO.read(new File("pacmanIconImg.png"));
        } catch (IOException e) {
        }


    }

    /**
     * Funckja ukrywa gre przed uzytkownikiem.
     */

    public void HideGame(){
        obj.setVisible(false);
    }

    /**
     * Funckja cyklicznego rysowania gry.
     * @param g
     */
    @Override
    public void paint(Graphics g) {

        // background
        g.setColor(Color.black);
        g.fillRect(0, 0, game.getPacmanMap().GetWidth() * 19 * 2 + 20, game.getPacmanMap().GetHeight() * 2 * 19 + 100);
        g.setColor(Color.gray);
        g.fillRect(10, 10, game.getPacmanMap().GetWidth() * 19 * 2, game.getPacmanMap().GetHeight() * 2 * 19);

        // Wypisanie liczby punktow
        g.setColor(Color.white);
        g.setFont(new Font(null, Font.BOLD, 40));
        g.drawString("Score: " + game.getScore(), 10, game.getPacmanMap().GetHeight() * 2 * 19 + 50);

        // Rysowanie mapy
        this.DrawMap(g, pacmanIconImg);

        // Rysowanie mapy punktow
        this.DrawScoreMap(g);

        // Rysowanie pacmana
        this.DrawPacman(game.getPictureCycyle(), pacmanIconImg, g);

        // Rysowanie Ghostow
        for (int i = 0; i < 4; i++) {
            this.DrawGhost(game.getPictureCycyle(), pacmanIconImg, g, i, game.getTimeForEat(),i);
        }

        // czy zabity
        if (game.isKilled()) {
            g.setColor(Color.red);
            g.setFont(new Font(null, Font.BOLD, 60));
            g.drawString("GAME OVER", 10 + ((game.getPacmanMap().GetWidth()-10) * 19), 10 + (game.getPacmanMap().GetHeight() * 19));
        }

        // czy wygrana
        if (game.isWin()) {
            g.setColor(Color.green);
            g.setFont(new Font(null, Font.BOLD, 60));
            g.drawString("YOU WIN!", 10 + ((game.getPacmanMap().GetWidth()-8) * 19) , 10 + game.getPacmanMap().GetHeight() * 19);
        }

        // czy pausa
        if (!game.isWin() && !game.isPlay() && !game.isKilled()) {
            g.setColor(Color.magenta);
            g.setFont(new Font(null, Font.BOLD, 60));
            g.drawString("PAUSE", 10 + ((game.getPacmanMap().GetWidth()-6) * 19) , 10 + game.getPacmanMap().GetHeight() * 19);
        }

        g.dispose();

    }


    /**
     * Funkcja rysowania pacmana
     * @param grapCycle  cykl pacmana
     * @param pacmanIconImg wskaznik na jpg z grafika gry
     */
    private void DrawPacman(int grapCycle, BufferedImage pacmanIconImg, Graphics g){
        int cycle = grapCycle%3;
        Pacman pacman = game.getPacmanPlayer();

        if( cycle == 0){
            g.drawImage(pacmanIconImg, 10 + pacman.GetXPosition()*2, 10 + pacman.GetYPosition() *2, 10 + pacman.GetXPosition()*2 + 2 * 19, 10 + pacman.GetYPosition() *2 + 2 * 19,43,1,62,20,null);
        }
        else {
            if( (pacman.GetXDirection() == 0 && pacman.GetYDirection() == 0) || (pacman.GetXDirection() == -1 && pacman.GetYDirection() == 0) ){
                if(cycle == 1){
                    g.drawImage(pacmanIconImg, 10 + pacman.GetXPosition()*2, 10 + pacman.GetYPosition() *2, 10 + pacman.GetXPosition()*2 + 2 * 19, 10 + pacman.GetYPosition() *2 + 2 * 19,1,1,20,20,null);
                }
                if(cycle == 2){
                    g.drawImage(pacmanIconImg, 10 + pacman.GetXPosition()*2, 10 + pacman.GetYPosition() *2, 10 + pacman.GetXPosition()*2 + 2 * 19, 10 + pacman.GetYPosition() *2 + 2 * 19,22,1,41,20,null);
                }
            }
            if( pacman.GetXDirection() == 1 && pacman.GetYDirection() == 0 ){
                if(cycle == 1){
                    g.drawImage(pacmanIconImg, 10 + pacman.GetXPosition()*2, 10 + pacman.GetYPosition() *2, 10 + pacman.GetXPosition()*2 + 2 * 19, 10 + pacman.GetYPosition() *2 + 2 * 19,1,22,20,41,null);
                }
                if(cycle == 2){
                    g.drawImage(pacmanIconImg, 10 + pacman.GetXPosition()*2, 10 + pacman.GetYPosition() *2, 10 + pacman.GetXPosition()*2 + 2 * 19, 10 + pacman.GetYPosition() *2 + 2 * 19,22,22,41,41,null);
                }
            }
            if( pacman.GetXDirection() == 0 && pacman.GetYDirection() == -1 ){
                if(cycle == 1){
                    g.drawImage(pacmanIconImg, 10 + pacman.GetXPosition()*2, 10 + pacman.GetYPosition() *2, 10 + pacman.GetXPosition()*2 + 2 * 19, 10 + pacman.GetYPosition() *2 + 2 * 19,1,43,20,62,null);
                }
                if(cycle == 2){
                    g.drawImage(pacmanIconImg, 10 + pacman.GetXPosition()*2, 10 + pacman.GetYPosition() *2, 10 + pacman.GetXPosition()*2 + 2 * 19, 10 + pacman.GetYPosition() *2 + 2 * 19,22,43,41,62,null);
                }
            }
            if( pacman.GetXDirection() == 0 && pacman.GetYDirection() == 1 ){
                if(cycle == 1){
                    g.drawImage(pacmanIconImg, 10 + pacman.GetXPosition()*2, 10 + pacman.GetYPosition() *2, 10 + pacman.GetXPosition()*2 + 2 * 19, 10 + pacman.GetYPosition() *2 + 2 * 19,1,64,20,83,null);
                }
                if(cycle == 2){
                    g.drawImage(pacmanIconImg, 10 + pacman.GetXPosition()*2, 10 + pacman.GetYPosition() *2, 10 + pacman.GetXPosition()*2 + 2 * 19, 10 + pacman.GetYPosition() *2 + 2 * 19,22,64,41,83,null);
                }
            }

        }

    }

    /**
     * Funckja rysowania ducha
     * @param grapCycle cykl rysowania gry
     * @param pacmanIconImg wskaznik na jpg z grafika gry
     * @param color color ducha
     * @param timeToDie czas przez jaki pacman moze jeszcze zjesc duchy
     * @param number indes ducha do rysownia
     */

    private void DrawGhost(int grapCycle, BufferedImage pacmanIconImg, Graphics g, int color, int timeToDie, int number){
        Ghost ghost = game.getGhost(number);
        if((timeToDie==0 && ghost.GetIsAlive()) || (ghost.GetTimeToLive()<250 && grapCycle>2)  ){
            if(ghost.GetXDirection() == 0 && ghost.GetYDirection()==-1){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,1+(grapCycle%2*21),85+(color*21),20+(grapCycle%2*21),104+(color*21),null);
            }
            if(ghost.GetXDirection() == 0 && ghost.GetYDirection()==1){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,44+(grapCycle%2*21),85+(color*21),61+(grapCycle%2*21),104+(color*21),null);
            }
            if(ghost.GetXDirection() != 1 && ghost.GetYDirection()==0){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,85+(grapCycle%2*21),85+(color*21),104+(grapCycle%2*21),104+(color*21),null);
            }
            if(ghost.GetXDirection() == 1 && ghost.GetYDirection()==0){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,127+(grapCycle%2*21),85+(color*21),146+(grapCycle%2*21),104+(color*21),null);
            }
        }
        else if((timeToDie<50 && ghost.GetIsAlive())){
            if(ghost.GetXDirection() == 0 && ghost.GetYDirection()==-1){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,1+(grapCycle%4*21),169,20+(grapCycle%4*21),188,null);
            }
            if(ghost.GetXDirection() == 0 && ghost.GetYDirection()==1){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,1+(grapCycle%4*21),169,20+(grapCycle%4*21),188,null);
            }
            if(ghost.GetXDirection() != 1 && ghost.GetYDirection()==0){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,1+(grapCycle%4*21),169,20+(grapCycle%4*21),188,null);
            }
            if(ghost.GetXDirection() == 1 && ghost.GetYDirection()==0){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,1+(grapCycle%4*21),169,20+(grapCycle%4*21),188,null);
            }
        }
        else if(timeToDie>0 && ghost.GetIsAlive()){
            if(ghost.GetXDirection() == 0 && ghost.GetYDirection()==-1){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,1+(grapCycle%2*21),169,20+(grapCycle%2*21),188,null);
            }
            if(ghost.GetXDirection() == 0 && ghost.GetYDirection()==1){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,1+(grapCycle%2*21),169,20+(grapCycle%2*21),188,null);
            }
            if(ghost.GetXDirection() != 1 && ghost.GetYDirection()==0){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,1+(grapCycle%2*21),169,20+(grapCycle%2*21),188,null);
            }
            if(ghost.GetXDirection() == 1 && ghost.GetYDirection()==0){
                g.drawImage(pacmanIconImg, 10 + ghost.GetXPosition()*2, 10 + ghost.GetYPosition() *2, 10 + ghost.GetXPosition()*2 + 2 * 19, 10 + ghost.GetYPosition() *2 + 2 * 19,1+(grapCycle%2*21),169,20+(grapCycle%2*21),188,null);
            }
        }


    }

    /**
     * Funkcja rysująca punkty na mapie.
     */
    private void DrawScoreMap(Graphics g) {
        Map mapPacman = game.getPacmanMap();
        g.setColor(Color.yellow);
        for (int y = 0; y < mapPacman.GetHeight(); y++) {
            for (int x = 0; x < mapPacman.GetWidth(); x++) {
                if (mapPacman.GetScoreMap(x,y) == 100) {
                    g.fillRect(10 + ((x + 1) * 19 * 2) - 21, 10 + ((y + 1) * 19 * 2) - 21, 4, 4);
                } else if (mapPacman.GetScoreMap(x,y) == 500) {
                    g.fillRect(10 + ((x + 1) * 19 * 2) - 23, 10 + ((y + 1) * 19 * 2) - 23, 8, 8);
                }
            }
        }
    }

    /**
     * Funkcja rysujaca mape
     * @param pacmanIconImg wskanik na jpg z grafika z gry
     */
    private void DrawMap(Graphics g, BufferedImage pacmanIconImg) {
        //narozniki
        Map mapPacman = game.getPacmanMap();

        //skret prawo-dol
        g.drawImage(pacmanIconImg, 10, 10, 10 + 19*2, 10 + 19*2,106,43,125,62,null);
        //skret lewo-dol
        g.drawImage(pacmanIconImg, 10+((mapPacman.GetWidth()-1)*19)*2, 10 , 10+(((mapPacman.GetWidth()-1)+1)*19)*2, 10 + 19*2,85,43,104,62,null);
        //skret prawo-gora
        g.drawImage(pacmanIconImg, 10, 10+((mapPacman.GetHeight()-1)*19)*2, 10 + 19*2, 10+(((mapPacman.GetHeight()-1)+1)*19)*2,148,43,167,62,null);
        //skret lewo-gora
        g.drawImage(pacmanIconImg, 10+((mapPacman.GetWidth()-1)*19)*2, 10+((mapPacman.GetHeight()-1)*19)*2, 10+(((mapPacman.GetWidth()-1)+1)*19)*2, 10+(((mapPacman.GetHeight()-1)+1)*19)*2,127,43,146,62,null);


        //pasek gorny
        for (int x = 1,y = 0; x < mapPacman.GetWidth()-1; x++){
            //pusty
            if (mapPacman.GetMap(x, y) != 0) {
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,190,22,209,41,null);
            }
            //pionowy
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,1,104,20,null);
            }
            //poziomy
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,1,125,20,null);
            }
            //koniec od gory
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,148,1,167,20,null);
            }
            //rozgalezienie od lewej
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,22,125,41,null);
            }
            //rozgalezienie od prawej
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,148,22,167,41,null);
            }
            //rozgalezienie od dolu
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,169,22,188,41,null);
            }
            //skret lewo-gora
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,127,43,146,62,null);
            }
            //skret prawo-gora
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,148,43,167,62,null);
            }
        }

        //pasek dolny
        for (int x = 1,y =mapPacman.GetHeight()-1; x < mapPacman.GetWidth()-1; x++){
            //pusty
            if (mapPacman.GetMap(x, y) != 0) {
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,190,22,209,41,null);
            }
            //pionowy
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y-1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,1,104,20,null);
            }
            //poziomy
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y-1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,1,125,20,null);
            }
            //koniec od dolu
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y-1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,190,1,209,20,null);
            }
            //rozgalezienie od lewej
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y-1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,22,125,41,null);
            }
            //rozgalezienie od gory
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y-1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,127,22,146,41,null);
            }
            //rozgalezienie od prawej
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y-1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,148,22,167,41,null);
            }
            //skret lewo-dol
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y-1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,43,104,62,null);
            }
            //skret prawo-dol
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y-1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,43,125,62,null);
            }
        }

        //pasek lewy
        for (int y = 1,x = 0; y < mapPacman.GetHeight()-1; y++){
            //pusty
            if (mapPacman.GetMap(x, y) != 0) {
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,190,22,209,41,null);
            }
            //koniec od lewej strony
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x, y-1) != 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,127,1,146,20,null);
            }
            //pionowy
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x, y-1) == 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,1,104,20,null);
            }
            //poziomy
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x, y-1) != 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,1,125,20,null);
            }
            //rozgalezienie od gory
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x, y-1) == 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,127,22,146,41,null);
            }
            //rozgalezienie od prawej
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x, y-1) == 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,148,22,167,41,null);
            }
            //rozgalezienie od dolu
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) == 0
                    && mapPacman.GetMap(x, y-1) != 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,169,22,188,41,null);
            }
            //skret lewo-dol
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x, y-1) != 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,43,104,62,null);
            }
            //skret lewo-gora
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x+1, y) != 0
                    && mapPacman.GetMap(x, y-1) == 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,127,43,146,62,null);
            }

        }

        //pasek prawy
        for (int y = 1,x = mapPacman.GetWidth()-1; y < mapPacman.GetHeight()-1; y++){
            //puste
            if (mapPacman.GetMap(x, y) != 0) {
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,190,22,209,41,null);
            }
            //kolko
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y-1) != 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,22,104,41,null);
            }
            //koniec od prawej strony
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y-1) != 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,169,1,188,20,null);
            }
            //pionowy
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y-1) == 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,1,104,20,null);
            }
            //poziomy
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y-1) != 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,1,125,20,null);
            }
            //rozgalezienie od lewej
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y-1) == 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,22,125,41,null);
            }
            //rozgalezienie od gory
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y-1) == 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,127,22,146,41,null);
            }
            //rozgalezienie od dolu
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x-1, y) == 0
                    && mapPacman.GetMap(x, y-1) != 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,169,22,188,41,null);
            }
            //skret prawo-dol
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y-1) != 0
                    && mapPacman.GetMap(x, y+1) == 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,43,125,62,null);
            }
            //skret prawo-gora
            if (mapPacman.GetMap(x, y) == 0
                    && mapPacman.GetMap(x-1, y) != 0
                    && mapPacman.GetMap(x, y-1) == 0
                    && mapPacman.GetMap(x, y+1) != 0
            ){
                g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,148,43,167,62,null);
            }
        }

        //srodek
        for (int y = 1; y < mapPacman.GetHeight()-1; y++) {
            for (int x = 1; x < mapPacman.GetWidth()-1; x++) {
                //puste
                if (mapPacman.GetMap(x, y) != 0) {
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,190,22,209,41,null);
                }
                //kolko
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) != 0
                        && mapPacman.GetMap(x-1, y) != 0
                        && mapPacman.GetMap(x, y-1) != 0
                        && mapPacman.GetMap(x, y+1) != 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,211,1,230,20,null);
                }
                //koniec od lewej strony
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) != 0
                        && mapPacman.GetMap(x-1, y) == 0
                        && mapPacman.GetMap(x, y-1) != 0
                        && mapPacman.GetMap(x, y+1) != 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,127,1,146,20,null);
                }
                //koniec od prawej strony
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) == 0
                        && mapPacman.GetMap(x-1, y) != 0
                        && mapPacman.GetMap(x, y-1) != 0
                        && mapPacman.GetMap(x, y+1) != 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,169,1,188,20,null);
                }
                //pionowy
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) != 0
                        && mapPacman.GetMap(x-1, y) != 0
                        && mapPacman.GetMap(x, y-1) == 0
                        && mapPacman.GetMap(x, y+1) == 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,1,104,20,null);
                }
                //poziomy
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) == 0
                        && mapPacman.GetMap(x-1, y) == 0
                        && mapPacman.GetMap(x, y-1) != 0
                        && mapPacman.GetMap(x, y+1) != 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,1,125,20,null);
                }
                //koniec od gory
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) != 0
                        && mapPacman.GetMap(x-1, y) != 0
                        && mapPacman.GetMap(x, y-1) == 0
                        && mapPacman.GetMap(x, y+1) != 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,148,1,167,20,null);
                }
                //koniec od dolu
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) != 0
                        && mapPacman.GetMap(x-1, y) != 0
                        && mapPacman.GetMap(x, y-1) != 0
                        && mapPacman.GetMap(x, y+1) == 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,190,1,209,20,null);
                }
                //skrzyzowanie
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) == 0
                        && mapPacman.GetMap(x-1, y) == 0
                        && mapPacman.GetMap(x, y-1) == 0
                        && mapPacman.GetMap(x, y+1) == 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,22,104,41,null);
                }
                //rozgalezienie od lewej
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) != 0
                        && mapPacman.GetMap(x-1, y) == 0
                        && mapPacman.GetMap(x, y-1) == 0
                        && mapPacman.GetMap(x, y+1) == 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,22,125,41,null);
                }
                //rozgalezienie od gory
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) == 0
                        && mapPacman.GetMap(x-1, y) == 0
                        && mapPacman.GetMap(x, y-1) == 0
                        && mapPacman.GetMap(x, y+1) != 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,127,22,146,41,null);
                }
                //rozgalezienie od prawej
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) == 0
                        && mapPacman.GetMap(x-1, y) != 0
                        && mapPacman.GetMap(x, y-1) == 0
                        && mapPacman.GetMap(x, y+1) == 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,148,22,167,41,null);
                }
                //rozgalezienie od dolu
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) == 0
                        && mapPacman.GetMap(x-1, y) == 0
                        && mapPacman.GetMap(x, y-1) != 0
                        && mapPacman.GetMap(x, y+1) == 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,169,22,188,41,null);
                }
                //skret lewo-dol
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) != 0
                        && mapPacman.GetMap(x-1, y) == 0
                        && mapPacman.GetMap(x, y-1) != 0
                        && mapPacman.GetMap(x, y+1) == 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,85,43,104,62,null);
                }
                //skret prawo-dol
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) == 0
                        && mapPacman.GetMap(x-1, y) != 0
                        && mapPacman.GetMap(x, y-1) != 0
                        && mapPacman.GetMap(x, y+1) == 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,106,43,125,62,null);
                }
                //skret lewo-gora
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) != 0
                        && mapPacman.GetMap(x-1, y) == 0
                        && mapPacman.GetMap(x, y-1) == 0
                        && mapPacman.GetMap(x, y+1) != 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,127,43,146,62,null);
                }
                //skret prawo-gora
                if (mapPacman.GetMap(x, y) == 0
                        && mapPacman.GetMap(x+1, y) == 0
                        && mapPacman.GetMap(x-1, y) != 0
                        && mapPacman.GetMap(x, y-1) == 0
                        && mapPacman.GetMap(x, y+1) != 0
                ){
                    g.drawImage(pacmanIconImg, 10+(x*19)*2, 10+(y*19)*2, 10+((x+1)*19)*2, 10+((y+1)*19)*2,148,43,167,62,null);
                }
            }
        }
    }


    /**
     * Funckja dziedzczna odpowidajaca za progres aplikacji
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // System.out.println("void actionPerformed(ActionEvent e)");

        game.Action();
        repaint();

    }


    /**
     * Funckja dziedziczna odpowidajaca za progres aplikacji
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }




    /**
     * Funckja dziedziczna odpowidajaca za progres aplikacji - wciskanie klawisza
     */
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.setWantedXDirection(1);
            game.setWantedYDirection(0);

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
           game.setWantedXDirection(-1);
            game.setWantedYDirection(0);

        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
           game.setWantedXDirection(0);
            game.setWantedYDirection(-1);

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
           game.setWantedXDirection(0);
            game.setWantedYDirection(1);

        }

    }

    /**
     * Funckja dziedziczna odpowidajaca za progres aplikacji - zwolnienie klawisza
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.PauseGame();
        }

    }

}