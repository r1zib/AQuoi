package fr.piratekado.aqua;

import java.net.MalformedURLException;
import java.util.Vector;

import javax.swing.ImageIcon;

import fr.piratekado.aqua.image.AnalyseImage;
import fr.piratekado.aqua.image.AnalyseImageBase;

import junit.framework.TestCase;

public class TestAnalyseImage extends TestCase {

	
	
public TestAnalyseImage() {
	// TODO Auto-generated constructor stub
	super();
	
}	


public void testCompareImage  () throws MalformedURLException {
	
	AnalyseImage ana = new AnalyseImage();
	ana.setImage(AnalyseImageBase.toBufferedImage(new ImageIcon(getClass().getResource("/test1.png")).getImage()));
	ana.compareImage();
	int info [][] = ana.getInfo();
	int resul [][] = {{4,3,3,4,2,2},
		              {2,2,2,4,1,1},
		              {2,2,2,4,2,4},
		              {2,4,2,4,2,2},
		              {3,4,4,2,2,2},
		              {3,2,2,3,1,4}};
	assertEquals(this.toString(resul), this.toString(info));
	
	ana.setImage(AnalyseImageBase.toBufferedImage(new ImageIcon(getClass().getResource("/test2.png")).getImage()));
	ana.compareImage();
	info = ana.getInfo();
	int resul2 [][] = {{0,0,1,4,2,4},
		  	 {0,4,2,0,0,0},
		     {2,3,1,0,0,2},
		     {1,1,1,0,3,2},
		     {3,2,4,3,0,2},
		     {3,3,4,1,1,1}};
	assertEquals(this.toString(resul2), this.toString(info));

	ana.setImage(AnalyseImageBase.toBufferedImage(new ImageIcon(getClass().getResource("/test3.png")).getImage()));
	ana.compareImage();
	info = ana.getInfo();
	int resul3 [][] = {{4,3,2,2,1,1},
		  	 {2,2,3,3,3,2},
		     {1,3,2,2,1,2},
		     {4,4,4,2,3,3},
		     {2,1,1,3,4,4},
		     {4,1,4,2,2,4}};
	assertEquals(this.toString(resul3), this.toString(info));
	
	ana.setImage(AnalyseImageBase.toBufferedImage(new ImageIcon(getClass().getResource("/test4.png")).getImage()));
	ana.compareImage();
	info = ana.getInfo();
	int  resul4 [][]= {{3,3,2,2,-1,3},
		  	 {4,3,0,3,2,0},
		     {2,1,3,4,2,3},
		     {0,0,4,3,1,3},
		     {3,0,0,3,4,1},
		     {2,2,0,2,1,3}};
	assertEquals(this.toString(resul4), this.toString(info));
	
	ana.setImage(AnalyseImageBase.toBufferedImage(new ImageIcon(getClass().getResource("/test5.png")).getImage()));
	ana.compareImage();
	info = ana.getInfo();
	int  resul5 [][]= {{3,2,2,3,0,3},
		  	 {-1,4,1,3,2,3},
		     {0,2,4,3,4,0},
		     {3,0,3,3,4,2},
		     {0,2,0,1,4,3},
		     {4,3,2,4,3,2}};
	assertEquals(this.toString(resul5), this.toString(info));
	
		
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