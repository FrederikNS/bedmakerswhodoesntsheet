package gui;

import javax.swing.JPanel;

/**
 * @author Frederik Nordahl Sabroe
 *
 */
public class SharedVariables {
	
	/**
	 * The Gantt Panel
	 */
	public static JPanel gantt;
	/**
	 * The Resource Panel
	 */
	public static JPanel resources;
	/**
	 * The Resource Usage Panel
	 */
	public static JPanel resourceUsage;
	/**
	 * The Task Panel
	 */
	public static JPanel tasks;
	
	/**
	 * Example Data
	 */
	public static String taskTableData[][] = {{"Sleeping","Today","Tomorrow","FrederikNS"},
			{"Sleeping","Today","Tomorrow","Jacob"}};
	/**
	 * Example Data
	 */
	public static Object[][] resourceTableData = {{"Frederik Nordahl Sabroe","FNS","SoftEng","Coding","Everything"}};

}
