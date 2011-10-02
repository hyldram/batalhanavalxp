package src.main;

import java.io.*;
import javax.swing.*;

public class Board implements Serializable{

	private static final long serialVersionUID = 42;
	
	public JTable enemyScore;
	public JTable enemyTable;
	public JButton enemyButton;
	
	// Pega referencia da Tabela Score do Inimigo
	public JTable getEnemyScore() {
		return enemyScore;
	}

	public void setEnemyScore(JTable enemyScore) {
		this.enemyScore = enemyScore;
	}
	
	// Pega referencia da Tabuleiro do Inimigo
	public JTable getEnemyTable() {
		return enemyTable;
	}

	public void setEnemyTable(JTable enemyTable) {
		this.enemyTable = enemyTable;
	}

	// Pega referencia do Botão Iniciar Disparo do Inimigo
	public JButton getEnemyButton() {
		return enemyButton;
	}

	public void setEnemyButton(JButton enemyButton) {
		this.enemyButton = enemyButton;
	}
}
