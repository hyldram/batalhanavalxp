package src.main;

import src.windows.*;

public class Main {

	public static void main(String args[]){
		
		// Para aumentar o tempo do Splash Screen
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new BeginWindow();
	}
}
