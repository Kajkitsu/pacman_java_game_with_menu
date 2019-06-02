package com.java_pacman_menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Klasa odpowiadajaca za wyswietlanie menu.
 */

public class DMenu extends JFrame implements ActionListener {

    private JComboBox<String> mapCBox;
    private JButton playButton;

    private JButton scoreButton;
    private JButton exitButton;
    private JTextField textNick;

    /**
     * Konstruktor obiektu.
     * Wczytuje on cale okienko menu z odpowiednimi ustawieniami i parametrami.
     */
    public DMenu(){
        this.setSize(290,290);
        this.setTitle("Pacman by Kajkitsu");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(null);

        JLabel labelMenu = new JLabel("Select map:");
        labelMenu.setBounds(10,10,135,30);
        this.add(labelMenu);

        JLabel labelNick = new JLabel("Choose nickname:");
        labelNick.setBounds(10,45,135,30);
        this.add(labelNick);


        String mapa[] = new String[]{"map1","map2","map3","map1-test"};

        mapCBox = new JComboBox<>(mapa);
        mapCBox.setBounds(140,10,140,30);
        this.add(mapCBox);
        mapCBox.addActionListener(this);



        textNick = new JTextField("quest");
        textNick.setBounds(140,45,140,30);
        this.add(textNick);
        textNick.addActionListener(this);

        playButton = new JButton("Play");
        playButton.setBounds(10,80,270,80);
        this.add(playButton);
        playButton.addActionListener(this);

        scoreButton = new JButton("Score board");
        scoreButton.setBounds(10,165,270,40);
        this.add(scoreButton);
        scoreButton.addActionListener(this);

        exitButton = new JButton("Exit game :(");
        exitButton.setBounds(10,210,270,40);
        this.add(exitButton);
        exitButton.addActionListener(this);

        this.setVisible(true);
    }


    /**
     * Funkcja uruchamiajaca gre.
     */
    private void startGame(){
        String nickname = textNick.getText();
        String map = (String)mapCBox.getSelectedItem();
        this.setVisible(false);
        Game game = new Game(map,nickname,this);

    }

    /**
     * Funkcja uruchamiajaca tablice wynikow
     */
    private void ShowScoreBoard(){
        DScoreBoard scoreBoard = new DScoreBoard(this);
        scoreBoard.ShowScore();
    }


    /**
     * Funckja dziedziczna odpowidajaca za progres aplikacji
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == playButton){
            this.startGame();
        }
        else if(source == exitButton){
            System.exit(1);
        }
        else if(source == scoreButton){
            this.ShowScoreBoard();

        }
    }
}