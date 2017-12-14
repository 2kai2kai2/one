package com.kaikai.one;

public class Enemy {
	
	public int x,y;
	public Direction dir;
	
	public Enemy() {
		//Random location
		dir = Direction.values()[Main.r.nextInt(Direction.values().length)];
		if (dir == Direction.UP) {
			y = Main.size-1;
			x = Main.r.nextInt(Main.size-1);
		} else if (dir == Direction.RIGHT) {
			y = Main.r.nextInt(Main.size-1);
			x = 0;
		} else if (dir == Direction.DOWN) {
			y = 0;
			x = Main.r.nextInt(Main.size-1);
		} else if (dir == Direction.LEFT) {
			y = Main.r.nextInt(Main.size-1);
			x = Main.size-1;
		}
		
	}
	
	public static void allstep() {
		for (Enemy e : Main.enemies) {
			e.step();
		}
	}
	
	public void step() {
		int dx = Main.playerx - x;
		int dy = Main.playery - y;
		if (Math.abs(dx)>Math.abs(dy)) {
			if (dx>0) {
				dir = Direction.RIGHT;
				x++;
			}
			else if (dx<0) {
				dir = Direction.LEFT;
				x--;
			}
		} else {
			if (dy>0) {
				dir = Direction.DOWN;
				y++;
			}
			else if (dy<0) {
				dir = Direction.UP;
				y--;
			}
		}
		if (Main.playerx == x && Main.playery == y) {
			//YOU LOSE
			Main.running = false;
		}
	}
	
	
	
}
