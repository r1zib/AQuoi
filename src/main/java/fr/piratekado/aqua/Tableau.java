package fr.piratekado.aqua;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.piratekado.aqua.image.AnalyseImage;
import fr.piratekado.aqua.image.ApplicationRessource;


public class Tableau extends JPanel implements InterfaceBtn {
	private static final long serialVersionUID = 1L;
	public final static int DEC_X = 10;
	public final static int PAS_X = 40;
	public final static int LARG = 40 * 6;
	public final static int PAS_Y = 40;
	public final static int HAUT = 40 * 6;
	private int info [] [] = new int[6][6];

	
	private AffichageGrille affGrille;
	public Tableau() {
		super();
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				Point pt = e.getPoint();
				int colx = (pt.x - DEC_X) / PAS_X;
				int coly = (pt.y ) / PAS_Y;
				if ( colx >= 0 && colx <=5 && coly >= 0 && coly <=5) {
					affGrille.action(colx, coly);
					repaint();
				}
				
			}
		});
	}


	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.drawImage(ApplicationRessource.getImgBg(this), 0 , 0, null);

		g.setColor(Color.BLUE);

		for (int x= 0; x < 6  ;x++) {
			g.drawRect(x * PAS_X + DEC_X, 0, PAS_X, HAUT );
		}
		for (int y=0;y < 6 ;y++) {
			g.drawRect(DEC_X, y * PAS_X , LARG,  PAS_Y );
		}

	}
	public void actionCalcul() {
		// TODO Auto-generated method stub
		repaint();
	} 

	/* Setter and getter : nécessaire pour Junit*/

	public void setInfo(int[][] info) {
		this.info = info;
	}
	
	public void initGrille (int[][] info){
		if (affGrille == null) {
			affGrille = new AffichageGrille(info);
			add(affGrille);
			// on ne met pas le Layout 
			setLayout(null);

		} else {
			affGrille.setInfo(info);
		}
		
	}
	@Override
	public int actionPhoto() {
		try {
			AnalyseImage anaIma = new AnalyseImage();
			anaIma.captureEcran();
			anaIma.compareImage();
			info = anaIma.getInfo();
			initGrille(info);
		
			AlgoRecherche algo = new AlgoRecherche (affGrille.getGrille());
			System.out.println("recherche");
			affGrille.setBestAction(algo.recherche(2));
			System.out.println("fin recherche");
			actionCalcul();
			affGrille.setDebug(true);
			
		
		
			 
			return 0;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,	"Problème dans la recherche automatique :" + e.getMessage() );
			return 0;
		}
	}


	@Override
	public void setActionBtn(int ligne, int col) {
		// TODO Auto-generated method stub
		
	}
	

}
