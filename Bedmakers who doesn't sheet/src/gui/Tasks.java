package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Tasks extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4421925036291362007L;

	public Tasks(){
		JScrollPane scroller = new JScrollPane();
		JTable listOfTasks = new JTable();
		
		add(scroller);
		scroller.add(listOfTasks);
	}
}
