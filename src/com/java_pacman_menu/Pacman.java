package com.java_pacman_menu;

/**
 * Klasa pacmana, ktorym steruje gracz.
 */
public class Pacman extends Hero {

    /**
     * Konstruktor parametryczny pacmana
     * @param x wspolrzedna pozioma pixela spawnu pacmana
     * @param y wspolrzedna pionowa pixel spawnu pacmana
     * @param map wskaznik na mape
     */
    public Pacman (int x, int y, Map map){
        this.xPosition=x;
        this.yPosition=y;
    }

    /**
     * Funkcja wywolywana przez gracza, odpowiada za zmiane kierunku pacmana
     * @param wantedXDirection kierunek w plaszczyznie poziomej na jaki gracz chce zmienić
     * @param wantedYDirection kierunek w plaszczyznie pionowej na jaki gracz chce zmienić
     * @param mapPacman wskaznik na mape gry
     */
    public void TryToChangeDirectionOfPacman(int wantedXDirection, int wantedYDirection, Map mapPacman){
        if(this.xPosition%19==0 && this.yPosition%19==0)
        {
            if(mapPacman.GetMap((this.xPosition+wantedXDirection)/19, (this.yPosition+wantedYDirection)/19)!=0 &&
                    mapPacman.GetMap(((this.xPosition+wantedXDirection+18)/19), ((this.yPosition+wantedYDirection+18)/19))!=0 &&
                    mapPacman.GetMap(((this.xPosition+wantedXDirection)/19), ((this.yPosition+wantedYDirection+18)/19))!=0 &&
                    mapPacman.GetMap(((this.xPosition+wantedXDirection+18)/19), ((this.yPosition+wantedYDirection)/19))!=0
            ){
                this.yDirection=wantedYDirection;
                this.xDirection=wantedXDirection;
            }

        }


    }

}