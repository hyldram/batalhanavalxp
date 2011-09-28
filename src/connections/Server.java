package src.connections;

import java.io.*;
import java.net.*;
import src.main.*;
import javax.swing.*;

public class Server extends Thread implements Runnable{
	
	protected String ip;
	protected String port;
	protected ServerSocket socketServer;
	protected Socket socketConnection;
	public ObjectInputStream receiveObject;
	public ObjectOutputStream sendObject;
	protected Shot shot = new Shot();
	
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
	
	public Shot getShot() {
		return shot;
	}

	public void run(){
		try {
			receiveObject = new ObjectInputStream(socketConnection.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
			if (socketConnection.isConnected()){
				try {
					shot = (Shot) receiveObject.readObject();
					System.out.println(shot.getColumn() + "--Server--" + shot.getRow());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	// Método que checa se a PORT passada é válida ou não.
	public boolean checkAddress(String port){
	//public boolean checkAddress(String ip, String port){	
		boolean statusAddress = false;
		
		InetSocketAddress socketAddress = new InetSocketAddress(Integer.parseInt(port));
		//InetSocketAddress socketAddress = new InetSocketAddress(ip, Integer.parseInt(port));
		if (socketAddress.isUnresolved() == false){
			statusAddress = true;
		}
		
		return statusAddress;
	}

	public void receiveShot(Shot shot, JTable tableEnemy, JTable tableScore){
		
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
}
