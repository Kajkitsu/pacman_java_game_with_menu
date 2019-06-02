package com.java_pacman_menu;

import java.awt.Rectangle;


/**
 * Klasa bohaterow gry,
 * zawiera podstawową fizykę bohaterow
 */
public class Hero {

    protected int xPosition = 0;
    protected int yPosition = 0;
    protected int xDirection = 0;
    protected int yDirection = 0;
    protected boolean isAlive = true;

    /**
     * Zwraca wspolrzedna x.
     * @return wspolrzedna x
     */
    public int GetXPosition() {
        return this.xPosition;
    }

    /**
     * Zwraca wspolrzedna y.
     * @return wspolrzedna y
     */
    public int GetYPosition() {
        return this.yPosition;
    }

    /**
     * Zwraca czy bohater zyje
     * @return Czy bohater zyje
     */
    public boolean GetIsAlive() {
        return this.isAlive;
    }

    /**
     * Zwraca kierunek w plaszczyznie poziomej.
     * @return kierunek w plaszczyznie poziomej.
     */
    public int GetXDirection() {
        return xDirection;
    }

    /**
     * Zwraca kierunek w plaszczyznie pionowej.
     * @return kierunek w plaszczyznie pionowej.
     */
    public int GetYDirection() {
        return yDirection;
    }

    /**
     * Przemieszcza bohatera gry o jeden piksel,
     * jezeli ruch sie nie uda, bo bohater napotka przeszkode
     * zwraca "false".
     * @return czy ruch sie odbyl
     */
    public boolean Move(Game game) {
        if (!this.GetIsAlive()) return false;

            if (
                    (this.xPosition + this.xDirection < 19 || this.xDirection + this.xPosition > (game.getPacmanMap().GetWidth() - 2) * 19+1)
                            && (game.getPacmanMap().GetMap((this.xDirection*18 + this.xPosition) / 19, this.yPosition / 19)) != 0
                            && this.yPosition%19==0){
                if (this.xPosition + this.xDirection == 0)
                    this.xPosition = 19 * (game.getPacmanMap().GetWidth() - 1) - 1;
                else if (this.xPosition + this.xDirection == 19 * (game.getPacmanMap().GetWidth() - 1))
                    this.xPosition = 1;
                else {
                    this.xPosition = this.xPosition + this.xDirection;
                }
                return true;
            } else if (
                    game.getPacmanMap().GetMap((this.xPosition + this.xDirection) / 19,(this.yPosition + this.yDirection) / 19) != 0
                            && game.getPacmanMap().GetMap(((this.xPosition + this.xDirection + 18) / 19),((this.yPosition + this.yDirection + 18) / 19)) != 0
                            && game.getPacmanMap().GetMap(((this.xPosition + this.xDirection) / 19),((this.yPosition + this.yDirection + 18) / 19)) != 0
                            && game.getPacmanMap().GetMap(((this.xPosition + this.xDirection + 18) / 19),((this.yPosition + this.yDirection) / 19)) != 0
            )
            {
                this.xPosition += this.xDirection;
                this.yPosition += this.yDirection;
                return true;
            } else {
                this.xDirection = 0;
                this.yDirection = 0;
                return false;
            }
    }

    /**
     * Sprawdza czy postacie sie stykaja co jest warunkiem zabicia bohatera.
     * @param killed bohater ktory jest atakowany
     * @return czy zabito bohatera z paramteru
     */
    public boolean TryToKill(Hero killed) {
        Rectangle rectKilled = new Rectangle(killed.GetXPosition() - 3, killed.GetYPosition() - 3, 13, 13);
        Rectangle rectKiller = new Rectangle(this.GetXPosition() - 3, this.GetYPosition() - 3, 13, 13);

        if (rectKiller.intersects(rectKilled) && this.isAlive && killed.isAlive) {
            killed.isAlive = false;
            return true;
        } else
            return false;

    }

}