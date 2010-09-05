package fr.piratekado.aqua;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JApplet;

public class Aqua extends JApplet {
		private static final long serialVersionUID = 1L;
		private LstBtn lstBtn;
		private Tableau tableau;
		public final static int DELAY = 200;

		
		public void init(){
			/*	
			String name = this.getCodeBase().toString();
				
				if (!name.startsWith("http://piratekado.free.fr") && false) {
					JOptionPane.showMessageDialog(this,	"Problème d'url : " + name );
					return;
				}
				*/
				lstBtn = new LstBtn();
				this.setLayout(new BorderLayout ());
				this.add(lstBtn,BorderLayout.NORTH );
				tableau = new Tableau();
				tableau.setSize(310, 330);
				tableau.setMaximumSize(new Dimension(310,330));
				lstBtn.setInterfaceBtn(tableau);
				this.add(tableau,BorderLayout.CENTER );
				
				this.setSize(310, 330);

		} 
		

}		