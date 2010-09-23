package fr.piratekado.aqua.image;

import java.awt.AWTException;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
/**
 * <b>AnalyseImageBase permet de capture d'écran et de trouver la position du jeu kadokado</b>
 * <p>
 * Pattern singleton pour améliorer les performances entre les différents click. <br/>
 * En général, on ne s'amuse pas à déplacer la fenêtre kadokado pendant une partie
 * </p>
 * <p>
 * Comment retrouve t-on la position jeux kadokado dans l'image ? <br/>
 * l'image <b>imageComp</b> correspond aux coin du jeux flash.  
 * </p>gf22
 * 
 * 
 * @version 1.1
 */

public class AnalyseImageBase {

	private static AnalyseImageBase instance;
	private static BufferedImage image;
	private static BufferedImage imageComp;
	private static int posX = -1;
	private static int posY = -1;
	
	
	private  AnalyseImageBase() {

	}

	public static AnalyseImageBase getInstance() {
		if (null == instance) { 
			instance = new AnalyseImageBase();
			imageComp = getCoin(instance);
		}
		return instance;
	}
	
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
	public boolean searchBorder () {
		
		/* on parcours toute l'image */
		int haut = image.getHeight(null) - imageComp.getHeight(null);
		int lar  = image.getWidth(null) - imageComp.getWidth(null);
		boolean rech = false;
		for (int j = 0; j < haut && !rech;j++) {
			for (int i = 0; i < lar && !rech;i++) {
				if (testImage(i,j)) {
					/* youpi on a trouvé l'endroit dans l'image */
					rech = true;
					posX = i;
					posY = j;
				}
			}	
		}
        
		if (!rech) {
			posX = -1;
			posY = -1;
		}
		return rech;
	}
	private boolean testImage (int x, int y) {
		for (int j = 0; j < imageComp.getHeight(null);j++) {
			for (int i = 0; i < imageComp.getWidth(null);i++) {
				int rgv = image.getRGB(x + i,y + j);
				int rgv2 = imageComp.getRGB(i,j);
				if (rgv != rgv2) return false;
			}	
		}
		
		return true;
		
	}
	static public BufferedImage toBufferedImage(Image image) {
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

	private static BufferedImage getCoin(Object canvas) {
        
    	BufferedImage image;
    	try {
            	image = toBufferedImage(new ImageIcon(canvas.getClass()
                        .getResource("/img1.png")).getImage());
        } catch (Exception ex) {
            return null;
        }
        
        return image;
    }
    /**
     * @return Position en X du jeux
     */
	public static int getPosX() {
		return posX;
	}
	/**
     * @return Position en Y du jeux
     */
	public static int getPosY() {
		return posY;
	}
	public static BufferedImage getImage() {
		return image;
	}
    /**
     * Nécessaire pour les Test Junit.
     * 
     * @return void
     */
	public static void setImage(BufferedImage image) {
		AnalyseImageBase.image = image;
	}
}
