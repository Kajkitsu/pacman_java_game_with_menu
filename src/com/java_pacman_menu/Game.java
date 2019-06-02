package com.java_pacman_menu;

import javax.swing.Timer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Klasa gry pacman,
 * odpowiada za ciag zdarzeń i logike rozgrywki.
 */

public class Game {
    private boolean play = false;
    private boolean killed = false;
    private boolean win = false;

    private Timer timer;
    private int delay = 15;
    private int cycle = 0;
    private int pictureCycyle = 1;
    private int timeForEat = 0;

    private Pacman pacmanPlayer;
    private Ghost ghost[] = new Ghost[4];

    private int wantedXDirection = 0;
    private int wantedYDirection = 0;

    private int score = 0;

    private Map mapPacman = null;

    String mapName;
    String nickname;
    DGame dgame;
    DMenu dMenu;

    /**
     * Konstruktor paramteryczny gry.
     * @param mapName sciezka do mapy gry
     * @param nickname nazwa gracza
     * @param dMenu wskaznik na menu
     */
    public Game(String mapName, String nickname, DMenu dMenu) {
        this.nickname = nickname;
        this.mapName = mapName;
        this.mapPacman = new Map(mapName+".txt");
        this.dgame = new DGame(this);
        this.dMenu = dMenu;
        this.timer = new Timer(delay, dgame);
        this.timer.start();

        // tworzenie mapy punktow
        this.getPacmanMap().MakeScoreMap();

        // tworzenie Pacmana i Ghostow
        pacmanPlayer = new Pacman(this.getPacmanMap().GetPacmanSquareX() * 19, this.getPacmanMap().GetPacmanSquareY() * 19, this.getPacmanMap());
        for (int i = 0; i < 4; i++) {
            ghost[i] = new Ghost(this.getPacmanMap().GetGhostSquareX(i) * 19, this.getPacmanMap().GetGhostSquareY(i) * 19, this.getPacmanMap());
        }
    }

    /**
     * Funkcja progresu akcji gry.
     * Odpowiada za odpowiedni uporządkowany ciąg wydarzen,
     * zakonczenie gry i zbieranie punktów
     */

    public void Action(){
        timer.start();

        if (play && !killed) {

            pacmanPlayer.TryToChangeDirectionOfPacman(wantedXDirection, wantedYDirection, this.getPacmanMap());



            //cykl gry
            cycle++;
            if (cycle == 90)
                cycle = 0;


            //cykl zmiany obrazka
            if (cycle % 5 == 0) {
                pictureCycyle++;
            }
            if (pictureCycyle == 6)
                pictureCycyle = 0;

            // poruszanie sie pacmana
            if (cycle % 15 != 0) {
                pacmanPlayer.Move(this);
            }

            //odradzanie sie ghostow
            for(int i = 0; i < 4; i++) {
               ghost[i].RestoreHealth();
            }

            // poruszanie sie ghostow
            for(int i = 0; i < 4; i++) {
                ghost[i].Move(this);

            }

            // zdobycie punktow przez pacmana
            int points = this.getPacmanMap().TakeScoreFrom(pacmanPlayer.GetXPosition(), pacmanPlayer.GetYPosition());
            if (points == 500) {
                timeForEat = 250;
            }
            score = points + score;
            if (timeForEat > 0) {
                for (int i = 0; i < 4; i++) {
                    if (pacmanPlayer.TryToKill(ghost[i]))
                       // score = score + 1000;
                        ;
                }
                timeForEat--;
            }

            //sprawdzenie czy wszystkie punkt z mapy zdobyte
            if (this.getPacmanMap().CheckIsScoreMapEmpty()) {
                win = true;
                play = false;

            }

            //proba zabicia pacmana
            for(int i = 0; i < 4; i++) {
                if (timeForEat == 0) {
                    if (ghost[i].TryToKill(pacmanPlayer)) {
                        killed = true;
                        play = false;
                        break;
                    }
                }
            }
        }
    }


    /**
     * Funkcja konca gry.
     * Zapisuje ona wynik gry i otwiera menu gry.
     */

    public void EndGame(){
        ScoreBoard scoreBoard = new ScoreBoard();

        String pattern = "dd.MM.yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();

        scoreBoard.AddScore(nickname,df.format(today),mapName,score);
        dgame.HideGame();
        dMenu.setVisible(true);

    }

    /**
     * Funkcja ustawia pozadany kierunek pacmana w plaszczyznie poziomej
     * @param wantedXDirection pozadany kierunek w plaszczyznie poziomej
     */
    public void setWantedXDirection(int wantedXDirection) {
        this.wantedXDirection = wantedXDirection;
    }

    /**
     * Funkcja ustawia pozadany kierunek pacmana w plaszczyznie pionowej
     * @param wantedYDirection pozadany kierunek w plaszczyznie pionowej
     */
    public void setWantedYDirection(int wantedYDirection) {
        this.wantedYDirection = wantedYDirection;
    }

    /**
     * Wstrzymanie gry.
     */
    public void PauseGame(){
        if(killed == true || win ==true ){
            this.EndGame();
        }
        if(!this.isKilled()){
            play = !play;
        }
    }

    /**
     * Funkcja zwraca prawde jezeli gra trwa.
     * @return czy gra trwa
     */
    public boolean isPlay() {
        return play;
    }

    /**
     * Funkcja zwraca prawde jezeli gracz jest zabity
     * @return czy zabity
     */
    public boolean isKilled() {
        return killed;
    }


    /**
     * Funkcja zwraca prawde jezeli gracz wygral
     * @return czy wygrana
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Funkcja zwraca cykl rysowania obrazkow
     * @return cykl rysowania obrazkow
     */
    public int getPictureCycyle() {
        return pictureCycyle;
    }

    /**
     * Funkcja zwraca czas jaki zostal graczowi w ktorym moze zjesc duchy.
     * @return czas jaki zostal graczowi w ktorym moze zjesc duchy
     */
    public int getTimeForEat() {
        return timeForEat;
    }

    /**
     * Funkcja zwraca wskanik na pacmana gracza.
     * @return wskanik na pacmana gracza
     */
    public Pacman getPacmanPlayer() {
        return pacmanPlayer;
    }

    /**
     * Funkcja zwraca wskanik na ducha o indeksie i.
     * @param i numer ducha
     * @return wskanik na ducha o indeksie i
     */
    public Ghost getGhost(int i) {
        return ghost[i];
    }


    /**
     * Funkcja zwraca liczbe punktow zdobytch przez gracza w tej grze.
     * @return iczbe punktow zdobytch przez gracza w tej grze
     */
    public int getScore() {
        return score;
    }

    /**
     * Funkcja zwraca wskaznik na mape gry
     * @return wskaznik na mape gry
     */
    public Map getPacmanMap() {
        return mapPacman;
    }


}
