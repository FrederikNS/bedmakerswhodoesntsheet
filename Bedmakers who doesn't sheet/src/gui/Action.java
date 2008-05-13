package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Frederik Nordahl Sabroe
 *
 */
public class Action implements ActionListener,SharedConstants {

	@Override
	public void actionPerformed(ActionEvent e) {
		int command = Integer.parseInt(e.getActionCommand());
		switch(Actions.values()[command]){
		
		}
	}
}
