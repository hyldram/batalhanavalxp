package src.main;

import java.io.*;

public class Shot implements Serializable{
	
	private static final long serialVersionUID = 42;
	
	protected boolean answer;
	protected boolean hit;
	protected int countShot;
	protected int countHit;
	protected int countPoints;
	protected String row;
	protected String column;
	protected Board board;
	
	// Método que cria Tiro
	public void createShot(String row, String column, Board board){
		setColumn(column);
		setRow(row);
		setBoard(board);
		setAnswer(false);
	}

	// Método que cria Tiro Resposta
	public void createShotAnswer(String row, String column, boolean answer, boolean hit, int countShot, int countHit, int countPoints){
		setColumn(column);
		setRow(row);
		setAnswer(answer);
		setHit(hit);
		setCountShot(countShot);
		setCountHit(countHit);
		setCountPoints(countPoints);
	}
	
	// Recebe o Tiro e faz as verificações se acertou o não
	public boolean receiveShot(String row, String column, Board board){
		
		if (board.getEnemyTable().getValueAt(Integer.parseInt(row), Integer.parseInt(column)) != null){
			board.getEnemyTable().setValueAt("X",Integer.parseInt(row), Integer.parseInt(column)); // Acertou = X
			setCountHit(countHit + 1);
			return true;
		}else {
			board.getEnemyTable().setValueAt("O",Integer.parseInt(row), Integer.parseInt(column)); // Errou = O
			return false;
		}
	}
	
	public String getRow() {
		return row;
	}
	
	public void setRow(String row) {
		this.row = row;
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}

	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
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
	
}
