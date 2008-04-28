package gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel {

	/**
	 * Serialised UID
	 */
	private static final long serialVersionUID = -5707394191276063225L;

	public Toolbar(){
		JButton newProject = new JButton("New Project");
		JButton removeProject = new JButton("Remove Project");
		JButton addActivity = new JButton("Add Activity");
		JButton removeactivity = new JButton("Remove Activity");
		JButton addEmployee = new JButton("Add Employee");
		JButton removeEmployee = new JButton("Remove Employee");
		JButton edit = new JButton("Edit");

		add(newProject);
		add(removeProject);
		add(addActivity);
		add(removeactivity);
		add(addEmployee);
		add(removeEmployee);
		add(edit);
	}
}
