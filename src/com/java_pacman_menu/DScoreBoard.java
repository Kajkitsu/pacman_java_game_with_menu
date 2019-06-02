package com.java_pacman_menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;


/**
 * Klasa odpowiadajaca za wyswietlanie
 * tablicy wynikow.
 */
public class DScoreBoard extends JFrame implements ActionListener {

    private JButton returnButton;
    private JFrame menuFrame;
    private Vector<Vector<String>> data;
    private Vector<String> columnNames;


    /**
     * Funkcja pokazuje tablice wynikow i ukrywa menu gry.
     */
    public void ShowScore(){
        this.setVisible(true);
        this.menuFrame.setVisible(false);

    }

    /**
     * Funckja odpowidajaca za zaladowanie tablicy wynikow.
     */
    public void LoadScoreBoard(){
        columnNames = new Vector<String>();
        columnNames.add("PLACE");
        columnNames.add("NICKNAME");
        columnNames.add("SCORE");
        columnNames.add("DATE");
        columnNames.add("MAP");

        ScoreBoard scoreBoard = new ScoreBoard();
        data = scoreBoard.GetVVScoreBoard();
    }

    /**
     * Konstruktor parametryczny tablicy wynikow.
     * @param menuFrame wskaznik na menu
     */
    public DScoreBoard(JFrame menuFrame) {
        this.menuFrame=menuFrame;
        this.menuFrame.setVisible(false);

        this.setSize(500,290);
        this.setTitle("Pacman by Kajkitsu");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        returnButton = new JButton("Return");
        returnButton.setBounds(10,210,480,40);
        this.add(returnButton);
        returnButton.addActionListener(this);

        this.LoadScoreBoard();
        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(0).setPreferredWidth(8);
        table.getColumnModel().getColumn(2).setPreferredWidth(8);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.setFillsViewportHeight(true);
        table.setBounds(10,30,480,180);
        table.getTableHeader().setBounds(10,10,480,20);
        this.add(table.getTableHeader());
        this.add(table);


    }



    /**
     * Funckja dziedziczna odpowidajaca za progres aplikacji
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == returnButton){
           this.setVisible(false);
           menuFrame.setVisible(true);
        }

    }
}