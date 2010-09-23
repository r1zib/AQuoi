package fr.piratekado.aqua;

import java.net.MalformedURLException;
import java.util.Vector;

import fr.piratekado.aqua.grille.Grille;

import junit.framework.TestCase;

public class TestAlgoRecherche extends TestCase{
	public void testAlgo  () throws MalformedURLException {
		int test [][] = {{4,3,3,4,2,2},
	            {2,2,2,4,1,1},
	            {2,2,2,4,2,4},
	            {2,4,2,4,2,2},
	            {3,4,4,2,2,2},
	            {3,2,2,3,1,4}};	
	 AlgoRecherche algo = new AlgoRecherche (new Grille(test,null));
	 Vector<Action> result = algo.recherche(2);
	 System.out.println(result.toString());
	 assertEquals(1,result.size());
	 assertEquals(3,result.firstElement().x);
	 assertEquals(2,result.firstElement().y);
		int test2 [][] = {{2,3,2,2,2,2},
	            {3,3,3,4,2,2},
	            {3,3,4,2,2,3},
	            {3,4,2,1,4,2},
	            {2,1,2,4,2,3},
	            {2,2,3,2,2,3}};	
		algo = new AlgoRecherche (new Grille(test2,null));
		result = algo.recherche(2);
		for (int i = 0; i< result.size(); i++) {
			Action act = result.elementAt(i);
			System.out.println("x " + act.x + "  " + act.y + " " + act.nbBulle);
		}
		
		
	 
	}
}
