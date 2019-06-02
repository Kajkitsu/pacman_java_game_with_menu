package com.java_pacman_menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.Scanner;


/**
 * Klasa mapy z gry.
 * Zawiera cała logike, zapis i odczyt danych zwiazanych z mapą.
 */

public class Map {
    protected String pathToMap = null;
    protected String pathToImgOfMap = null;
    protected int height = 0;
    protected int width = 0;
    protected int pacmanSquareX = 0;
    protected int pacmanSquareY = 0;
    protected int ghostSquareX[] = new int[4];
    protected int ghostSquareY[] = new int[4];
    protected int map[][];
    protected int scoreMap[][];


    /**
     * Parametryczny konstruktor mapy.
     * @param path Sciezka do pliku tekstowego z danymi mapy z gry.
     */
    public Map(String path) {
        this.pathToMap = path;
        try {
            this.LoadMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map() {
//        System.out.println("Podaj sciezke bo pliku konfiguracyjnego mapy: ");
//        Scanner odczyt = new Scanner(System.in);
//        this.pathToMap = odczyt.nextLine();
//        System.out.println(this.pathToMap);
//        odczyt.close();
//        try {
//            this.LoadMap();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * Zwraca wysokosc mapy, tzn. liczbe kartek w pionie
     * @return wysokosc mapy, tzn. liczba kartek w pionie
     */
    public int GetHeight() {
        return this.height;
    }

    /**
     * Zwraca szerokosc mapy, tzn. liczbe kartek w poziomie
     * @return szerokosc mapy, tzn. liczba kartek w poziomie
     */
    public int GetWidth() {
        return this.width;
    }

    /**
     * Zwraca wspolrzedna X spawnu pacmana (tzn numer kratki w poziomie)
     * @return wspolrzedna X
     */
    public int GetPacmanSquareX() {
        return this.pacmanSquareX;
    }


    /**
     * Zwraca wspolrzedna Y spawnu pacmana (tzn numer kratki w pionie)
     * @return wspolrzedna Y
     */
    public int GetPacmanSquareY() {
        return this.pacmanSquareY;
    }

    /**
     * Zwraca wspolrzedna X spawnu ducha o numerze i (tzn numer kratki w poziomie ducha o numerze i)
     * @param i numer ducha w zakresie (0-3)
     * @return wspolrzedna X spawnu ducha
     */
    public int GetGhostSquareX(int i) {
        return this.ghostSquareX[i];
    }

    /**
     * Zwraca wspolrzedna Y spawnu ducha o numerze i (tzn numer kratki w pionie ducha o numerze i)
     * @param i numer ducha w zakresie (0-3)
     * @return wspolrzedna Y spawnu ducha
     */
    public int GetGhostSquareY(int i) {
        return this.ghostSquareY[i];
    }

    /**
     * Zwraca liczbe ktora reprezentuje typ kwadratu z kwadratu o wspolrzednych x i y.
     * 0 - sciana
     * 1 - zwykly punkt
     * 2 - duzy punkt
     * 3 - pusty kwadrat
     * @param x wspolrzedna x
     * @param y wspolrzedna y
     * @return liczba reprezentuajca typ kwadratu
     */
    public int GetMap(int x, int y) {
        return this.map[x][y];
    }

    /**
     * Zwraca liczbe punktow dostepnych na kwadracie o wspolrzednych x i y
     * @param x wspolrzedna x
     * @param y wspolrzedna y
     * @return liczba punktow
     */
    public int GetScoreMap(int x, int y) {
        return this.scoreMap[x][y];
    }

    /**
     * Funkcja na podstawie mapy z pliku tworzy(Wczytuje poczatkowe wartosci) mape punktów które można zdobyć na mapie.
     */
    public void MakeScoreMap() {
        this.scoreMap = new int[this.width][this.height];
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (map[x][y] == 1)
                    this.scoreMap[x][y] = 100;
                else if (map[x][y] == 2)
                    this.scoreMap[x][y] = 500;
                else
                    this.scoreMap[x][y] = 0;
            }
        }
    }

    /**
     * Funkcja sprawda czy mapa punktów jest pusta.
     * @return czy mapa punktow jest pusta
     */
    public boolean CheckIsScoreMapEmpty() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (this.scoreMap[x][y] > 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Funckja zwraca punkty z kwadratu w poblizu wspolrzednych pacmana i zeruje wartosc w mapie punktow.
     * @param pacmanXposition wspolrzedna x pacmana
     * @param pacmanYposition wspolrzedna y pacmana
     * @return wartosc zdoobytych punktow
     */
    public int TakeScoreFrom(int pacmanXposition, int pacmanYposition) {
        if ((pacmanXposition + 6) / 19 == (pacmanXposition + 13) / 19) {
            int points = this.scoreMap[(pacmanXposition + 9) / 19][(pacmanYposition + 9) / 19];
            this.scoreMap[(pacmanXposition + 9) / 19][(pacmanYposition + 9) / 19] = 0;
            return points;
        }
        return 0;
    }


    /**
     * Funkcja inicjalizacyjna mapy na podstawie danych z piku tesktowego.
     * @throws IOException
     */
    protected void LoadMap() throws IOException {
        FileReader fileReader = new FileReader(pathToMap);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String readedLine = bufferedReader.readLine();
        int i = 0;

        // wczytanie wyskosci i szerokosci
        for (i = 0; i < readedLine.length(); i++) {
            if (readedLine.charAt(i) != ' ') {
                this.width = this.width * 10;
                this.width += readedLine.charAt(i) - 48;
            } else {
                i++;
                break;
            }
        }
        for (; i < readedLine.length(); i++) {
            if (readedLine.charAt(i) != ' ') {
                this.height = this.height * 10;
                this.height += readedLine.charAt(i) - 48;
            } else {
                i++;
                break;
            }
        }

        // wczytanie wsporzednych pacmana
        readedLine = bufferedReader.readLine();
        for (i = 0; i < readedLine.length(); i++) {
            if (readedLine.charAt(i) != ' ') {
                this.pacmanSquareX = this.pacmanSquareX * 10;
                this.pacmanSquareX += readedLine.charAt(i) - 48;
            } else {
                i++;
                break;
            }
        }
        for (; i < readedLine.length(); i++) {
            if (readedLine.charAt(i) != ' ') {
                this.pacmanSquareY = this.pacmanSquareY * 10;
                this.pacmanSquareY += readedLine.charAt(i) - 48;
            } else {
                i++;
                break;
            }
        }

        // wczytanie wsporzednych duchow
        for (int j = 0; j < 4; j++) {
            readedLine = bufferedReader.readLine();
            for (i = 0; i < readedLine.length(); i++) {
                if (readedLine.charAt(i) != ' ') {
                    this.ghostSquareX[j] = this.ghostSquareX[j] * 10;
                    this.ghostSquareX[j] += readedLine.charAt(i) - 48;
                } else {
                    i++;
                    break;
                }
            }
            for (; i < readedLine.length(); i++) {
                if (readedLine.charAt(i) != ' ') {
                    this.ghostSquareY[j] = this.ghostSquareY[j] * 10;
                    this.ghostSquareY[j] += readedLine.charAt(i) - 48;
                } else {
                    i++;
                    break;
                }
            }

        }

        // wczytanie mapy
        this.map = new int[this.width][this.height];
        for (int y = 0; y < this.height; y++) {
            readedLine = bufferedReader.readLine();
            for (int x = 0; x < this.width; x++) {
                this.map[x][y] = readedLine.charAt(x * 2) - 48;
            }
        }
        this.pathToImgOfMap = bufferedReader.readLine();
        bufferedReader.close();
    }


}