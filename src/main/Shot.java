package src.main;

import java.io.*;

public class Shot implements Serializable{
	
	private static final long serialVersionUID = 42;
	protected String row;
	protected String column;
	protected boolean releaseButton;
	
	public void createShot(String row, String column){
		setColumn(column);
		setRow(row);
		setReleaseButton(true);
	}
	
	public void deleteShot(){
		setColumn(null);
		setRow(null);
		setReleaseButton(false);
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
	
	public boolean isReleaseButton() {
		return releaseButton;
	}
	
	public void setReleaseButton(boolean releaseButton) {
		this.releaseButton = releaseButton;
	}
	
	
}
