package com.Jakilla;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static GameWindow gameWindow;
    private static long frame_time;
    private static Image kosmos;
    private static Image game_over;
    private static Image cat;
    private static float cat_left = 200;
    private static float cat_top = -110;
    private static float cat_v = 200;
    private static int result;

    public static void main(String[] args) throws IOException {
        kosmos = ImageIO.read(GameWindow.class.getResourceAsStream("kosmos.png"));
        game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        cat = ImageIO.read(GameWindow.class.getResourceAsStream("cat.png"));
        gameWindow = new GameWindow();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(200,100);
        gameWindow.setSize(906,478);
        gameWindow.setResizable(false);
        frame_time = System.nanoTime();
        GameField gameField = new GameField();
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float cat_right = cat_left + cat.getWidth(null);
                float cat_bottom = cat_top + cat.getHeight(null);
                boolean is_cat = x >= cat_left && x <= cat_right && y >= cat_top && y <= cat_bottom;
                if (is_cat) {
                    cat_top = - 110;
                    cat_left = (int) (Math.random() * (gameField.getWidth() - cat.getWidth(null)));
                    cat_v = cat_v + 20;
                    result++;
                    gameWindow.setTitle("Result" + result);
                }
            }
        });
        gameWindow.add(gameField);
        gameWindow.setVisible(true);
    }
    private static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - frame_time) * 0.000000001f;
        frame_time = current_time;

        cat_top = cat_top + cat_v * delta_time;
        g.drawImage(kosmos,0,0,null);
        g.drawImage(cat,(int) cat_left, (int) cat_top, null);
        if (cat_top > gameWindow.getHeight()) g.drawImage(game_over,280,120,null);
    }
    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
