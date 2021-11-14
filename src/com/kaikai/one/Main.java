package com.kaikai.one;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static boolean running;

    public static Random r = new Random();
    public static Scanner s = new Scanner(System.in);

    public static int playerx, playery;
    public static Direction playerdir;
    public static int size = 8;
    public static int score = 0;

    public static ArrayList<Enemy> enemies = new ArrayList();

    public static void main(String[] args) {
        System.out.print("Game size: ");
        int sizein = s.nextInt();
        sizein = Math.max(2, Math.min(sizein, 20));

        running = true;
        playerx = size / 2;
        playery = playerx;
        playerdir = Direction.UP;
        enemies.add(new Enemy());
        while (running) {
            clear();
            render();
            String in = s.next().toLowerCase();
            if (in.equals("w") || in.equals("a") || in.equals("s") || in.equals("d") || in.equals("q") ||
                    in.equals("i") || in.equals("j") || in.equals("k") || in.equals("l")) {
                if (in.equals("w") && playery > 0) { // if "w" and can go up
                    killEnemies(playerx, playery - 1);
                    playerdir = Direction.UP;
                    playery--;
                } else if (in.equals("a") && playerx > 0) {
                    killEnemies(playerx - 1, playery);
                    playerdir = Direction.LEFT;
                    playerx--;
                } else if (in.equals("s") && playery != size - 1) {
                    killEnemies(playerx, playery + 1);
                    playerdir = Direction.DOWN;
                    playery++;
                } else if (in.equals("d") && playerx != size - 1) {
                    killEnemies(playerx + 1, playery);
                    playerdir = Direction.RIGHT;
                    playerx++;
                } else if (in.equals("i")) {
                    killEnemies(playerx, playery - 1);
                    playerdir = Direction.UP;
                } else if (in.equals("j")) {
                    killEnemies(playerx - 1, playery);
                    playerdir = Direction.LEFT;
                } else if (in.equals("k")) {
                    killEnemies(playerx, playery + 1);
                    playerdir = Direction.DOWN;
                } else if (in.equals("l")) {
                    killEnemies(playerx + 1, playery);
                    playerdir = Direction.RIGHT;
                }

                // Run Enemy AI
                Enemy.allstep();
                if (r.nextDouble() > (1 - ((score / 10) / (2 * Math.sqrt(1 + Math.pow(score / 10, 2)))))) {
                    // if (enemies.size() < score/20) {
                    enemies.add(new Enemy());
                    // }

                }

            } // else, repeat for new input
        }
        // run when running == false:
        clear();
        System.out.println("  GAME OVER  \n");
        System.out.println("Score: " + score);

    }

    public static void killEnemies(int x, int y) {
        Iterator<Enemy> iten = enemies.iterator();
        while (iten.hasNext()) {
            Enemy en = iten.next();
            if (en.x == x && en.y == y) {
                iten.remove();
                score += 10;
            }
        }
    }

    // ======
    public static void clear() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            try {
                Runtime.getRuntime().exec("cls");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("\033[H033[2J");
            System.out.flush();
        }
    }

    public static void render() {
        ArrayList<ArrayList<Character>> lines = new ArrayList();
        for (int i = 0; i < size; i++) {
            lines.add(new ArrayList());
            for (int j = 0; j < size; j++) {
                lines.get(i).add('.');
            }
        }
        for (Enemy e : enemies) {
            if (e.dir == Direction.UP) {
                lines.get(e.y).set(e.x, '^');
            } else if (e.dir == Direction.RIGHT) {
                lines.get(e.y).set(e.x, '>');
            } else if (e.dir == Direction.DOWN) {
                lines.get(e.y).set(e.x, 'v');
            } else if (e.dir == Direction.LEFT) {
                lines.get(e.y).set(e.x, '<');
            }
        }

        if (playerdir == Direction.UP) {
            lines.get(playery).set(playerx, 'A');
        } else if (playerdir == Direction.RIGHT) {
            lines.get(playery).set(playerx, '}');
        } else if (playerdir == Direction.DOWN) {
            lines.get(playery).set(playerx, 'V');
        } else if (playerdir == Direction.LEFT) {
            lines.get(playery).set(playerx, '{');
        }

        for (ArrayList<Character> alc : lines) {
            for (Character c : alc) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println("Score: " + score);

    }

}
