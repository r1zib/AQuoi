package fr.piratekado.aqua;

public class Explosion {
private int x;
private int y;
private int dir;

static final int UP = 1;
static final int DOWN = 2;
static final int LEFT = 3;
static final int RIGTH = 4;
static final int DEL  = 5;

public Explosion(int x, int y, int dir) {
	this.x = x;
	this.y = y;
	this.dir = dir;
}

public void move() {
	
	switch (dir) {
	case UP:   y--; break;
	case DOWN: y++; break;
	case LEFT: x--; break;
	case RIGTH: x++; break;
	default:
		x = -1;
		break;
	}
	
	if (x > 5 || x < 0 || y > 5 || y < 0) {
		dir = DEL;
	} 
	
}
public int getX() {
	return x;
}
public int getY() {
	return y;
}
public void setDead() {
	dir = DEL;
}

public boolean isDead() {
	return (dir == DEL);
}
public int getDir() {
	return dir;
}
}