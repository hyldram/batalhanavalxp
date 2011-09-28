package src.connections;

import java.io.*;
import java.net.*;
import src.main.*;

import javax.swing.*;

public class Client extends Thread implements Runnable{

	protected String ip;
	protected String port;
	protected Socket socketClient;
	public ObjectInputStream receiveObject;
	public ObjectOutputStream sendObject;
	protected Shot shot = new Shot();
	
	public Client (String ip, String port){
		
		try {
			socketClient = new Socket(ip, Integer.parseInt(port));
			sendObject = new ObjectOutputStream(socketClient.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Shot getShot() {
		return shot;
	}

	public void run(){
		try {
			receiveObject = new ObjectInputStream(socketClient.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
			if(socketClient.isConnected()){
				try {
					shot = (Shot) receiveObject.readObject();
					System.out.println(shot.getColumn() + "--Client--" + shot.getRow());
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
	
	public void receiveShot(Shot shot, JTable tableEnemy, JTable tableScore){
		
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
	
}
