package fr.piratekado.aqua;

import java.awt.Graphics;

public class Bulle {

private int taille;
private int x;
private int y;

public static final int BONUS = -1;
private boolean destroy;

public Bulle(int x, int y,int taille) {
	this.taille = taille;
	this.x = x;
	this.y = y;
	
	destroy = false;
}

public void grossir () {
	if (taille == BONUS) {
		destroy = true;
	} else {
		taille ++;
		if (taille >=5) destroy = true;
	}
	
}
public int getTaille() {
	return taille;
}
public boolean isDestroy() {
	return destroy;
}

public void paint (Graphics g, int etapeAnimation) {
	int posX = x * Tableau.PAS_X + Tableau.DEC_X;
	int posY = y * Tableau.PAS_Y; 
	switch (taille) {
		case 4 :
		case 3 : 
		case 2 : 
		case 1 : g.drawImage(ApplicationRessource.getBulle(this,taille,etapeAnimation), posX - 5 , posY - 5, null);break;
		case -1 : g.drawImage(ApplicationRessource.getBonus(this), posX - 5 , posY - 5, null);break; 
	}


}
public int getX() {
	return x;
}
public int getY() {
	return y;
}

}
