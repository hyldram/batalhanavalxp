package windows;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import connections.Server;

public class ServerWindow  extends JFrame{
	
	public ServerWindow() throws UnknownHostException{
		
		super("Iniciando um Servidor");
		
		// Cria um Container
		Container swFrame = this.getContentPane();
		swFrame.setLayout(null);
		
		// Cria Label sobre Campo IP do Servidor
		final JLabel lbServerIp = new JLabel("Digite o seu IP:");
		lbServerIp.setBounds(10, 10, 100, 50);
		
		InetAddress serverIp = InetAddress.getLocalHost();
		
		// Cria TextField sobre Campo IP do Servidor. Que vai receber informações
		// do usuário
		final JTextField tfServerIp = new JTextField(serverIp.getHostAddress());
		tfServerIp.setBounds(105, 10, 125, 50);
		tfServerIp.setEnabled(false);
		
		// Cria Label sobre Campo Porta do Servidor
		final JLabel lbServerPort = new JLabel("Digite a sua Porta:");
		lbServerPort.setBounds(10, 60, 120, 50);
		
		// Cria TextField sobre Campo Porta do Servidor. Que vai receber informações
		// do usuário
		final JTextField tfServerPort = new JTextField("");
		tfServerPort.setBounds(125, 60, 105, 50);
		
		// Cria Botão responsável por retornar a Tela Inicial
		JButton btReturn = new JButton("Cancelar");
		btReturn.setBounds(10, 115, 80, 40);
		btReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	setVisible(false);
                new BeginWindow();
            }
        });
		
		// Cria Botão responsável por enviar dados para Startar o Servidor
		JButton btStartServer = new JButton("Iniciar");
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
		// 2. Setar Frame como Visível
		// 3. Setar as dimensões do Frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 1
		this.setVisible(true); // 2
		this.setSize(240, 180); // 3
	}

}
