package fr.piratekado.aqua.grille;

public interface InterfaceAnimation {

	public void addExplosion (int x1, int y1, int x2, int y2,int dir);
	public void grossir (int x1, int y1, int taille);
	public void affExplosion ();
	public void affDestroy (int x, int y);
	public void affAction();

}
