package fr.piratekado.aqua.image;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;


/**
 * <b>AnalyseImage retrouve dans les informations du jeux de kadokado</b>
 * <p>
 * <ul>
 * <li>Fait une capture d'écran grace à <b>AnalyseImageBase</b></li>
 * <li>recherche les tailles des bulles</li>
 * </ul>
 * </p>
 * 
 * @version 1.1
 */
public class AnalyseImage {
	
	private AnalyseImageBase base;
	private int info [] [] = new int[6][6];
	public final static int FOND = 0xff6633;
	public final static int BULLE6COUPS = 0xfee500;
	
	public AnalyseImage() {
		base = AnalyseImageBase.getInstance();
	}
		
	public void captureEcran () {
	   base.captureEcran();
	}
	public boolean compareImage () {
		boolean ret = base.searchBorder();
		if (!ret) return ret;
		// position de la 1ere bulle
		int posX = base.getPosX() + 52;
		int posY = base.getPosY() + 32 ;
		
		/* Analyse du tableau */
		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < 6; i++) {
				int tab = rechercheValeur (posX  + i * 40,posY + j* 40);
				info [j][i] = tab;

			}
				
		}
		return true;
		
	}
	public int rechercheValeur (int x,int y) {
		/* Comment trouver la taille des bulles ?
		 *       5
		 *         
		 * Niv 1 : 0 1 2 3 4 OK 
		 * Niv 2 : 0 1 2 3 4 5 6 OK 
		 * niv 3 : 0 1 2 3 4 5 6 7 Ko
		 * niv 4 : 0 1 2 3 4 5 ok 
		 * 
		 * Bulle + 6 coup : comme bulle niveau 2
		 *  
		 * */
	    int  point [] [] = { {0 , 0}, {5 , 0} ,{-5 , 0}, {-8, 0}, {8, 0}, {-14, 0}, {12, 0},{0, -16}};
	    int  val = 0;
		
	    for (int i =0; i<8; i++) {
			int rgb = base.getImage().getRGB(x + point[i][0], y + point[i][1] );
			if (testCouleur (rgb, AnalyseImage.FOND) < 8000) {
				// Couleur de fond
				switch (i) {
				case 0:
				case 1:
				case 2:	val = 0; break;
				
				case 3:
				case 4: val = 1; 
				// distinction entre la bulle de Niv2 et la bulle + 6 coup
				rgb = base.getImage().getRGB(x , y - 5 );
				if (testCouleur (rgb, AnalyseImage.BULLE6COUPS) < 8000) {
					val = -1;
				}
				break;
				case 5:
				case 6: val = 2;
				// distinction entre la bulle de Niv2 et la bulle + 6 coup
				rgb = base.getImage().getRGB(x , y - 5 );
				if (testCouleur (rgb, AnalyseImage.BULLE6COUPS) < 8000) {
					val = -1;
				}
				break;
				case 7: val = 3; break;
				}
				// On sort des test dès la première erreurs
				
				break;
			} else {
				if (i == 5) val = 4;
			}
		}
		return val;

	}
	public int testCouleur (int couleur1, int couleur2) {
		/* il y a un fort contraste entre de fond et les bulles */
		/* le fond en orange  */ 
		int r1 = (couleur1 & 0x00ff0000)>>(4*4);
		int r2 = (couleur2 & 0x00ff0000)>>(4*4);
		int g1 = (couleur1 & 0x0000ff00)>>(4*2);
		int g2 = (couleur2 & 0x0000ff00)>>(4*2);
		int b1 = (couleur1 & 0x000000ff);
		int b2 = (couleur2 & 0x000000ff);
		int distance = (r1-r2)*(r1-r2) + (g1-g2)*(g1-g2) + (b1-b2)*(b1-b2);
		return distance ;
	}

	public void setInfo(int[][] info) {
		this.info = info;
	}
	public void setImage(BufferedImage image) {
		base.setImage(image);
	}
	public BufferedImage getImage() {
		return base.getImage();
	}
	public int[][] getInfo() {
		return info;
	}

	
	
}
