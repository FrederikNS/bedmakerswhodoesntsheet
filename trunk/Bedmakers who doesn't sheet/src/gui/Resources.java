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
public class Resources extends JPanel {

	/**
	 * Serialised UID
	 */
	private static final long serialVersionUID = 3621067286944916555L;

	public Resources(){
		this.setLayout(new GridLayout(0,1));

		String columnNames[] = {"Name","Initials","Assigned Project","Assigned Activities","Assigned 1337erships"};
		JTable taskTable = new JTable(SharedVariables.resourceTableData,columnNames);
		JScrollPane scroller = new JScrollPane(taskTable);

		taskTable.setFillsViewportHeight(true);
		taskTable.addColumn(new TableColumn());

		add(scroller);
	}

}