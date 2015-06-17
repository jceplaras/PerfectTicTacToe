package com.artdotcode.perfecttictactoe;

public class Move {
	int row;
	int column;
	int activePlayer;
	
	int score = 0;
	
	public Move(int row, int column, int activePlayer) {
		super();
		this.row = row;
		this.column = column;
		this.activePlayer = activePlayer;
	}
	
	
	
	
	public int getScore() {
		return score;
	}




	public void setScore(int score) {
		this.score = score;
	}




	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getActivePlayer() {
		return activePlayer;
	}
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	
}
