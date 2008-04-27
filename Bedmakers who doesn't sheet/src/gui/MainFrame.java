package gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame implements SharedVariables{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	
	public MainFrame(){
		JTabbedPane mode = new JTabbedPane();
		mode.addTab("Gantt", gantt);
		mode.addTab("Tasks", tasks);
		mode.addTab("Resources", resources);
		mode.addTab("Resource Usage", resourceUsage);
		
		add(mode);
	}
}
