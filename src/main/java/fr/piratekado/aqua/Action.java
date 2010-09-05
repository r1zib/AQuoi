package fr.piratekado.aqua;

import java.util.Iterator;

public class Action implements  Cloneable {
public int x;
public int y;
public int nbBulle;
public int nbBulleExplo;
public int nbCoupSup;

public Action() {
	// TODO Auto-generated constructor stub
}
public Action(Grille grille,int x, int y) {
	this.x = x;
	this.y = y;
	this.nbBulle = grille.getNbBulle();
	this.nbBulleExplo = grille.getBullesExplose();
	nbCoupSup = 0;
	if (nbBulleExplo >= 3)  nbCoupSup++;
	if (nbBulleExplo >= 8)  nbCoupSup++;
	if (nbBulleExplo >= 15) nbCoupSup++;
	if (nbBulleExplo >= 25) nbCoupSup += Math.floor((nbBulleExplo - 15) / 10);
	
	
}
@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

public static int nbCoupTotal (Iterator<Action> lstAction) {
	int nb = 0;
	while (lstAction.hasNext()) {
		Action e = lstAction.next();
		nb += e.nbCoupSup;
	}
	return nb;
	
}

public static String debugAffiche (Iterator<Action> lstAction) {
	int nb = 0;
	StringBuffer info = new StringBuffer();
	
	while (lstAction.hasNext()) {
		Action e = lstAction.next();
		nb += e.nbCoupSup;
		info.append("[" + e.x +"," + e.y +"," + e.nbBulleExplo + "," + e.nbCoupSup + "]");
	}
	info.append("Total :" +nb);
	
	return info.toString();
	
}
}
