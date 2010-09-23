package fr.piratekado.aqua.image;

import java.awt.Image;

import javax.swing.ImageIcon;

import fr.piratekado.aqua.grille.Explosion;


/**
 * <b>ApplicationRessource de lire les images pour le helper</b>
 * <p>Le theme : <br/>
 * Pour faire plaisir à ma fille de 6ans, j'ai tenté de faire un thème fille.
 * 
 * </p>
 * 
 * @version 1.1
 */
public class ApplicationRessource {
	 private static Image imgBg;
	 private static Image[] imgExplosion;
	 private static Image[] bulles;
	 private static Image[][] bullesAnim;
	 private static Image[] bullesBig;
	 private static Image   bulleExplo;
	 private static ImageIcon photo;
	 private static Image bonus;
	 private static String themes [] = {"/bulle/","/girl/"};
	 private static String theme   = themes [0];
	 
	 
	 
	 
	 private ApplicationRessource() {}
	 
	    public static Image getImgBg(Object canvas) {
	        if(imgBg == null) {
	            try {
	            	  System.out.println(theme + "fond.png");
	            	
	            	  System.out.println(canvas.getClass()
		                        .getResource(theme + "fond.png"));
	                imgBg = new ImageIcon(canvas.getClass()
	                        .getResource(theme + "fond.png")).getImage();
	            } catch (Exception ex) {
	                return null;
	            }
	        }
	        return imgBg;
	    }
	    public static Image getImgExplosion (Object canvas, int direction) {
	        if(imgExplosion == null) {
	        	imgExplosion = new Image[4];
	            try {
	            	  
	            	imgExplosion[0] = new ImageIcon(canvas.getClass()
	                        .getResource(theme + "explosion.png")).getImage();
	            	
	            	imgExplosion[1] = new ImageIcon(canvas.getClass()
	                        .getResource(theme + "explosion1.png")).getImage();
	            	imgExplosion[2] = new ImageIcon(canvas.getClass()
	                        .getResource(theme + "explosion2.png")).getImage();
	            	imgExplosion[3] = new ImageIcon(canvas.getClass()
	                        .getResource(theme + "explosion3.png")).getImage();
	            	
	            	
	            } catch (Exception ex) {
	                return null;
	            }
	        }
	        	        
	        switch (direction) {
	        	case Explosion.RIGTH : return imgExplosion[0];
	           	case Explosion.DOWN : return imgExplosion[1];
		        case Explosion.LEFT : return imgExplosion[2];
		        case Explosion.UP : return imgExplosion[3];
		        case 0 : return imgExplosion[0];
		        
		    }
	        return null;
	    }
	    public static Image getBulle(Object canvas, int taille,int sequence) {
	        if(bullesAnim == null) {
	        	bullesAnim = new Image[4][4];
	           
	            	for (int i=0;i<4;i++){
	            		for (int j=0;j<3;j++){
	            			try {
	            				String test = canvas.getClass().getResource(theme + "bulle" + (i+1) +"-"+ j + ".png").toString();
		                        
		            			bullesAnim[i][j] = new ImageIcon(canvas.getClass()
				                        .getResource(theme + "bulle" + (i+1) +"-"+ j + ".png")).getImage();
		    	            } catch (Exception ex) {
		    	            	bullesAnim[i][j] = getBulle(canvas, i+1);
		    	            }
		            		
		            	}
	            	}
	            	

	        }
	        if (sequence <0 || sequence >3) return getBulle(canvas, taille);
	        return bullesAnim[taille - 1][sequence];
        }    
	    public static Image getBulle(Object canvas, int taille) {
	        if(bulles == null) {
	        	bulles = new Image[4];
	            try {
	            	for (int i=0;i<4;i++){
		            	bulles[i] = new ImageIcon(canvas.getClass()
		                        .getResource(theme + "bulle" + (i+1) + ".png")).getImage();
	            		
	            	}
	            } catch (Exception ex) {
	                return null;
	            }
	        }
	        return bulles[taille - 1];
        }
	    public static Image getBulleBig(Object canvas, int taille) {
	        if(bullesBig == null) {
	        	bullesBig = new Image[4];
	            try {
	               	for (int i=0;i<4;i++){
	               		bullesBig[i] = new ImageIcon(canvas.getClass()
		                        .getResource(theme + "bulle" + (i+1) + "-big.png")).getImage();
	            		
	            	}
	            } catch (Exception ex) {
	                return null;
	            }
	        }
	        return bullesBig[taille - 1];
        }	
	    public static Image getBulleExplo(Object canvas) {
	        if(bulleExplo == null) {
	            try {
	            	bulleExplo = new ImageIcon(canvas.getClass()
	                        .getResource(theme + "bulle4-explo.png")).getImage();
	            } catch (Exception ex) {
	                return null;
	            }
	        }
	        return bulleExplo;
        }	
	    public static ImageIcon getPhoto(Object canvas) {
	        if(photo == null) {
	            try {
	            	photo = new ImageIcon(canvas.getClass()
	                        .getResource("/photo.png"));
	            } catch (Exception ex) {
	                return null;
	            }
	        }
	        return photo;
        }
	    public static Image getBonus(Object canvas) {
	        if(bonus == null) {
	            try {
	            	bonus = new ImageIcon(canvas.getClass()
	                        .getResource(theme + "bonus.png")).getImage();
	            } catch (Exception ex) {
	                return null;
	            }
	        }
	        return bonus;
        }	 
	    public static void newTheme() {
	    	// permet de changer de theme
	    	for (int i = 0; i<themes.length;i++){
	    		if (themes[i].equals(theme)) {
	    			if (i + 1 == themes.length) {
	    				theme = themes[0];
	    			} else {
	    				theme = themes[i + 1];
	    			}
	    			break;
	    			
	    		}
	    	}
	    	System.out.println("nouveau theme" + theme);
			imgExplosion = null;
			bulles = null;;
			bullesBig = null;;
			bulleExplo = null;;
			bonus = null;
			imgBg = null;
			bullesAnim = null;

	    }

}
