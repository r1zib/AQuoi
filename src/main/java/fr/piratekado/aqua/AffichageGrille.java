package fr.piratekado.aqua;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.piratekado.aqua.grille.Bulle;
import fr.piratekado.aqua.grille.Grille;
import fr.piratekado.aqua.grille.InterfaceAnimation;
import fr.piratekado.aqua.image.ApplicationRessource;

public class AffichageGrille extends JPanel implements Runnable, InterfaceAnimation{
	
	/**
	 * 
	 */
	private Vector<Animation> lstAnimation ;
	
	private static final long serialVersionUID = 1L;
	public Grille grille;
	private Graphics g;
	// permet de savoir si on a lancer une animation
	private int animationRun = ANIMATION_ATTENTE;
	private static final int ANIMATION_ATTENTE = 0;
	private static final int ANIMATION_EXPLOSION = 1;
	private static final int ANIMATION_STOP = 2;
	
	private static final int NB_FRAME_EXPLOSION = 10;
	private static final int TEMPS_EXPLOSION = 10;
	private static final int TEMPS_ATTENTE = 400;
	
	// variable temporaire pour passer l'info à un thread
	private int actionX = 0;
	private int actionY = 0;
	// Resultat de l'ago de recherche de la meiheure solution
	private Vector<Action> bestAction;
	
	private Thread animator;
	// pour gérer l'avancement dans les animations
	int pas = 0;
	int pasBulle = 0;

	private boolean debug;
	private JLabel frameAnimation; 
	
	public AffichageGrille(int [][] info) {
		super();
		this.grille = new Grille(info, this);
		setLayout(null);
		setOpaque(false);
		setBounds(0, 0, 320, 300);
		animator = new Thread(this);
		animator.start();
		lstAnimation = new Vector<Animation>();
	}


	@Override
	public void paint (Graphics g) {
		super.paint(g);
		this.g = g;
		//affichage de la grille
		for (int i=0;i<6;i++) {
			for (int j =0; j<6; j++) {
				Bulle bulle = grille.getBulle(i, j);
				if (bulle != null) bulle.paint(g, pasBulle);
			}
		}

		if (bestAction != null) {
			g.setColor(Color.RED);
			Font normal = new  Font("Arial", Font.BOLD , 15) ;
			g.setFont(normal);
			Object[] lst = bestAction.toArray();

			for (int ind = 0; ind < lst.length; ind ++) {
				Action act = (Action) lst[ind];
				// Cette action a déja été traité
				if (act == null) continue;
				StringBuffer aff = new StringBuffer();
				aff.append(ind + 1);

				// Recherche s'il y a plusieurs coups à faire sur une cellule
				for (int j = ind + 1; j < lst.length; j++) {
					Action act2 = (Action) lst[j];
					if (act2 != null && 
							act2.x == act.x &&
							act2.y == act.y) {
						aff.append(" ").append(j + 1);
						lst[j] = null;
					}
				}
				int width = (g.getFontMetrics().charsWidth(aff.toString().toCharArray(), 0, aff.length()));
				int height = g.getFontMetrics().getHeight();
				g.drawString(aff.toString(), Tableau.PAS_X * act.x + Tableau.DEC_X +  (Tableau.PAS_X - width ) / 2 , Tableau.PAS_Y * act.y + (Tableau.PAS_Y -  height) /2 + height);



			}

			// informations supplémentaires 
			Action fin = (Action)bestAction.lastElement();
			g.setFont(new  Font("Arial", Font.PLAIN , 15));
			g.setColor(Color.WHITE);
			StringBuffer msg = new StringBuffer();
			msg.append("En " + bestAction.size()+ " coup");
			if (bestAction.size() > 1) msg.append("s");
			if (fin.nbBulle == 0) msg.append(", 0 bulle  BRAVO!!");
			if (fin.nbBulle == 1) msg.append(", reste 1 bulle.");
			if (fin.nbBulle > 1) msg.append(", reste "+ fin.nbBulle + " bulles.");
			g.drawString(msg.toString(),Tableau.DEC_X , Tableau.PAS_Y * 6 + 10 + g.getFontMetrics().getHeight() );

		}
		if (animationRun == ANIMATION_EXPLOSION) {
			animationExplosion();
		}

	}

	public void setInfo (int[][] info) {
		grille = new Grille(info, this);
		animationRun = ANIMATION_ATTENTE;
	}
	public void setBestAction(Vector<Action> bestAction) {
		this.bestAction = bestAction;
	}

	public Grille getGrille () {
		return grille;
	}
	public void setGrille (Grille grille) {
		this.grille = grille;
		
	}

	@Override
	public void run() {
		while(true){
			switch (animationRun) {
				case ANIMATION_ATTENTE : attente() ;break;
				case ANIMATION_EXPLOSION : 	animation(); break;
				case ANIMATION_STOP : 	return;
				
			}
			debug = false;
			
		}
		
	}
	
	public void animation() {
		grille.setAnimation(this);
		grille.action(actionX, actionY);
		// quand on a fini l'animation alors on repasse en animation attente
		animationRun = ANIMATION_ATTENTE;
	}

	@Override
	public void affExplosion() {
		// Lancement de l'animation
		for (pas = 0; pas<NB_FRAME_EXPLOSION;pas++){
			this.repaint();
			try {
				Thread.sleep(TEMPS_EXPLOSION);
			} catch (InterruptedException e) {
			}
		}
		lstAnimation.removeAllElements();
	}

	public void attente () {
		// animation quand on ne fait rien

		long tm = System.currentTimeMillis();
		pasBulle = 0;
		boolean add = true;
		while (Thread.currentThread() == animator && animationRun == ANIMATION_ATTENTE) {
			repaint();
			if (add) pasBulle++; else pasBulle--;
			if (pasBulle >= 2) add = false;
			if (pasBulle <= 0) add = true;

			// Delay depending on how far we are behind.
			try {
				tm += TEMPS_ATTENTE;
				Thread.sleep(Math.max(0, tm - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				break;
			}

		}	
	}
	// élément de l'animation
	@Override
	public void addExplosion(int x1, int y1, int x2, int y2,int dir) {
		// Déplacement de l'explosion
		lstAnimation.add(new Animation(Animation.ACTION_EXPLOSION,x1,y1,x2,y2,dir));
	}

	@Override
	public void grossir(int x1, int y1, int taille) {
		// Une bulle qui grossit
		
		lstAnimation.add(new Animation(Animation.ACTION_GROSSIR,x1,y1,0,0,taille));
		
	}

	@Override
	public void affAction() {
		
		if (animationRun == ANIMATION_EXPLOSION) {
			// Lancement de l'animation
			for (pas = 0; pas<NB_FRAME_EXPLOSION;pas++){
				update(g);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
				}
			}
		}
		lstAnimation.removeAllElements();
	}
	private void animationExplosion() {
		int x =0, y=0;
		Enumeration<Animation> elt = lstAnimation.elements();
		while(elt.hasMoreElements()) {
			Animation a = (Animation)elt.nextElement();
			switch (a.action) {
			case Animation.ACTION_EXPLOSION: 
				x = (int)((((a.x2 +0.5)* Tableau.PAS_X) + 
						   (a.x2 - a.x1) * Tableau.PAS_X * (pas)/NB_FRAME_EXPLOSION) )   + Tableau.DEC_X;
				y = (int)(((a.y2 + 0.5) * Tableau.PAS_Y + 
							(a.y2 - a.y1) * Tableau.PAS_Y * (pas)/NB_FRAME_EXPLOSION));
				g.drawImage(ApplicationRessource.getImgExplosion(this, a.dir),x, y,null);
				
			break;
			case Animation.ACTION_GROSSIR: 
				if (a.taille > 4 ) {
					g.drawImage(ApplicationRessource.getBulleExplo(this), a.x1*Tableau.PAS_X + Tableau.DEC_X - 5 , a.y1 * Tableau.PAS_Y  - 5, null);
				} else if (a.taille == Bulle.BONUS){
						
				} else {
					g.drawImage(ApplicationRessource.getBulleBig(this,a.taille), a.x1*Tableau.PAS_X + Tableau.DEC_X - 5 , a.y1 * Tableau.PAS_Y  - 5, null);
				}
				break;
			}
		}
		

	}
	
	public void action(int x, int y) {
		actionX = x;
		actionY = y;
		animationRun = ANIMATION_EXPLOSION;
	// lancement du calcul dans un tread pour pouvoir faire de l'animation	
	//	animator = new Thread(this);
	//	animator.start();
	}
	
	class Animation {
		public int action;
		public int x1;
		public int y1;
		public int x2;
		public int y2;
		public int taille;
		public int dir;
		
		public final static int ACTION_EXPLOSION = 1;
		public final static int ACTION_GROSSIR = 2;
		public Animation(int action, int x1, int y1, int x2, int y2, int info) {
			this.action = action;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			switch (action) {
				case ACTION_EXPLOSION : this.dir = info; break;
				case ACTION_GROSSIR : this.taille = info; break;
			}	
		}
		
		
	}


	@Override
	public void affDestroy(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	public void setDebug(boolean debug) {
		this.debug = true;
	}
	
}
