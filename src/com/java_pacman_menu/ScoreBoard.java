package com.java_pacman_menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


/**
 * Klasa odpowiadajaca za tablice wynikow z gry.
 */
public class ScoreBoard {
    private List<Score> listaPunktow = new ArrayList<Score>();

    /**
     * Konstruktor.
     * Wczytuje wyniki z pliku: scoreboard.txt
     */
    public ScoreBoard(){

        Scanner file = null;
        try {
            file = new Scanner(new File("scoreboard.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        while(file.hasNextLine()){
            Score tmp = new Score();
            tmp.nickname=file.nextLine();
            tmp.map=file.nextLine();
            tmp.date=file.nextLine();
            tmp.score=Integer.valueOf(file.nextLine());
//            System.out.println("ScoreBoard()"+tmp.toString());
            listaPunktow.add(tmp);
        }
        file.close();
        listaPunktow.sort(Comparator.comparingInt(Score::getScore).reversed());
    }

    /**
     * Funkcja zwraca tablice wynikow jak wektor z wektora
     * @return tablica wynikow jak wektor z wektora
     */
    public Vector<Vector<String>> GetVVScoreBoard(){


        Vector<Vector<String>> data = new Vector<Vector<String>>();
        int place = 1;


        for(Score scoreFL : listaPunktow){
            Vector<String> newVector = new Vector<String>();
            newVector.add(place+".");
            newVector.add(scoreFL.nickname);
            newVector.add(scoreFL.score+".");
            newVector.add(scoreFL.date);
            newVector.add(scoreFL.map);
            data.add(newVector);
            place++;
        }

        return data;
    }


    /**
     * Funckcja odpowiadajaca za dodanie wyniku gry do tablicy wynikow
     * @param nickname nick gracza
     * @param date data
     * @param map nazwa mapy
     * @param score uzyskany wynik
     */
    public void AddScore(String nickname, String date, String map, int score){
        Score tmp = new Score();
        tmp.nickname=nickname;
        tmp.map=map;
        tmp.date=date;
        tmp.score=score;
//        for(Score tmp2 : listaPunktow){
//            System.out.println("AddScore()"+tmp2.toString());
//        }
        listaPunktow.add(tmp);
       // listaPunktow.sort(Comparator.comparingInt(Score::getScore));

        this.SaveScoreBoard();
    }

    /**
     * Funkcja zapisuje tablice wynikow do pliku.
     */
    public void SaveScoreBoard(){
        PrintWriter zapis = null;
        try {
            zapis = new PrintWriter("scoreboard.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        listaPunktow.sort(Comparator.comparingInt(Score::getScore).reversed());

        for(Score tmp : listaPunktow){
//            System.out.println("SaveScoreBoard()"+tmp.toString());
            zapis.println(tmp.getNickname());
            zapis.println(tmp.getMap());
            zapis.println(tmp.getDate());
            zapis.println(tmp.getScore());
        }


        zapis.close();


    }
}
