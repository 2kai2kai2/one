package com.kaikai.one;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUIAdapter extends JFrame {
    Canvas canvas;

    public GUIAdapter() {
        canvas = new Canvas() {
            // These overrides ensure resize and initial start smoothly display the board.
            @Override
            public void update(Graphics g) {
                if (isShowing())
                    paint(g);
            }

            @Override
            public void paint(Graphics g) {
                if (Main.running) {
                    GUIAdapter.this.update();
                } else {
                    GUIAdapter.this.update();
                    GUIAdapter.this.drawGameOver();
                }
            }
        };
        this.add(canvas);
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Main.processInput(e.getKeyChar());
            }
        });

        this.setTitle("One | Score: 0 | Turn: 0");

        this.setSize(400, 400);
        this.setVisible(true);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        update();
    }

    public void update() {
        this.setTitle("One | Score: " + Main.score + " | Turn: " + Main.time);

        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double tileWidth = width / Main.size;
        double tileHeight = height / Main.size;

        Graphics2D gfx = (Graphics2D) canvas.getGraphics();
        gfx.setStroke(new BasicStroke(0));
        for (int row = 0; row < Main.size; ++row) {
            for (int col = 0; col < Main.size; ++col) {
                if (col % 2 == 0 ^ row % 2 == 0) {
                    gfx.setColor(Color.gray);
                } else {
                    gfx.setColor(Color.white);
                }
                gfx.fillRect((int) (tileWidth * col), (int) (tileHeight * row), (int) tileWidth, (int) tileHeight);
            }
        }
        int playerX = Main.playerX;
        int playerY = Main.playerY;
        gfx.setColor(Color.cyan);
        switch (Main.playerDir) {
            case UP -> gfx.fillPolygon(
                    new int[]{(int) (tileWidth * (playerX + 0.5)), (int) (tileWidth * (playerX + 0.9)), (int) (tileWidth * (playerX + 0.5)), (int) (tileWidth * (playerX + 0.1))},
                    new int[]{(int) (tileHeight * (playerY + 0.1)), (int) (tileHeight * (playerY + 0.8)), (int) (tileHeight * (playerY + 0.6)), (int) (tileHeight * (playerY + 0.8))},
                    4);
            case RIGHT -> gfx.fillPolygon(
                    new int[]{(int) (tileWidth * (playerX + 0.9)), (int) (tileWidth * (playerX + 0.2)), (int) (tileWidth * (playerX + 0.4)), (int) (tileWidth * (playerX + 0.2))},
                    new int[]{(int) (tileHeight * (playerY + 0.5)), (int) (tileHeight * (playerY + 0.9)), (int) (tileHeight * (playerY + 0.5)), (int) (tileHeight * (playerY + 0.1))},
                    4);
            case DOWN -> gfx.fillPolygon(
                    new int[]{(int) (tileWidth * (playerX + 0.5)), (int) (tileWidth * (playerX + 0.9)), (int) (tileWidth * (playerX + 0.5)), (int) (tileWidth * (playerX + 0.1))},
                    new int[]{(int) (tileHeight * (playerY + 0.9)), (int) (tileHeight * (playerY + 0.2)), (int) (tileHeight * (playerY + 0.4)), (int) (tileHeight * (playerY + 0.2))},
                    4);
            case LEFT -> gfx.fillPolygon(
                    new int[]{(int) (tileWidth * (playerX + 0.1)), (int) (tileWidth * (playerX + 0.8)), (int) (tileWidth * (playerX + 0.6)), (int) (tileWidth * (playerX + 0.8))},
                    new int[]{(int) (tileHeight * (playerY + 0.5)), (int) (tileHeight * (playerY + 0.9)), (int) (tileHeight * (playerY + 0.5)), (int) (tileHeight * (playerY + 0.1))},
                    4);
        }
        gfx.setColor(Color.red);

        gfx.setStroke(new BasicStroke(3));
        for (Enemy e : Main.enemies) {
            switch (e.dir) {
                case UP -> {
                    gfx.drawLine((int) (tileWidth * (e.x + 0.5)), (int) (tileHeight * (e.y + 0.4)), (int) (tileWidth * (e.x + 0.3)), (int) (tileHeight * (e.y + 0.6)));
                    gfx.drawLine((int) (tileWidth * (e.x + 0.5)), (int) (tileHeight * (e.y + 0.4)), (int) (tileWidth * (e.x + 0.7)), (int) (tileHeight * (e.y + 0.6)));
                }
                case RIGHT -> {
                    gfx.drawLine((int) (tileWidth * (e.x + 0.6)), (int) (tileHeight * (e.y + 0.5)), (int) (tileWidth * (e.x + 0.4)), (int) (tileHeight * (e.y + 0.3)));
                    gfx.drawLine((int) (tileWidth * (e.x + 0.6)), (int) (tileHeight * (e.y + 0.5)), (int) (tileWidth * (e.x + 0.4)), (int) (tileHeight * (e.y + 0.7)));
                }
                case DOWN -> {
                    gfx.drawLine((int) (tileWidth * (e.x + 0.5)), (int) (tileHeight * (e.y + 0.6)), (int) (tileWidth * (e.x + 0.3)), (int) (tileHeight * (e.y + 0.4)));
                    gfx.drawLine((int) (tileWidth * (e.x + 0.5)), (int) (tileHeight * (e.y + 0.6)), (int) (tileWidth * (e.x + 0.7)), (int) (tileHeight * (e.y + 0.4)));
                }
                case LEFT -> {
                    gfx.drawLine((int) (tileWidth * (e.x + 0.4)), (int) (tileHeight * (e.y + 0.5)), (int) (tileWidth * (e.x + 0.6)), (int) (tileHeight * (e.y + 0.3)));
                    gfx.drawLine((int) (tileWidth * (e.x + 0.4)), (int) (tileHeight * (e.y + 0.5)), (int) (tileWidth * (e.x + 0.6)), (int) (tileHeight * (e.y + 0.7)));
                }
            }
        }
    }

    public void drawGameOver() {
        this.setTitle("One | Score: " + Main.score + " | Turn: " + Main.time);

        Graphics2D gfx = (Graphics2D) canvas.getGraphics();
        int gameoverWidth = canvas.getWidth() * 4 / 5;
        gfx.setFont(Font.decode("MONOSPACE BOLD " + gameoverWidth / 9 * 2 / 4 * 3));
        gfx.drawString("GAME OVER", canvas.getWidth() / 2 - gameoverWidth / 2, canvas.getHeight() * 2 / 5);
        int scoreWidth = canvas.getWidth() / 3;
        String scoreText = "SCORE: " + Main.score;
        int charWidth = scoreWidth / scoreText.length();
        gfx.setFont(Font.decode("MONOSPACE BOLD " + charWidth * 2 / 4 * 3));
        gfx.drawString(scoreText, canvas.getWidth() / 2 - scoreWidth / 2, canvas.getHeight() * 2 / 5 + charWidth * 3);
        String turnText = "TURN: " + Main.time;
        gfx.drawString(turnText, canvas.getWidth() / 2 - (turnText.length() * charWidth) / 2, canvas.getHeight() * 2 / 5 + charWidth * 6);
        gfx.drawString("PRESS R TO RESTART", canvas.getWidth() / 2 - (18 * charWidth) / 2, canvas.getHeight() * 2 / 5 + charWidth * 9);
    }
}
