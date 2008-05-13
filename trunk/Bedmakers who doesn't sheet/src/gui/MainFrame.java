package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author Frederik Nordahl Sabroe
 *
 */
public class MainFrame extends JFrame{

	/**
	 * Serialised UID
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	
	/**
	 * The method for displaying the primary frame 
	 */
	public MainFrame(){
		super("Planner");
		
		JMenuBar menu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		
		JTabbedPane mode = new JTabbedPane();
		mode.setPreferredSize(new Dimension(1024,768));
		
		JPanel toolbar = new Toolbar();
		
		setJMenuBar(menu);
		menu.add(fileMenu);
		menu.add(helpMenu);
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(quitMenuItem);
		
		mode.addTab("Gantt", SharedVariables.gantt=new Gantt());
		mode.addTab("Tasks", SharedVariables.tasks=new Tasks());
		mode.addTab("Resources", SharedVariables.resources=new Resources());
		mode.addTab("Resource Usage", SharedVariables.resourceUsage=new ResourceUsage());
		
		add(mode);
		add(toolbar,BorderLayout.NORTH);
		
		pack();
		setVisible(true);
	}
}
