package connections;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public String ip;
	public String port;
	
	// Se conecta ao Servidor com o IP e Porta passados por parâmetros
	public void connectServer(String ip, String port) throws NumberFormatException, UnknownHostException, IOException{
		Socket socketClient = new Socket(ip, Integer.parseInt(port));
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
