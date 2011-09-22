package src.connections;

import java.io.*;
import java.net.*;

public class Server{
	
	public String ip;
	public String port;
	
	public void startServer(String port) throws IOException{
		
		// Tag responsável por guardar o Status do Endereço
		boolean addressStatus = false;
		
		// Executa método para checar o IP e PORTA passados
		addressStatus = checkAddress(port);
		//addressStatus = checkAddress(ip, port);
		
		// Se o IP e PORTA existem Cria Socket. Senão exibe mensagem de erro
		if (addressStatus == true){

			// Cria Socket
			ServerSocket socketServer = new ServerSocket(Integer.parseInt(port));
			//ServerSocket socketServer = new ServerSocket();
			
			//InetSocketAddress serverAddress = new InetSocketAddress(ip, Integer.parseInt(port));
			
			//socketServer.bind(serverAddress);
			
			// Server apenas para guardar a porta na Classe
			setPort(port);
			
			// Aceita conexões
			Socket socketConnection = socketServer.accept();
			

		}else{
			System.out.println("ERRO");
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
