package src.windows;

import java.awt.*;
import javax.swing.*;
//import javax.swing.table.DefaultTableCellRenderer;

public class BoardWindow extends JFrame{
	
	private static final long serialVersionUID = 42;
	public Container bwFrame; 
	//public DefaultTableCellRenderer io_rd_renderer;
	public JPanel panel;
	public JTable table;
	public JScrollPane pane;
	public String type; // Determinar se é Cliente ou Servidor
	
	public BoardWindow(String type){
		
		// Determina nome Janela
		super("Batalha Naval XP");
		
		// Cria um Container
		bwFrame = this.getContentPane();
		
		// Cria um Painel
        panel = new JPanel();
        
        // Cria uma Tabela
        table = new JTable(11, 11);
        
        // Determina a cor das bordas da tabela
        Color c = Color.BLACK;
        table.setGridColor(c);
        
        // Colorir coluna
        //Color index = Color.GREEN;
        //io_rd_renderer.setBackground(index);
        //table.getColumnModel().getColumn(0).setCellRenderer(io_rd_renderer);
        
        // Retira o cabeçalho da tabela
        table.setTableHeader(null);
        
        // Exibe as bordas da tabela
        table.setShowGrid(true);
        
        // Númera a primeira coluna e linha
        setFirstColumn(table);
        setFirstRow(table);
        
        // Cria um frame para colocar a tabela
        pane = new JScrollPane(table);
        
        // Adiciona frame ao painel
        panel.add(pane);
        
        // Adiciona o painel ao container
        bwFrame.add(panel);
                
        // Configura detalhes do Frame
        // 1. Encerrar Applicação ao Fechar
        // 2. Setar Frame como Visível
        // 3. Setar as dimensões do Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 1
        this.setVisible(true); // 2
        this.setSize(1024, 768); // 3		

		// Guardar na classe que tipo de conexão está sendo feita (Cliente ou Servidor)
		setType(type.toUpperCase());
		
		// Monta o Jogo
		mountBoard(table);
	}
	
	public void setFirstRow(JTable table){
		
		// Seta valores para a Primeira Linha
		for (int i = 0; i <= 10; i++) {
			table.setValueAt(String.valueOf(i), i, 0);
		}		
	}
	
	public void setFirstColumn(JTable table){
		
		// Seta valores para a Primeira Coluna
		for (int i = 0; i <= 10; i++) {
			table.setValueAt(String.valueOf(i), 0, i);
		}
	}
	
	public void mountBoard(JTable table){
		
		new MountWindow(table);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
