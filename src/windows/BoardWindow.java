package src.windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import src.main.*;
import src.connections.*;

public class BoardWindow extends JFrame{
	
	private static final long serialVersionUID = 42;
	
	protected Container bwFrame; // container que guarda objetos
	//public DefaultTableCellRenderer io_rd_renderer;
	protected JPanel panel;	// container que guarda pane
	public JTable table;	// tabela que guarda posição das peças
	public JTable tableEnemy;	// tabela que guarda os tiros dados
	protected JTable tableScore;	// tabela que guarda pontuação
	protected JScrollPane pane;		// container que guarda table das peças
	protected JScrollPane paneEnemy;	// containerq que guarda table dos tiros enviados
	protected JScrollPane paneScore;	// container que guarda table da pontuaçãp
	protected String type; // Determinar se é Cliente ou Servidor
	protected JTextField tf1c = new JTextField();  // textfield onde será inserido a coluna do tiro
	protected JTextField tf1r = new JTextField();  // textfield onde será inserido a linha do tiro 
	protected Object[] message;	// objeto que guarda mensagem
	protected Object[] errorMessage;	// objeto que guarda mensagem de erro
	protected JLabel player1 = new JLabel("Seu Tabuleiro  --->");	// label indicativo
	protected JLabel player2 = new JLabel("Respostas ------>");	// label indicativo
	protected JButton btShot;	// botão iniciar disparo (chama método)
	protected Server socketServer; // guarda referencia do objeto server
	protected Client socketClient;	// guarda referencia do objeto client
	public Board board;	// guarda referencias dos objetos
	public List<String> shots = new ArrayList<String>(); // arraylist contendo as posições enviadas
	public JRadioButton btHorizontal = new JRadioButton();
	public JRadioButton btVertical = new JRadioButton();
	
	// Método construtor da Tela que contem os Tabuleiros
	//public BoardWindow(int[][] tabuleiro, String gameType, Server server, Client client){
	public BoardWindow(String gameType, Server server, Client client){
		// Determina nome Janela
		super("Batalha Naval XP");
		
		// Cria um Container
		bwFrame = this.getContentPane();
		
		// Guarda qual o modo que está rodando
		setType(gameType);
		
		// Cria um Painel
        panel = new JPanel();
        
        // Cria uma Tabela
        table = new JTable(11, 11);
        tableEnemy = new JTable(11,11);
        tableScore = new JTable(3, 4);
        
        // Determina a cor das bordas da tabela
        Color c = Color.BLACK;
        table.setGridColor(c);
        tableEnemy.setGridColor(c);
        tableScore.setGridColor(c);
        
        // Colorir coluna
        //Color index = Color.GREEN;
        //io_rd_renderer.setBackground(index);
        //table.getColumnModel().getColumn(0).setCellRenderer(io_rd_renderer);
        
        // Retira o cabeçalho da tabela
        table.setTableHeader(null);
        tableEnemy.setTableHeader(null);
        tableScore.setTableHeader(null);
        
        // Exibe as bordas da tabela
        table.setShowGrid(true);
        tableEnemy.setShowGrid(true);
        tableScore.setShowGrid(true);
        
        // Númera a primeira coluna e linha
        setFirstColumn(table);
        setFirstRow(table);
        
        setFirstColumn(tableEnemy);
        setFirstRow(tableEnemy);
        
        mountTableScore(tableScore);
        
        // Cria um frame para colocar a tabela e ajusta o seu tamanho
        
        // Tabela "Seu tabuleiro"
        pane = new JScrollPane(table);
        pane.getViewport().setPreferredSize(table.getPreferredSize());
        
        // Tabela "Respostas"
        paneEnemy = new JScrollPane(tableEnemy);
        paneEnemy.getViewport().setPreferredSize(tableEnemy.getPreferredSize());
        
        // Tabela de Pontuação
        paneScore = new JScrollPane(tableScore);
        paneScore.getViewport().setPreferredSize(tableScore.getPreferredSize());
        
        // Botão para Iniciar Disparo
        btShot = new JButton("Iniciar Disparo");
        btShot.setSize(50, 50);
        btShot.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
            {
        		// Instancia Objeto a ser enviado por Socket
        		Shot shot = new Shot();
        		
        		int check = 1;
            	int error = 0;
            	
            	// Enquanto não foi apertado o botão certo e os dados não estiverem corretos, o aplicativo irá solicitar coordenadas
            	while (check != 0){
            		
            		// Limpa possíveis rastros nos JTexts
            		tf1c.setText(null);
        			tf1r.setText(null);
        			
        			// Cria campos para Inserção
        			message = new Object[] {  
        					"Coordenadas do Tiro","Coluna", tf1c, "Linha", tf1r};
        		
        			// Cria Mensagem de Erro
        			errorMessage = new Object[] {"Verifique os coordenadas inseridos, pois existem coordenadas inválidas.\n" +
													"Coordenadas válidas são de 1 a 10. Letras não são válidas\n" +
        											"Coordenadas repetidas."};
        				
        			// Solicita dados aos usuário
        			check = JOptionPane.showConfirmDialog(null, message, "Inserir coordenada do Tiro", JOptionPane.OK_OPTION);
        			
        			// Se o botão pressionado for Sim
        			if (check == 0){
        				
        				// Faz validação do tiro. Se for válidado envia tiro pelo Socket. Se não exibe mensagem de erro
        				if (validateShot(tf1r.getText(), tf1c.getText(), shots)){
        					
        					// Cria Objeto a ser enviado
        					shot.createShot(tf1r.getText(), tf1c.getText(), board);
        					
        					try {
        						
        						// Verifica se é Cliente ou Servidor. Desabilita o Botão para não enviar mais de um tiro e envia objeto por Socket
        						if (type.equals("Server")){
        							btShot.setEnabled(false);
        							socketServer.sendObject.flush();
        							socketServer.sendObject.writeObject(shot);
        							socketServer.sendObject.flush();
        						}
        						
        						if (type.equals("Client")){
        							btShot.setEnabled(false);
        							socketClient.sendObject.flush();
        							socketClient.sendObject.writeObject(shot);
        							socketClient.sendObject.flush();
        						}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
        				}else {
        					// Mensagem de erro
    						error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
    						check = 1;
        				}
        			}	
            	}
            }
        });
        
        // Adiciona frame ao painel
        panel.add(player1);
        panel.add(pane);
        panel.add(player2);
        panel.add(paneEnemy);
        panel.add(paneScore);
        panel.add(btShot);
        panel.setAlignmentX(CENTER_ALIGNMENT);
        
        // Adiciona o painel ao container
        bwFrame.add(panel);
        
        // Instancia Objeto Board
        board = new Board();
        
        // Inicia o jogo pelo Servidor
        if (type.equals("Client")){
        	btShot.setEnabled(false);
        }
        
        // Adiciona as referências aos objetos que serão usados pelas Classes na thread
        if (getType().equals("Server")){
			socketServer = server;
			socketServer.setServerTable(table);
			socketServer.setServerScore(tableScore);
			socketServer.setServerButton(btShot);
			socketServer.setServerTableAnswer(tableEnemy);
		}else{
			socketClient = client;
			socketClient.setClientTable(table);
			socketClient.setClientScore(tableScore);
			socketClient.setClientButton(btShot);
			socketClient.setClientTableAnswer(tableEnemy);
		}
			
        // Configura detalhes do Frame
        // 1. Encerrar Applicação ao Fechar
        // 2. Setar Frame como Visível
        // 3. Setar as dimensões do Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 1
        this.setVisible(true); // 2
        this.setSize(1024, 460); // 3		
    	
        btHorizontal.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
            {
        		if (btHorizontal.isSelected()){
        			btVertical.setSelected(false);
        		}
            }
        });
        
        btVertical.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
            {
        		if (btVertical.isSelected()){
        			btHorizontal.setSelected(false);
        		}
            }
        });
        
        mountBoard(table);
        
        // Percorre matriz do tabuleiro preenchida com valores dos barcos posicionados pelo usuario
    	/*for( int linha = 1; linha < 10; linha++ )
        {
        	for( int coluna = 1; coluna < 10; coluna++ )
        	{
        		// Se houver algum barco na posicao corrente, exibe na celula da tabela
        		if ( tabuleiro[linha][coluna] > 0 )
        		{
        			// Coloca as Iniciais dos Barcos(P, Q, T, D e S)
        			switch (tabuleiro[linha][coluna]){
	        			case 1:
	        			{
	        				table.setValueAt("S", linha, coluna);
	        				break;
	        			}
	        			case 2:
	        			{
	        				table.setValueAt("D", linha, coluna);
	        				break;
	        			}
	        			case 3:
	        			{
	        				table.setValueAt("T", linha, coluna);
	        				break;
	        			}
	        			case 4:
	        			{
	        				table.setValueAt("Q", linha, coluna);
	        				break;
	        			}
	        			case 5:
	        			{
	        				table.setValueAt("P", linha, coluna);
	        				break;
	        			}
        			}
        			
        		}
        	}		
        }*/
    	
	}

	// Seta valores para a Primeira Linha
	public void setFirstRow(JTable table){
		
		for (int i = 0; i <= 10; i++) {
			table.setValueAt(String.valueOf(i), i, 0);
		}		
	}
	
	// Seta valores para a Primeira Coluna
	public void setFirstColumn(JTable table){
		
		for (int i = 0; i <= 10; i++) {
			table.setValueAt(String.valueOf(i), 0, i);
		}
	}
	
	// Retorna a variável Type
	public String getType() {
		return type;
	}

	// Seta variável Type
	public void setType(String type) {
		this.type = type;
	}
	
	// Método que verifica se tem algum zero
	public boolean hasZeros(String row, String column){
		
		boolean check = false; 
		
		if (row.equals("0") || column.equals("0")){
			 return true;
		}
		
		return check;
	}
	
	// Método que verifica se tem alguma letra
	public boolean hasLetters(String row, String column){
		
		boolean check = false;
		
		Pattern pattern = Pattern.compile("[0-9]");  
        Matcher matchRow = pattern.matcher(row);
        Matcher matchColumn = pattern.matcher(column);
          
        if(!matchRow.find() || !matchColumn.find()) {   
			return true;
		}
		
		return check;
	}
	
	// Método que verifica se tem algum valor ""
	public boolean hasNull(String row, String column){

		boolean check = false;
		
		if (row.equals("") || column.equals("")){
			return true;
		}
		
		return check;
		
	}
	
	// Verifica se as coordenadas são válidas
	public boolean isCoordenatesOk(String row1, String column1, int size, int type){
		
		boolean check = false;
		
		if (type == 1){
			if (Integer.parseInt(column1) + size - 1 <= 10) {
				check = true;
			}
		}else{
			if (Integer.parseInt(row1) + size - 1 <= 10) {
				check = true;
			}
		}
		System.out.println(check);
		return check;
	}
	
	// Verifica se nas coordenadas enviadas, não existe nenhuma peça
	public boolean isPositionOk(JTable table, String row1, String column1, int size, int type){
	
		boolean check = false;
		
		for (int i = 0; i <= size - 1; i++) {
				
			if (type == 1){
				if (table.getValueAt(Integer.parseInt(row1), Integer.parseInt(column1) + i) == null){
					check = true;
				}else {
					return false;
				}
			}else {
				if (table.getValueAt(Integer.parseInt(row1) + i, Integer.parseInt(column1)) == null){
					check = true;
				}else {
					return false;
				}
			}
		}
		System.out.println(check);
		return check;
	}
	
	// Monta as Colunas e Linhas da tabela com pontuação
	public void mountTableScore(JTable table){
		
		table.setValueAt("Seus Pontos", 1, 0);
		table.setValueAt("Pontos Rival", 2, 0);
		
		table.setValueAt("Qntd. Tiros", 0, 1);
		table.setValueAt("Acertos", 0, 2);
		table.setValueAt("Restam", 0, 3);
	}
	
	// Método para validação do Tiro
	public boolean validateShot(String row, String column, List<String> shots){
		
		boolean check = false;
		
		// Verifica se essas coordenadas já foram usadas. Se foram usadas avisa usuário
		if (!shots.isEmpty()){
			
			for (int i = 0; i < shots.size(); i++) {
				
				if (shots.get(i).equals(column + row)){
					
					return false;
				}
			}		
		}
		
		// Se coordenadas não foram usadas, guarda em um List
		shots.add(column + row);
		
		// Verifica se as coordenadas não tem Zeros, ou Letras ou passam do Limite do Tabuleiro
		if (!hasZeros(row, column) && !hasLetters(row, column) && !hasNull(row, column) && (Integer.parseInt(row)<=10)
				&& (Integer.parseInt(row)>0) && (Integer.parseInt(column)<=10) && (Integer.parseInt(column)>0)){
			
			check = true;
		}
		return check;
	}
	
	 // Método que monta o Tabuleiro e valida os valores inseridos
    public void mountBoard(JTable table){
            
            int check = 1;
            int error = 0;
            
            // Mensagem de erro com os valores
            errorMessage = new Object[]{"Verifique os coordenadas inseridos, pois existem coordenadas inválidos.\n" +
                                        "Coordenadas válidas são de 1 a 10. Letras não são válidas\n" +
                                        "Coordenadas devem ser valores seqüênciais (ou para Direita ou para Esquerda).\n" +
                                        "Não é permitido usar Coordenadas repetidas. Selecionar Horizontal ou Vertical."};
            
            
            // Insere 1 Submarino(1 peça)
            btHorizontal.setSelected(true);
            btVertical.setSelected(false);
            message = new Object[] {  
            "Posição Inicial","Coluna", tf1c, "Linha", tf1r, "Horizontal", btHorizontal, "Vertical", btVertical};
    
            while(check != 0){
                    
                    // Limpa possíveis rastros nos inputs 
                    tf1c.setText(null);
                    tf1r.setText(null);
                    
                    
                    // Solicita dados aos usuário
                    check = JOptionPane.showConfirmDialog(null, message, "Inserir 1 Submarino (1 peça)", JOptionPane.OK_OPTION);
                    
                    // Verifica qual é o opção escolhida
                    if (check == 0){
                            
                    	int position = checkRadioButton(btHorizontal, btVertical);
                    	
                    		if ((position == 1) || (position == 2)){ 
                    	
	                            // Verifica se tem Zeros, Letras e "". SE NÃO TEM insere valores na tabela
	                            // SE TEM exibe mensagem de erro e solicita novamente ao usuário
	                            if (!hasZeros(tf1r.getText(), tf1c.getText())  &&  
	                                    !hasLetters(tf1r.getText(), tf1c.getText()) && 
	                                    !hasNull(tf1r.getText(), tf1c.getText()) && 
	                                    isCoordenatesOk(tf1r.getText(), tf1c.getText(), 1, position) &&
	                                    isPositionOk(table, tf1r.getText(), tf1c.getText(), 1, position)){
	                                    
	                                    // Insere na tabela
	                            		for (int i = 0; i < 1; i++) {
                            				if (position == 1){
                            					table.setValueAt("S", Integer.parseInt(tf1r.getText()), Integer.parseInt(tf1c.getText()) + i);
                            				}else {
                            					table.setValueAt("S", Integer.parseInt(tf1r.getText()) + i, Integer.parseInt(tf1c.getText()));
                            				}
	                            		}
	              
	                            }else {
	                                    
	                                    // Mensagem de erro
	                                    error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
	                                    check = 1;
	                            }
                    		}else {
                    			// Mensagem de erro
                                error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
                                check = 1;
                    		}
                    }
            }
            
            check = 1;
            
            // Insere 1 navio 2 canhões
            btHorizontal.setSelected(true);
            btVertical.setSelected(false);
            message = new Object[] {  
            "Posição Inicial", "Coluna", tf1c, "Linha", tf1r, "Horizontal", btHorizontal, "Vertical", btVertical};
            while(check != 0){
                    
                    // Limpa possíveis rastros nos inputs
                    tf1c.setText(null);
                    tf1r.setText(null);

                    
                    // Solicita dados aos usuário
                    check = JOptionPane.showConfirmDialog(null, message, "Incluir 1 Navio de 2 Canhões (2 peças)", JOptionPane.OK_OPTION);
                    
                    // Verifica qual é o opção escolhida
                    if (check == 0){
                            
                    	int position = checkRadioButton(btHorizontal, btVertical);
                    	
                		if ((position == 1) || (position == 2)){
                    	
                            // Verifica se tem Zeros, Letras e "". SE NÃO TEM insere valores na tabela
                            // SE TEM exibe mensagem de erro e solicita novamente ao usuário
                            if (!hasZeros(tf1r.getText(), tf1c.getText()) && 
                                !hasLetters(tf1r.getText(), tf1c.getText()) && 
                                !hasNull(tf1r.getText(), tf1c.getText()) && 
                                isCoordenatesOk(tf1r.getText(), tf1c.getText(), 2, position) && 
                                isPositionOk(table, tf1r.getText(), tf1c.getText(), 2, position)){
                                    
                                    
                            	for (int i = 0; i < 2; i++) {
                    				if (position == 1){
                    					table.setValueAt("D", Integer.parseInt(tf1r.getText()), Integer.parseInt(tf1c.getText()) + i);
                    				}else {
                    					table.setValueAt("D", Integer.parseInt(tf1r.getText()) + i, Integer.parseInt(tf1c.getText()));
                    				}
                        		}
                                    
                            }else {
                                    
                                    // Mensagem de erro
                                    error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
                                    check = 1;
                            }
                        }else {
                    			// Mensagem de erro
                                error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
                                check = 1;
                		}
                    }
            }
            
            check = 1;
            
            // Insere 1 navio 3 canhões
            btHorizontal.setSelected(true);
            btVertical.setSelected(false);
            message = new Object[] {  
            "Posição Inicial", "Coluna", tf1c, "Linha", tf1r, "Horizontal", btHorizontal, "Vertical", btVertical};
            while(check != 0){
                    
                    // Limpa possíveis rastros nos inputs
                    tf1c.setText(null);
                    tf1r.setText(null);

                    
                    // Solicita dados aos usuário
                    check = JOptionPane.showConfirmDialog(null, message, "Incluir 1 Navio de 3 Canhões (3 peças)", JOptionPane.OK_OPTION);
                    
                    // Verifica qual é o opção escolhida
                    if (check == 0){
                            
                    	int position = checkRadioButton(btHorizontal, btVertical);
                    	
                		if ((position == 1) || (position == 2)){
                    	
                            // Verifica se tem Zeros, Letras e "". SE NÃO TEM insere valores na tabela
                            // SE TEM exibe mensagem de erro e solicita novamente ao usuário
                            if (!hasZeros(tf1r.getText(), tf1c.getText()) && 
                                !hasLetters(tf1r.getText(), tf1c.getText()) && 
                                !hasNull(tf1r.getText(), tf1c.getText()) && 
                                isCoordenatesOk(tf1r.getText(), tf1c.getText(), 3, position) && 
                                isPositionOk(table, tf1r.getText(), tf1c.getText(), 3, position)){
                                    
                                    
                            	for (int i = 0; i < 3; i++) {
                    				if (position == 1){
                    					table.setValueAt("T", Integer.parseInt(tf1r.getText()), Integer.parseInt(tf1c.getText()) + i);
                    				}else {
                    					table.setValueAt("T", Integer.parseInt(tf1r.getText()) + i, Integer.parseInt(tf1c.getText()));
                    				}
                        		}
                                    
                            }else {
                                    
                                    // Mensagem de erro
                                    error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
                                    check = 1;
                            }
                        }else {
                    			// Mensagem de erro
                                error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
                                check = 1;
                		}
                    }
            }
            
            check = 1;
            
            // Insere 1 navio 4 canhões
            btHorizontal.setSelected(true);
            btVertical.setSelected(false);
            message = new Object[] {  
            "Posição Inicial", "Coluna", tf1c, "Linha", tf1r, "Horizontal", btHorizontal, "Vertical", btVertical};
            while(check != 0){
                    
                    // Limpa possíveis rastros nos inputs
                    tf1c.setText(null);
                    tf1r.setText(null);

                    
                    // Solicita dados aos usuário
                    check = JOptionPane.showConfirmDialog(null, message, "Incluir 1 Navio de 4 Canhões (4 peças)", JOptionPane.OK_OPTION);
                    
                    // Verifica qual é o opção escolhida
                    if (check == 0){
                            
                    	int position = checkRadioButton(btHorizontal, btVertical);
                    	
                		if ((position == 1) || (position == 2)){
                    	
                            // Verifica se tem Zeros, Letras e "". SE NÃO TEM insere valores na tabela
                            // SE TEM exibe mensagem de erro e solicita novamente ao usuário
                            if (!hasZeros(tf1r.getText(), tf1c.getText()) && 
                                !hasLetters(tf1r.getText(), tf1c.getText()) && 
                                !hasNull(tf1r.getText(), tf1c.getText()) && 
                                isCoordenatesOk(tf1r.getText(), tf1c.getText(), 4, position) && 
                                isPositionOk(table, tf1r.getText(), tf1c.getText(), 4, position)){
                                    
                                    
                            	for (int i = 0; i < 4; i++) {
                    				if (position == 1){
                    					table.setValueAt("Q", Integer.parseInt(tf1r.getText()), Integer.parseInt(tf1c.getText()) + i);
                    				}else {
                    					table.setValueAt("Q", Integer.parseInt(tf1r.getText()) + i, Integer.parseInt(tf1c.getText()));
                    				}
                        		}
                                    
                            }else {
                                    
                                    // Mensagem de erro
                                    error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
                                    check = 1;
                            }
                        }else {
                    			// Mensagem de erro
                                error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
                                check = 1;
                		}
                    }
            }
            
            check = 1;
            
            // Insere 1 Porta Aviões
            btHorizontal.setSelected(true);
            btVertical.setSelected(false);
            message = new Object[] {  
            "Posição Inicial", "Coluna", tf1c, "Linha", tf1r, "Horizontal", btHorizontal, "Vertical", btVertical};
            while(check != 0){
                    
                    // Limpa possíveis rastros nos inputs
                    tf1c.setText(null);
                    tf1r.setText(null);

                    
                    // Solicita dados aos usuário
                    check = JOptionPane.showConfirmDialog(null, message, "Incluir 1 Porta Aviões (5 peças)", JOptionPane.OK_OPTION);
                    
                    // Verifica qual é o opção escolhida
                    if (check == 0){
                            
                    	int position = checkRadioButton(btHorizontal, btVertical);
                    	
                		if ((position == 1) || (position == 2)){
                    	
                            // Verifica se tem Zeros, Letras e "". SE NÃO TEM insere valores na tabela
                            // SE TEM exibe mensagem de erro e solicita novamente ao usuário
                            if (!hasZeros(tf1r.getText(), tf1c.getText()) && 
                                !hasLetters(tf1r.getText(), tf1c.getText()) && 
                                !hasNull(tf1r.getText(), tf1c.getText()) && 
                                isCoordenatesOk(tf1r.getText(), tf1c.getText(), 5, position) && 
                                isPositionOk(table, tf1r.getText(), tf1c.getText(), 5, position)){
                                    
                                    
                            	for (int i = 0; i < 5; i++) {
                    				if (position == 1){
                    					table.setValueAt("P", Integer.parseInt(tf1r.getText()), Integer.parseInt(tf1c.getText()) + i);
                    				}else {
                    					table.setValueAt("P", Integer.parseInt(tf1r.getText()) + i, Integer.parseInt(tf1c.getText()));
                    				}
                        		}
                                    
                            }else {
                                    
                                    // Mensagem de erro
                                    error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
                                    check = 1;
                            }
                        }else {
                    			// Mensagem de erro
                                error = JOptionPane.showConfirmDialog(null, errorMessage, "Erro ao inserir Coordenadas", JOptionPane.CANCEL_OPTION);
                                check = 1;
                		}
                    }
            }
            
    }
    
    public int checkRadioButton(JRadioButton radio1, JRadioButton radio2){
    	
    	int check = 1;
    	
    	if (radio1.isSelected()){
    		check = 1;
    	}
    	
    	if (radio2.isSelected()){
    		check = 2;
    	}
    	
    	return check;
    }
	
}
