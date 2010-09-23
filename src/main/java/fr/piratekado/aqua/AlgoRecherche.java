package fr.piratekado.aqua;

import java.util.Vector;

import fr.piratekado.aqua.grille.Grille;

public class AlgoRecherche {

	Grille etape0;
	int action [] = new int[2];
	Vector<Action> recherche ;
	
	public AlgoRecherche(Grille info) {
		// TODO Auto-generated constructor stub
		this.etape0 = info;
	}
	
	public Vector<Action> recherche (int coup) {
		// calcul pour toutes les cases le résultat 
		
		Action actionTop = null;
		Vector<Action> lstActionTop = null;
		
		
		for (int i=0;i<6;i++) {
			for (int j =0; j<6; j++) {
				// on de détruit pas avec les bombes 
				if (etape0.getBulle(i, j) == null ) continue;
				Grille test = (Grille)etape0.clone();
				test.action(i, j);
				Action action = new Action(test,i,j);
	
				if (action.nbBulle == 0) {
					// on a trouvé la bonne solution
					Vector<Action> lstAction = new Vector<Action>();
					lstAction.add(action);
					return lstAction;
					
				}
				if (coup == 0) {
					// System.out.println("algo" + i + " " + j + " " + test.getNbBulle());
					// Recherche de la meilheure solution
					if ( actionTop == null ||
						 action.nbBulle <= actionTop.nbBulle) {
						try {
							actionTop = (Action)action.clone();
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				} else {
					// Recherche récurssive
					AlgoRecherche recussif = new AlgoRecherche(test);
					Vector<Action> lstActionR = new Vector<Action>();
					lstActionR.add(action);
					lstActionR.addAll(recussif.recherche(coup - 1 ));
					/* Recherche si la solution est la meilheure */
					if (/* lstActionTop n'est pas initialisé */
						lstActionTop == null ||
						/* solution avec moins de coups qui détruit toutes les bulles */
						lstActionR.lastElement().nbBulle == 0  &&
						lstActionTop.lastElement().nbBulle == 0 && 
						lstActionTop.size() >= lstActionR.size() ||
						/* solution qui détruit le maximum de bulles */
						lstActionTop.lastElement().nbBulle > lstActionR.lastElement().nbBulle 
						
					)
					 {
							lstActionTop = lstActionR;
					 } else if (lstActionTop.lastElement().nbBulle == lstActionR.lastElement().nbBulle) {
						 // optimisation des coups
						 if (Action.nbCoupTotal(lstActionTop.iterator()) < Action.nbCoupTotal(lstActionR.iterator())) {
							 System.out.println("lstActionTop + " + Action.debugAffiche(lstActionTop.iterator()) + "  " + Action.debugAffiche(lstActionR.iterator()));
							 lstActionTop = lstActionR;
							
						 } else {
							 System.out.println("lstActionTop - " + Action.debugAffiche(lstActionTop.iterator()) + "  " + Action.debugAffiche(lstActionR.iterator()));
								
						 }
						// optimisation des points
						 if (lstActionTop.lastElement().nbBulleExplo < lstActionR.lastElement().nbBulleExplo) {
							 
						 }
					 }
					
					
				}
				
			}
		}
		
		if (coup == 0) {
			Vector<Action> lstAction = new Vector<Action>();
			lstAction.add(actionTop);
			return lstAction;
		} else {
			return lstActionTop;
		}
		 
	}
	public int[] getAction() {
		return action;
	}
}
