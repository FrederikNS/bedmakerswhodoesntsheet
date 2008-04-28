package gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame implements SharedVariables{

	/**
	 * Serialised UID
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	
	public MainFrame(){
		
		JMenuBar menu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		
		JTabbedPane mode = new JTabbedPane();
		setJMenuBar(menu);
		menu.add(fileMenu);
		menu.add(helpMenu);
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(quitMenuItem);
		
		mode.addTab("Gantt", gantt);
		mode.addTab("Tasks", tasks);
		mode.addTab("Resources", resources);
		mode.addTab("Resource Usage", resourceUsage);
		
		add(mode);
		
		pack();
		setVisible(true);
	}
}
