package fr.piratekado.aqua;

import java.net.MalformedURLException;

import fr.piratekado.aqua.grille.Grille;

import junit.framework.TestCase;

public class TestGrille extends TestCase {
	public void testCalcul  () throws MalformedURLException {

		/* video Niv 7 */
		int testSimple [][] = {{3,0,2,3,2,3},
	            {3,2,2,2,1,2},
	            {1,2,4,2,0,1},
	            {1,0,4,2,0,4},
	            {1,1,3,3,2,2},
	            {3,3,2,2,3,0}};
		
		Grille test = new Grille(testSimple,null);
		int result[][];
		test.action(1, 1);
		int testSimpleResult [][] = {{3,0,2,3,2,3},
	            {3,3,2,2,1,2},
	            {1,2,4,2,0,1},
	            {1,0,4,2,0,4},
	            {1,1,3,3,2,2},
	            {3,3,2,2,3,0}};
		result = test.getInfo();
		assertEquals(this.toString(result), this.toString(testSimpleResult));
		
		
		
		/* video Niv 7 */
		int info [][] = {{3,0,2,3,2,3},
	            {3,2,2,2,1,2},
	            {1,2,4,2,0,1},
	            {1,0,4,2,0,4},
	            {1,1,3,3,2,2},
	            {3,3,2,2,3,0}};
		
		test = new Grille(info,null);
		test.action(2, 3);
		int info2[][] = {{3,0,2,3,2,3},
	            {3,2,4,2,1,2},
	            {1,3,0,3,0,1},
	            {2,0,0,3,0,4},
	            {1,2,0,4,2,2},
	            {3,3,3,2,3,0}};
		result = test.getInfo();
		
		assertEquals(this.toString(result), this.toString(info2));
		
		test.action(3, 3);
		int info3[][] = {{3,0,2,3,2,3},
	            {3,2,4,2,1,2},
	            {1,3,0,3,0,1},
	            {2,0,0,4,0,4},
	            {1,2,0,4,2,2},
	            {3,3,3,2,3,0}};
		result = test.getInfo();
		assertEquals(this.toString(result), this.toString(info3));
		
		test.action(3, 3);
		int info4[][] = {{3,0,2,3,2,3},
	            {3,2,4,3,1,2},
	            {1,4,0,0,0,3},
	            {4,0,0,0,0,0},
	            {1,3,0,0,3,3},
	            {3,3,3,4,3,0}};
		
		result = test.getInfo();
		assertEquals(this.toString(result), this.toString(info4));
	}
	public String toString (int[][] tab) {
	    StringBuffer res = new StringBuffer();
		  for (int y = 0; y<6;y++) {
			  res.append("[");
	  	  for (int x = 0; x<6;x++) {
	  		  res.append(tab [y][x]).append(",");
	  	  }
		  res.append("]");
	   }
	   return res.toString();	
	}
	
}
