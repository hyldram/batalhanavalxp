package src.main;

import java.io.*;
import javax.swing.*;

public class Board implements Serializable{

	private static final long serialVersionUID = 42;
	
	public JTable enemyScore;
	public JTable enemyTable;
	public JButton enemyButton;
	
	public JTable getEnemyScore() {
		return enemyScore;
	}

	public void setEnemyScore(JTable enemyScore) {
		this.enemyScore = enemyScore;
	}

	public JTable getEnemyTable() {
		return enemyTable;
	}

	public void setEnemyTable(JTable enemyTable) {
		this.enemyTable = enemyTable;
	}

	public JButton getEnemyButton() {
		return enemyButton;
	}

	public void setEnemyButton(JButton enemyButton) {
		this.enemyButton = enemyButton;
	}
}
