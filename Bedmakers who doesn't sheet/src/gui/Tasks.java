package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 * @author Frederik Nordahl Sabroe
 *
 */
public class Tasks extends JPanel {
	
	/**
	 * Serialised UID
	 */
	private static final long serialVersionUID = 4421925036291362007L;

	public Tasks(){
		this.setLayout(new GridLayout(0,1));
		
		String columnNames[] = {"Task","Start Date","Deadline","Participants"};
		JTable taskTable = new JTable(SharedVariables.taskTableData,columnNames);
		JScrollPane scroller = new JScrollPane(taskTable);
		
		taskTable.setFillsViewportHeight(true);
		taskTable.addColumn(new TableColumn());
		
		add(scroller);
	}
}
