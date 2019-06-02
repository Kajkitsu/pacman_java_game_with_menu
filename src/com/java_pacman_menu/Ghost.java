package com.java_pacman_menu;

import java.util.Random;

/**
 * Klasa duchów z gry,
 * zawiera specjalistyczna fizyke i sztuczna inteligencje duchow.
 */
public class Ghost extends Hero {

    private Random rand = new Random();
    private int timeToLive ;

    /**
     * Konstruktor ducha.
     * @param x wspolrzedna spawnu x
     * @param y wspolrzedna spawnu y
     * @param map wskaznik na mape na ktorej duch sie pojawia
     */
    public Ghost(int x, int y, Map map) {
        this.xPosition = x;
        this.yPosition = y;
        this.yDirection=0;
        this.xDirection=0;
        this.ChangeToRandomDirection(map);
        this.timeToLive=1000;
    }

    /**
     * Funckja odpowiadajaca za przywrocenie ducha do gry.
     */
    public void RestoreHealth(){
        if(!this.GetIsAlive()){
            if(timeToLive<=0){
                timeToLive = 1000;
                this.isAlive=true;
            }
            this.timeToLive--;

        }
    }

    /**
     * Funckcja zwraca czas do odrodzenia ducha
     */
    public int GetTimeToLive(){
        return timeToLive;
    }


    /**
     * Funkcja wykrywajaca pacmana.
     * Ducha sprawdza czy miedzy nim a pacmanem w lini poziomej
     * lub pionowej nie ma zadnej przeszkody, jezeli tak to zmienia
     * kierunek na pacmana.
     * @param pacmanPlayer pacman ktory jest scigany
     * @param mapPacman wskaznik na mape
     * @return czy wykryto pacmana i zmieniono kierunek
     */
    public boolean DetectPacman(Pacman pacmanPlayer, Map mapPacman) {
        boolean isObstacle = false;
        boolean isDetect = false;
        if(this.xPosition%19==0 && this.yPosition%19==0){
            if (pacmanPlayer.GetXPosition() == this.xPosition && this.isAlive) {
                if (pacmanPlayer.GetYPosition() > this.yPosition) {
                    int j = this.yPosition;
                    while (pacmanPlayer.GetYPosition() > j) {
                        j++;
                        if (mapPacman.GetMap(pacmanPlayer.GetXPosition() / 19, j / 19) == 0) {
                            isObstacle = true;
                        }
                    }
                } else {
                    int j = pacmanPlayer.GetYPosition();
                    while (this.yPosition > j) {
                        j++;
                        if (mapPacman.GetMap(pacmanPlayer.GetXPosition() / 19, j / 19) == 0) {
                            isObstacle = true;
                        }
                    }
                }
                if (!isObstacle) {
                    this.xDirection = 0;
                    this.yDirection = pacmanPlayer.GetYPosition() > this.yPosition ? 1 : (-1);
                }
                isDetect=true;

            } else if (pacmanPlayer.GetYPosition() == this.yPosition && this.isAlive) {
                if (pacmanPlayer.GetXPosition() > this.xPosition) {
                    int j = this.xPosition;
                    while (pacmanPlayer.GetXPosition() > j) {
                        j++;
                        if (mapPacman.GetMap(j / 19, pacmanPlayer.GetYPosition() / 19) == 0) {
                            isObstacle = true;
                        }
                    }
                } else {
                    int j = pacmanPlayer.GetXPosition();
                    while (this.xPosition > j) {
                        j++;
                        if (mapPacman.GetMap(j / 19, pacmanPlayer.GetYPosition() / 19) == 0) {
                            isObstacle = true;
                        }
                    }
                }
                if (!isObstacle) {
                    this.xDirection = pacmanPlayer.GetXPosition() > this.xPosition ? 1 : (-1);
                    this.yDirection = 0;
                }
                isDetect=true;

            }

        }
        return isDetect;

    }

    /**
     * Funkcja sprawda czy duch znajduje sie na skrzyzowaniu dróg,
     * jezeli tak to z określonym prawdopodobieństwem duch zmienia kierunek
     * na losowy skręt.
     *
     * @param probability prawdopobodniestwo z jaka duch ma skrecic na skrzyzowaniu wyrazona  w zakresie 0-100
     * @param game wskaznik na gre
     * @return czy duch zmienil kierunek na skrzyzowaniu
     */

    public boolean RecognizeCrossing(int probability, Game game){

        Random rand = new Random();
        int random = rand.nextInt(100);
        Map mapPacman = game.getPacmanMap();
        int bigX=this.GetXPosition()/19;
        int bigY=this.GetYPosition()/19;

        if(probability>=random && this.GetXPosition()%19==0 && this.GetYPosition()%19==0){


            if(this.GetXDirection()==0){
                //ide w osi pionowej
                //czy jest skret w lewo
                boolean leftTurn = (mapPacman.GetMap(bigX-1,bigY)!=0);
                boolean rightTurn = (mapPacman.GetMap(bigX+1,bigY)!=0);
                if(leftTurn || rightTurn){
                    this.yDirection=0;
                    if(leftTurn && rightTurn){
                        int randomTurn = rand.nextInt(2);
                        if(randomTurn==0){
                            this.xDirection=1;
                        }
                        else {
                            this.xDirection=-1;

                        }

                    }
                    else if(leftTurn){
                        this.xDirection=-1;
                    }
                    else if(rightTurn){
                        this.xDirection=1;
                    }
                }
            }
            else {
                //ide w osi poziomej
                //czy jest skret w lewo
                boolean leftTurn = (mapPacman.GetMap(bigX,bigY-1)!=0);
                boolean rightTurn = (mapPacman.GetMap(bigX,bigY+1)!=0);
                if(leftTurn || rightTurn){
                    this.xDirection=0;
                    if(leftTurn && rightTurn){
                        int randomTurn = rand.nextInt(2);
                        if(randomTurn==0){
                            this.yDirection=1;
                        }
                        else {
                            this.yDirection=-1;

                        }


                    }
                    else if(leftTurn){
                        this.yDirection=-1;
                    }
                    else if(rightTurn){
                        this.yDirection=1;
                    }
                }

            }

            return true;
        }
        return false;


    }


    /**
     * Funkcja nadpisuje z klasy dziedziczacej w celu poprzedzenia
     * ruchu o jeden pixel przez ducha funkcjami sztucznej inteligenacji,
     * tzn sprawdzajacych szereg zaleznosci czy moze i w jakim kierunku powinnnien
     * pojsc duch
     * @param game wskaznik na gre
     * @return czy ruch sie odbyl poprawnie
     */
    @Override
    public boolean Move(Game game){
        boolean isMove=false;

        if(!this.GetIsAlive()) return false;

        this.RecognizeCrossing(30,game);

        if (game.getTimeForEat() == 0){
            if(this.DetectPacman(game.getPacmanPlayer(), game.getPacmanMap())) {
                if(super.Move(game)){
                    return true;
                }
                else {
                    this.ChangeToRandomDirection(game.getPacmanMap());
                    return super.Move(game);
                }
            }
            else {
                if(super.Move(game)){
                    return true;
                }
                else {
                    this.ChangeToRandomDirection(game.getPacmanMap());
                    return super.Move(game);
                }

            }
        }
        else {
            if(super.Move(game)){
                return true;
            }
            else {
                this.ChangeToRandomDirection(game.getPacmanMap());
                return super.Move(game);
            }
        }
    }

    /**
     * Funkcja losuje nowy kierunek ruchu ducha,
     * w ktorym moze sie przemiescic i zmienia na niego
     * @param mapPacman wskaznik na mape gry
     */

    public void ChangeToRandomDirection(Map mapPacman){
        int randWandtedGhostXDirection = 0;
        int randWandtedGhostYDirection = 0;
        int n = 0;

        while ((this.xDirection == 0) && (this.yDirection == 0) &&  this.isAlive) {
            n = rand.nextInt(4) + 1;

            switch (n) {
                case 1:
                    randWandtedGhostXDirection = 1;
                    randWandtedGhostYDirection = 0;
                    break;

                case 2:
                    randWandtedGhostXDirection = -1;
                    randWandtedGhostYDirection = 0;
                    break;

                case 3:
                    randWandtedGhostXDirection = 0;
                    randWandtedGhostYDirection = 1;
                    break;

                case 4:
                    randWandtedGhostXDirection = 0;
                    randWandtedGhostYDirection = -1;
                    break;

                default:
                    break;
            }

            if(
                    mapPacman.GetMap((this.xPosition+randWandtedGhostXDirection)/19, (this.yPosition+randWandtedGhostYDirection)/19)!=0 &&
                            mapPacman.GetMap(((this.xPosition+randWandtedGhostXDirection+18)/19), ((this.yPosition+randWandtedGhostYDirection+18)/19))!=0 &&
                            mapPacman.GetMap(((this.xPosition+randWandtedGhostXDirection)/19), ((this.yPosition+randWandtedGhostYDirection+18)/19))!=0 &&
                            mapPacman.GetMap(((this.xPosition+randWandtedGhostXDirection+18)/19), ((this.yPosition+randWandtedGhostYDirection)/19))!=0 )
            {
                this.yDirection = randWandtedGhostYDirection;
                this.xDirection = randWandtedGhostXDirection;
            }

        }

    }
}