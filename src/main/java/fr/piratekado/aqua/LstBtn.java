package fr.piratekado.aqua;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LstBtn extends JPanel  {
	private static final long serialVersionUID = 1L;
	
	private JButton photo;
	private JButton theme;
	
	private InterfaceBtn interfaceBtn;

	public LstBtn() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		/* Gestion du bouton loupe */
		photo = new JButton();
		photo.setIcon( ApplicationRessource.getPhoto(this));
		photo.setPreferredSize(new Dimension(70,30));
		photo.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfaceBtn.actionPhoto();
			}
		});
		theme = new JButton();
		theme.setPreferredSize(new Dimension(70,30));
		theme.setText("theme");
		theme.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationRessource.newTheme();
			}
		});
		this.add(theme);
		this.add(photo);
	}
	
	
	public InterfaceBtn getInterfaceBtn() {
		return interfaceBtn;
	}
	public void setInterfaceBtn(InterfaceBtn interfaceBtn) {
		this.interfaceBtn = interfaceBtn;
	}


	
}
