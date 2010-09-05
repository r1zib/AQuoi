package fr.piratekado.aqua;

import java.util.Iterator;
import java.util.Vector;


public class Grille implements Cloneable {
private Bulle tab [] [] = new Bulle[6][6];
Vector<Explosion> exp ;
private InterfaceAnimation animation = null;
// pour l'algo 
private int bulleExplose = 0;

public Grille(int [][] info, InterfaceAnimation animation) {

	//affichage de la grille
	for (int i=0;i<6;i++) {
		for (int j =0; j<6; j++) {
			if (info[j][i] != 0) {
				tab[j][i] = new Bulle(i,j,info[j][i]);
			} else {
				tab[j][i] = null;
			}
			
		}
	}
	this.animation = animation;
}

public InterfaceAnimation getAnimation() {
	return animation;
}
public void setAnimation(InterfaceAnimation animation) {
	this.animation = animation;
}
public void action(int x, int y) {
	Bulle bulle = tab [y][x];
	bulleExplose = 0;
	if (bulle != null) {
		bulle.grossir();
		if (animation != null) {
			animation.grossir(x, y, bulle.getTaille());
			animation.affAction();
		}
		exp = explosion(x, y,bulle);

		if (exp instanceof Vector && !exp.isEmpty()) {
			// gestion explosion 
			bulleExplose++;
			tab[y][x] = null;
			this.gestionExplosion();
			
			
		}
	} else {
		//  explosion 
		for (int i = x-1; i<x+2;i++) {
			if (i < 0 || i > 5) continue;
			for (int j = y-1; j<y+2;j++) {
				if (j < 0 || j > 5) continue;
				if (animation != null && tab[j][i] != null) {
					animation.affDestroy(i, j);
				}				
				tab[j][i] = null;
			}	
		}
		
	}
}


public void gestionExplosion () {
	
	while (!exp.isEmpty()) {
		Iterator<Explosion> e = exp.iterator();
		while(e.hasNext()) {
		    Explosion o = (Explosion)e.next();
		    move(o);
		    if (o.isDead()) {
		    	e.remove();
		    } 
		}
		if (animation != null) {
			animation.affExplosion();
		}
		/* recherche des bulles qui sont explosées */
		for (int i=0;i<6;i++) {
			for (int j =0; j<6; j++) {
				if (tab[j][i] != null) {
					Vector<Explosion> newExplosion = explosion(i, j, (Bulle)tab[j][i]);
					if (newExplosion != null) {
						exp.addAll(newExplosion);
					} 
					if (tab[j][i].isDestroy()) {
						tab[j][i] = null;
						bulleExplose++;
					}
					
					
				}
			}
		}	
	}
	
	
	
}
// Déplacement d'une explosion
public void move(Explosion exp) {
	int x = exp.getX();
	int y = exp.getY();
	
	exp.move();
	if (!exp.isDead()) {
	// Recherche s'il y une bulle 

		Bulle bulle = getBulle(exp.getX(), exp.getY());
		if (bulle != null) {
			bulle.grossir();
			if (animation != null) {
				animation.grossir(exp.getX(), exp.getY(), bulle.getTaille());
			}
			exp.setDead();
		} else {
			if (animation != null) {
				animation.addExplosion(x, y, exp.getX(), exp.getY(), exp.getDir());
			}		
		}
	}
	
}

public Bulle getBulle(int x, int y) {
	return tab[y][x];
}

public int[][] getInfo () {
	int [][] info = new int [6][6];
	for (int i=0;i<6;i++) {
		for (int j =0; j<6; j++) {
			if (tab[j][i] != null) {
				info[j][i] = tab[j][i].getTaille();
				
			} else {
				info[j][i] = 0;
			}
		}
	}	
	return info;
}

public Vector<Explosion> explosion(int x, int y, Bulle bulle) {
	if (bulle.getTaille() >=5) {
		Vector<Explosion> exp  =  new Vector<Explosion>();
		exp.add(new Explosion(x,y,Explosion.DOWN));
		exp.add(new Explosion(x,y,Explosion.UP));
		exp.add(new Explosion(x,y,Explosion.LEFT));
		exp.add(new Explosion(x,y,Explosion.RIGTH));
		return exp;
	} else {
		return null;
	}
	
}




public int getNbBulle () {
	int nb = 0;
	for (int i=0;i<6;i++) {
		for (int j =0; j<6; j++) {
			if (tab[j][i] != null) nb ++;
		}
	}	
	return nb;
	
}
public Object clone() {
	return new Grille(this.getInfo(), null);
}

public int getBullesExplose (){
	return bulleExplose;
}

}
