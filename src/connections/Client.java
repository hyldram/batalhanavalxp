package src.connections;

import java.io.*;
import java.net.*;
import src.main.*;
import javax.swing.*;

public class Client extends Thread implements Runnable{

	protected String ip; // guarda ip
	protected String port;	// guarda porta
	protected Socket socketClient;	// cria socket cliente
	public ObjectInputStream receiveObject;	// objeto que recebe objeto
	public ObjectOutputStream sendObject;	// objeto que envia objeto
	protected Shot shot;	// objeto tiro
	protected Shot shotAnswer;	// objeto tiro resposta
	protected boolean hitAnswer;	// tag se é ou não tiro resposta
	protected int countShot;	// contador dos tiros enviados
	protected int countHit;	// contador dos tiros que acertaram
	protected int countPoints = 15;	// contador dos pontos totais
	protected JTable clientTable;	// guarda referencia da tabela que contem os barcos do cliente
	protected JTable clientTableAnswer;	//  guarda referencia da tabela que contem os tiros enviados pelo cliente
	protected JTable clientScore;	//  guarda referencia da tabela que contem a pontuação do client
	protected JButton clientButton;	//  guarda referencia do botão iniciar disparo do cliente
	protected Object[] errorMessage;	// objeto que guarda mensagem de erro
	
	// Pelo Método construtor, conecta-se com o servidor e cria-se o socket
	public Client (String ip, String port){
		
		try {
			socketClient = new Socket(ip, Integer.parseInt(port));
			sendObject = new ObjectOutputStream(socketClient.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Threads que recebe Objeto e envia Objeto
	// Como ela funciona:
	// 1 - Recebe o Objeto e verifica se ele é um Tiro Resposta ou um Tiro. Se não for um tiro resposta: pega as Referências o Tabuleiro do Cliente;
	// verifica quantos tiros já foram dados; verifica se acertou ou não um barco e marca no tabuleiro do Cliente; faz a contagem de Tiros, Acertos 
	// e quantos resta para acabar o jogo; acerta a pontuação do Cliente; monta o Tiro Resposta para o Servidor, para que ele possa marcar em sua 
	// Grade de Resposta assim como acertar a sua pontuação; envia o Tiro Resposta para o Servidor; e liberar o Botão para Iniciar Disparo do Cliente.
	// ---------------------------------------------------------------------------------------------------------------------------------------------
	// 2 - Se o Tiro for um Tiro Resposta, então: verifica se o tiro acertou ou não para determinar qual marcação usar; atualizar a Grade de
	// Pontuação
	public void run(){
		if(socketClient.isConnected()){
			try {
				receiveObject = new ObjectInputStream(socketClient.getInputStream());
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
						configureServerBoard(getShot());
						
						// Acerta a contagem de Tiros
						setCountShot(getCountShot() + 1);
						
						// Verifica se o Tiro acerta ou não um barco
						setHitAnswer(getShot().receiveShot(getShot().getRow(), getShot().getColumn(), getShot().getBoard()));
						
						// Se Acertou incrementa a contagem
						if (isHitAnswer()){
							setCountHit(getCountHit() + 1);
							setCountPoints(getCountPoints() - 1);
						}
						
						
						// Acerta a pontuação do Cliente
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
						
						int wipe = 0;
						
						if (getCountPoints() == 0){
							
							interrupt();
							disconnect();
							
							// Cria Mensagem de Derrota
		        			errorMessage = new Object[] {"Você PERDEU! Clique em Ok para finalizar o jogo. PERDEU!\n\n Há!"};
							wipe = JOptionPane.showConfirmDialog(null, errorMessage, "VOCÊ PERDEU!", JOptionPane.CANCEL_OPTION);
						}else{
						
							// Libera Botão para o Cliente poder Iniciar o Diparo
							getShot().getBoard().getEnemyButton().setEnabled(true);
						}
					}else {
						
						// Verifica se o Tiro Resposta acertou ou não. E marca no tabuleiro com determinada Marca 
						if (getShot().isHit()){
							getClientTableAnswer().setValueAt("X", Integer.parseInt(getShot().getRow()), Integer.parseInt(getShot().getColumn())); // Acertou = X
						}else {
							getClientTableAnswer().setValueAt("O", Integer.parseInt(getShot().getRow()), Integer.parseInt(getShot().getColumn())); // Errou = O
						}
						
						// Atualiza Pontuação do Cliente
						getClientScore().setValueAt(getShot().getCountShot(), 1, 1);
						getClientScore().setValueAt(getShot().getCountHit(), 1, 2);
						getClientScore().setValueAt(getShot().getCountPoints(), 1, 3);
						
						int wipe;
						
						if (getShot().getCountPoints() == 0){
							
							interrupt();
							disconnect();
							
							// Cria Mensagem de Derrota
		        			errorMessage = new Object[] {"Você GANHOU! Clique em OK para finalizar o jogo. GANHOU!\n\n"};
							wipe = JOptionPane.showConfirmDialog(null, errorMessage, "VOCÊ GANHOU!", JOptionPane.CANCEL_OPTION);
						}
						
					}
				}
			}
	}
	
	// Alimenta o Board do Servidor com as referencias do Cliente
	public void configureServerBoard(Shot shot){
		
		shot.getBoard().setEnemyScore(getClientScore());
		shot.getBoard().setEnemyTable(getClientTable());
		shot.getBoard().setEnemyButton(getClientButton());
	}
	
	// Método para disconectar do Socket
	public void disconnect(){
		
		try {
			socketClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public JTable getClientTable() {
		return clientTable;
	}

	public void setClientTable(JTable clientTable) {
		this.clientTable = clientTable;
	}

	public JTable getClientScore() {
		return clientScore;
	}

	public void setClientScore(JTable clientScore) {
		this.clientScore = clientScore;
	}

	public JButton getClientButton() {
		return clientButton;
	}

	public void setClientButton(JButton clientButton) {
		this.clientButton = clientButton;
	}

	public JTable getClientTableAnswer() {
		return clientTableAnswer;
	}

	public void setClientTableAnswer(JTable clientTableAnswer) {
		this.clientTableAnswer = clientTableAnswer;
	}
	
	public Shot getShot() {
		return shot;
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

	public Shot getShotAnswer() {
		return shotAnswer;
	}

	public void setShotAnswer(Shot shotAnswer) {
		this.shotAnswer = shotAnswer;
	}
	
}
