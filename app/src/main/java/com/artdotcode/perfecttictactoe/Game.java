package com.artdotcode.perfecttictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Game {
	private int activeTurn;
	private int board[][] = new int[3][3];
	
	public static final int X_PLAYER = 1;
	public static final int O_PLAYER = 2;
	public Game(int activeTurn, int[][] board) {
		super();
		this.activeTurn = activeTurn;
		this.board = board;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public void setActiveTurn(int activeTurn) {
		this.activeTurn = activeTurn;
	}

	public ArrayList<Move> getAvailableMoves() {
		ArrayList<Move> moveList = new ArrayList<Move>();
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				if(board[i][j] == 0) {
					Move move = new Move(i,j,activeTurn);
					moveList.add(move);
				}
			}
        Collections.shuffle(moveList);
		return moveList;
	}
	
	public Game getNewGameState(Move move) {
		int newBoard[][] = new int[3][3];
		
		int newActivePlayer = (move.getActivePlayer()==X_PLAYER)?O_PLAYER:X_PLAYER;
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++){
				newBoard[i][j] = this.board[i][j];
			}
		
		newBoard[move.getRow()][move.getColumn()] = move.getActivePlayer();
		
		Game game = new Game(newActivePlayer,newBoard);
		
		return game;
	}
	
	public boolean isWin(int player) {
		for(int row=0;row<3;row++)
			if(board[row][0]==player&&board[row][1]==player&&board[row][2]==player) {
				//System.out.println("WIN ON ROW "+row);
				return true;
			}
		for(int col=0;col<3;col++)
			if(board[0][col]==player&&board[1][col]==player&&board[2][col]==player) {
				//System.out.println("WIN ON COL "+col);
				return true;
			}
		if(board[0][0]==player&&board[1][1]==player&&board[2][2]==player) return true;
		if(board[2][0]==player&&board[1][1]==player&&board[0][2]==player) return true;
		return false;
	}
	
	public boolean isGameOver() {
		if(isWin(X_PLAYER)) { return true;}
		if(isWin(O_PLAYER)) { return true;}
		for(int r=0;r<3;r++){
			for(int c=0;c<3;c++)
				if(board[r][c] == 0) return false;
		}
		return true;
	}
	
	public int getActiveTurn() {
		return activeTurn;
	}
	
	public void printGame() {
		for(int i=0;i<3;i++)
			System.out.println(Arrays.toString(this.getBoard()[i]));
		System.out.println("Next Active Player"+this.getActiveTurn());
	}
}
