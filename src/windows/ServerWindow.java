package src.windows;

import javax.swing.*;

import src.connections.Server;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class ServerWindow  extends JFrame{

	private static final long serialVersionUID = 42;
	public Container swFrame;
	public JLabel lbServerIp;
	public JTextField tfServerIp;
	public JLabel lbServerPort;
	public JTextField tfServerPort;
	public JButton btReturn;
	public JButton btStartServer;
	
	public ServerWindow() throws UnknownHostException{
		
		// Determina nome Janela
		super("Iniciando um Servidor");
		
		// Cria um Container
		swFrame = this.getContentPane();
		swFrame.setLayout(null);
		
		// Cria Label sobre Campo IP do Servidor
		lbServerIp = new JLabel("Digite o seu IP:");
		lbServerIp.setBounds(10, 10, 100, 50);
		
		InetAddress serverIp = InetAddress.getLocalHost();
		
		// Cria TextField sobre Campo IP do Servidor. Que vai receber informações
		// do usuário
		tfServerIp = new JTextField(serverIp.getHostAddress());
		tfServerIp.setBounds(105, 10, 125, 50);
		tfServerIp.setEnabled(false);
		
		// Cria Label sobre Campo Porta do Servidor
		lbServerPort = new JLabel("Digite a sua Porta:");
		lbServerPort.setBounds(10, 60, 120, 50);
		
		// Cria TextField sobre Campo Porta do Servidor. Que vai receber informações
		// do usuário
		tfServerPort = new JTextField("");
		tfServerPort.setBounds(125, 60, 105, 50);
		
		// Cria Botão responsável por retornar a Tela Inicial
		btReturn = new JButton("Cancelar");
		btReturn.setBounds(10, 115, 80, 40);
		btReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	setVisible(false);
                new BeginWindow();
            }
        });
		
		// Cria Botão responsável por enviar dados para Startar o Servidor
		btStartServer = new JButton("Iniciar");
		btStartServer.setBounds(150, 115, 80, 40);
		btStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Server server = new Server();
				try {
					server.startServer(tfServerPort.getText());
					//server.startServer(tfServerIp.getText(), tfServerPort.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				new BoardWindow("Server");
			}
		});
		
		// Adiciona Botões, Labels e Textfields ao Container
		swFrame.add(lbServerIp);
		swFrame.add(tfServerIp);
		swFrame.add(lbServerPort);
		swFrame.add(tfServerPort);
		swFrame.add(btStartServer);
		swFrame.add(btReturn);
				
		// Configura detalhes do Frame
		// 1. Encerrar Applicação ao Fechar
		// 2. Setar as dimensões do Frame
		// 3. Setar Frame como Visível
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 1
		this.setSize(240, 180); // 2
		this.setVisible(true); // 3
		
	}

}
