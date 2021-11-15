package com.kaikai.one;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static boolean running;

    public static Random r = new Random();
    public static Scanner s = new Scanner(System.in);

    public static GUIAdapter gui;
    public static int playerX, playerY;
    public static Direction playerDir;
    public static int size = 8;
    public static int score = 0;
    public static int time = 0;

    public static ArrayList<Enemy> enemies = new ArrayList<>();

    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true");
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(8, 3, 32, 1));
        JOptionPane.showMessageDialog(null, spinner, "Enter Game Size:", JOptionPane.PLAIN_MESSAGE);
        size = (int) spinner.getValue();

        running = true;
        playerX = playerY = size / 2;
        playerDir = Direction.UP;
        enemies.add(new Enemy());

        gui = new GUIAdapter();
    }

    public static void processInput(char input) {
        if (input == 'r') {
            running = true;
            playerX = playerY = size / 2;
            playerDir = Direction.UP;
            score = 0;
            time = 0;
            enemies.clear();
            gui.update();
        }
        if (!running) {
            return;
        }
        switch (input) {
            case 'w' -> {
                if (playerY > 0) {
                    killEnemies(playerX, playerY - 1);
                    playerDir = Direction.UP;
                    playerY--;
                }
            }
            case 'a' -> {
                if (playerX > 0) {
                    killEnemies(playerX - 1, playerY);
                    playerDir = Direction.LEFT;
                    playerX--;
                }
            }
            case 's' -> {
                if (playerY < size - 1) {
                    killEnemies(playerX, playerY + 1);
                    playerDir = Direction.DOWN;
                    playerY++;
                }
            }
            case 'd' -> {
                if (playerX < size - 1) {
                    killEnemies(playerX + 1, playerY);
                    playerDir = Direction.RIGHT;
                    playerX++;
                }
            }
            case 'i' -> {
                killEnemies(playerX, playerY - 1);
                playerDir = Direction.UP;
            }
            case 'j' -> {
                killEnemies(playerX - 1, playerY);
                playerDir = Direction.LEFT;
            }
            case 'k' -> {
                killEnemies(playerX, playerY + 1);
                playerDir = Direction.DOWN;
            }
            case 'l' -> {
                killEnemies(playerX + 1, playerY);
                playerDir = Direction.RIGHT;
            }
            case 'q' -> {
            }
            default -> {
                return;
            }
        }
        ++time;
        Enemy.allstep();
        if (r.nextDouble() < Math.log1p(score) / 8 + 0.2 || enemies.isEmpty()) {
            enemies.add(new Enemy());
        }
        if (running) {
            gui.update();
        } else {
            // You just got killed.
            gui.update();
            gui.drawGameOver();
        }
    }

    public static void killEnemies(int x, int y) {
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy en = it.next();
            if (en.x == x && en.y == y) {
                it.remove();
                score += 10;
            }
        }
    }
}
