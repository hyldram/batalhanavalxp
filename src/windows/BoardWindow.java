package windows;

import java.awt.Color;
import java.awt.Container;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;


public class BoardWindow extends JFrame{
	
	private static final long serialVersionUID = 42;

	public BoardWindow(){
		
		super("TESTE");
		
		DefaultTableCellRenderer io_rd_renderer = new DefaultTableCellRenderer();
		Container bwFrame = this.getContentPane();
		JPanel panel = new JPanel();
		JTable table = new JTable(11, 11);
		
		Color c = Color.BLACK;
		Color index = Color.GREEN;
		
		table.setGridColor(c);
		
		io_rd_renderer.setBackground(index);
		table.getColumnModel().getColumn(0).setCellRenderer(io_rd_renderer);
		
		table.setTableHeader(null);
		table.setShowGrid(true);
		
		JScrollPane pane = new JScrollPane(table);
		
		panel.add(pane);
		bwFrame.add(panel);
			
		// Configura detalhes do Frame
		// 1. Encerrar Applicação ao Fechar
		// 2. Setar Frame como Visível
		// 3. Setar as dimensões do Frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 1
		this.setVisible(true); // 2
		this.setSize(1024, 768); // 3
		
		table.setValueAt("1", 1, 0);
		table.setValueAt("2", 2, 0);
		table.setValueAt("3", 3, 0);
		table.setValueAt("4", 4, 0);
		table.setValueAt("5", 5, 0);
		table.setValueAt("6", 6, 0);
		table.setValueAt("7", 7, 0);
		table.setValueAt("8", 8, 0);
		table.setValueAt("9", 9, 0);
		table.setValueAt("10", 10, 0);
		
		table.setValueAt("1", 0, 1);
		table.setValueAt("2", 0, 2);
		table.setValueAt("3", 0, 3);
		table.setValueAt("4", 0, 4);
		table.setValueAt("5", 0, 5);
		table.setValueAt("6", 0, 6);
		table.setValueAt("7", 0, 7);
		table.setValueAt("8", 0, 8);
		table.setValueAt("9", 0, 9);
		table.setValueAt("10", 0, 10);
		
		table.setValueAt("teste", 1, 1);
		
		
		}
}
