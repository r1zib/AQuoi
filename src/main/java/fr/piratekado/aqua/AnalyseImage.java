package fr.piratekado.aqua;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class AnalyseImage {
	private BufferedImage image;
	private BufferedImage imageComp;
	private int info [] [] = new int[6][6];
	public final static int FOND = 0xff6633;
	public final static int BULLE6COUPS = 0xfee500;
	
	
	public void captureEcran () {
		
		Robot robot;
		try {
			robot = new Robot();
			image = robot.createScreenCapture(
			         new Rectangle(java.awt.Toolkit.getDefaultToolkit().getScreenSize()) );

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public boolean compareImage () {
		imageComp = toBufferedImage(ApplicationRessource.getCoin(this));
		
		/* on parcoure toute l'image */
		int haut = image.getHeight(null) - imageComp.getHeight(null);
		int lar  = image.getWidth(null) - imageComp.getWidth(null);
		int posX = 0;
		int posY = 0;
		boolean rech = false;
		for (int j = 0; j < haut;j++) {
			for (int i = 0; i < lar;i++) {
				if (testImage(i,j)) {
					/* youpi on a trouvé l'endroit dans l'image */
					rech = true;
					posX = i + 52;
					posY = j + 32;
					/* pour sortir des 2 for */
					j = haut;
					System.out.println("[" + posX + "," + posY + "]");
					break;
				}
			}	
		}
        if (!rech) return rech;
        
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
			int rgb = image.getRGB(x + point[i][0], y + point[i][1] );
			if (testCouleur (rgb, AnalyseImage.FOND) < 8000) {
				// Couleur de fond
				switch (i) {
				case 0:
				case 1:
				case 2:	val = 0; break;
				
				case 3:
				case 4: val = 1; break;
				case 5:
				case 6: val = 2;
				// distinction entre la bulle de Niv2 et la bulle + 6 coup
				rgb = image.getRGB(x , y - 5 );
				if (testCouleur (rgb, AnalyseImage.BULLE6COUPS) < 8000) {
					val = -1;
				}
				break;
				case 7: val = 3; break;
				}
				// On sort des test d�s la premi�re erreurs
				
				break;
			} else {
				if (i == 5) val = 4;
			}
		}
		return val;

	}
	public boolean testImage (int x, int y) {
		for (int j = 0; j < imageComp.getHeight(null);j++) {
			for (int i = 0; i < imageComp.getWidth(null);i++) {
				int rgv = image.getRGB(x + i,y + j);
				int rgv2 = imageComp.getRGB(i,j);
				if (rgv != rgv2) return false;
			}	
		}
		
		return true;
		
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
	public BufferedImage toBufferedImage(Image image) {
        /** On test si l'image n'est pas déja une instance de BufferedImage */
        if( image instanceof BufferedImage ) {
                return( (BufferedImage)image );
        } else {
                /** On s'assure que l'image est complètement chargée */
                image = new ImageIcon(image).getImage();
                
                /** On crée la nouvelle image */
                BufferedImage bufferedImage = new BufferedImage(
                            image.getWidth(null),
                            image.getHeight(null),
                            BufferedImage.TYPE_INT_RGB );
                
                Graphics g = bufferedImage.createGraphics();
                g.drawImage(image,0,0,null);
                g.dispose();
                
                return( bufferedImage );
        } 
}

	public void setInfo(int[][] info) {
		this.info = info;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public BufferedImage getImage() {
		return image;
	}
	public int[][] getInfo() {
		return info;
	}

	
	
}
