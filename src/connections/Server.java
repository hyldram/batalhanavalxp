package src.connections;

import java.io.*;
import java.net.*;
import src.main.*;
import javax.swing.*;

public class Server extends Thread implements Runnable{
	
	protected String ip;	// guarda ip
	protected String port;	// guarda porta
	public ServerSocket socketServer;	// objeto que criar o server do socket
	public Socket socketConnection;	// objeto que cria o socket
	public ObjectInputStream receiveObject;	// objeto que recebe objeto
	public ObjectOutputStream sendObject;	// objeto que envia objeto
	protected Shot shot = new Shot();	// objeto tiro
	protected Shot shotAnswer;	// objeto tiro resposta
	protected boolean hitAnswer;	// tag que guarda se é tiro resposta ou não
	protected int countShot;	// contador de tiro dados
	protected int countHit;	// contador de tiros que acertaram
	protected int countPoints = 15;	// contador dos tiros totais
	protected JTable serverTable; // guarda referencia da tabela que contem os barcos do server
	protected JTable serverTableAnswer;	//  guarda referencia da tabela que contem os tiros enviados pelo server
	protected JTable serverScore;	//  guarda referencia da tabela que contem a pontuação do server
	protected JButton serverButton;	//  guarda referencia do botão iniciar disparo do server 
	protected Object[] errorMessage; // objeto que guarda mensagem de erro
	
	// Método Construtor que cria Server Socket
	public Server(String port){
		try {
			socketServer = new ServerSocket(Integer.parseInt(port));
			socketConnection = socketServer.accept();
			sendObject = new ObjectOutputStream(socketConnection.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPort(port);
	}

	// Threads que recebe Objeto e envia Objeto
	// Como ela funciona:
	// 1 - Recebe o Objeto e verifica se ele é um Tiro Resposta ou um Tiro. Se não for um tiro resposta: pega as Referências o Tabuleiro do Servidor;
	// verifica quantos tiros já foram dados; verifica se acertou ou não um barco e marca no tabuleiro do Servidor; faz a contagem de Tiros, Acertos 
	// e quantos resta para acabar o jogo; acerta a pontuação do Servidor; monta o Tiro Resposta para o Cliente, para que ele possa marcar em sua 
	// Grade de Resposta assim como acertar a sua pontuação; envia o Tiro Resposta para o Cliente; e liberar o Botão para Iniciar Disparo do Servidor.
	// ------------------------------------------------------------------------------------------------------------------------------------------------
	// 2 - Se o Tiro for um Tiro Resposta, então: verifica se o tiro acertou ou não para determinar qual marcação usar; atualizar a Grade de
	// Pontuação
	public void run(){
		if (socketConnection.isConnected()){
			try {
				receiveObject = new ObjectInputStream(socketConnection.getInputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			while(true){
					try {
						// Recebe Objeto
						setShot((Shot) receiveObject.readObject());	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// Verifica se é Tiro Normal ou Tiro Resposta
					if (!getShot().isAnswer()){
						
						// Pega as Referências do Tabuleiro
						configureClientBoard(getShot());
						
						// Acerta a contagem de Tiros
						setCountShot(getCountShot() + 1);
						
						// Verifica se o Tiro acerta ou não um barco
						setHitAnswer(getShot().receiveShot(getShot().getRow(), getShot().getColumn(), shot.getBoard()));
						
						// Se Acertou incrementa a contagem
						if (isHitAnswer()){
							setCountHit(getCountHit() + 1);
							setCountPoints(getCountPoints() - 1);
						}
						
						// Acerta a pontuação do Servidor
						getShot().getBoard().getEnemyScore().setValueAt(getCountShot(), 2, 1);
						getShot().getBoard().getEnemyScore().setValueAt(getCountHit(), 2, 2);
						getShot().getBoard().getEnemyScore().setValueAt(getCountPoints(), 2, 3);
						
						// Cria o Tiro Resposta
						setShotAnswer(new Shot());
						getShotAnswer().createShotAnswer(getShot().getRow(), getShot().getColumn(), true, isHitAnswer(), getCountShot(), getCountHit(), getCountPoints());
						
						try {
							// Envia Tiro Resposta
							sendObject.writeObject(getShotAnswer());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						int wipe;
						
						if (getCountPoints() == 0){
							
							interrupt();
							disconnect();
							
							// Cria Mensagem de Derrota
		        			errorMessage = new Object[] {"Você PERDEU! Clique em Ok para voltar a Tela Inicial. Novamente, você PERDEU!\n\n Há!"};
							wipe = JOptionPane.showConfirmDialog(null, errorMessage, "VOCÊ PERDEU!", JOptionPane.CANCEL_OPTION);
						}else{
						
							// Libera Botão para o Servidor poder Iniciar o Diparo
							getShot().getBoard().getEnemyButton().setEnabled(true);
						}
					}else {
						
						// Verifica se o Tiro Resposta acertou ou não. E marca no tabuleiro com determinada Marca
						if (getShot().isHit()){
							getServerTableAnswer().setValueAt("X", Integer.parseInt(getShot().getRow()), Integer.parseInt(getShot().getColumn())); // Acertou = X
						}else {
							getServerTableAnswer().setValueAt("O", Integer.parseInt(getShot().getRow()), Integer.parseInt(getShot().getColumn())); // Errou = O
						}
						
						// Atualiza Pontuação do Servidor
						getServerScore().setValueAt(getShot().getCountShot(), 1, 1);
						getServerScore().setValueAt(getShot().getCountHit(), 1, 2);
						getServerScore().setValueAt(getShot().getCountPoints(), 1, 3);
						
						int wipe;
						
						if (getShot().getCountPoints() == 0){
							
							interrupt();
							disconnect();
							
							// Cria Mensagem de Derrota
		        			errorMessage = new Object[] {"Você GANHOU! Clique em Ok para voltar a Tela Inicial. FEITORIA!\n\n"};
							wipe = JOptionPane.showConfirmDialog(null, errorMessage, "FEITÔ! VOCÊ GANHOU!", JOptionPane.CANCEL_OPTION);
						}
					}
				}
			}
	}
	
	// Método que checa se a PORT passada é válida ou não.
	public boolean checkAddress(String port){
	
		boolean statusAddress = false;
		
		InetSocketAddress socketAddress = new InetSocketAddress(Integer.parseInt(port));
	
		if (socketAddress.isUnresolved() == false){
			statusAddress = true;
		}
		
		return statusAddress;
	}

	// Alimenta o Board do Cliente com as referencias do Servidor
	public void configureClientBoard(Shot shot){
		
		getShot().getBoard().setEnemyScore(getServerScore());
		getShot().getBoard().setEnemyTable(getServerTable());
		getShot().getBoard().setEnemyButton(getServerButton());
	}
	
	// Método para disconectar do Socket
	public void disconnect(){
		try {
			socketConnection.close();
			socketServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public JTable getServerTable() {
		return serverTable;
	}

	public void setServerTable(JTable serverTable) {
		this.serverTable = serverTable;
	}

	public JTable getServerScore() {
		return serverScore;
	}

	public void setServerScore(JTable serverScore) {
		this.serverScore = serverScore;
	}

	public JButton getServerButton() {
		return serverButton;
	}

	public void setServerButton(JButton serverButton) {
		this.serverButton = serverButton;
	}

	public JTable getServerTableAnswer() {
		return serverTableAnswer;
	}

	public void setServerTableAnswer(JTable serverTableAnswer) {
		this.serverTableAnswer = serverTableAnswer;
	}

	public Shot getShotAnswer() {
		return shotAnswer;
	}

	public void setShotAnswer(Shot shotAnswer) {
		this.shotAnswer = shotAnswer;
	}

	public boolean isHitAnswer() {
		return hitAnswer;
	}

	public void setHitAnswer(boolean hitAnswer) {
		this.hitAnswer = hitAnswer;
	}

	public int getCountShot() {
		return countShot;
	}

	public void setCountShot(int countShot) {
		this.countShot = countShot;
	}

	public int getCountHit() {
		return countHit;
	}

	public void setCountHit(int countHit) {
		this.countHit = countHit;
	}

	public int getCountPoints() {
		return countPoints;
	}

	public void setCountPoints(int countPoints) {
		this.countPoints = countPoints;
	}

	public void setShot(Shot shot) {
		this.shot = shot;
	}
	
	public Shot getShot() {
		return shot;
	}
}
