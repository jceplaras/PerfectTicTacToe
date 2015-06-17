package com.artdotcode.perfecttictactoe;

import java.util.ArrayList;


public class PerfectPlayer {
	int player;
	
	int opponent;
	
	Move choice;
	
	public PerfectPlayer(int player) {
		this.player=player;
		this.opponent = (player==1)?2:1;
	}
	
	public int score(Game game) {
		if(game.isWin(player))
			return 10;
		else if(game.isWin(opponent))
			return -10;
		else return 0;
		
	}

    public int alphaBeta(Game game, int alpha, int beta) {
        //game.printGame();
        if(game.isGameOver()) return score(game);
        int score;
        Move bestChoice = null;
        if(game.getActiveTurn() == this.player) {

            for(Move move: game.getAvailableMoves()) {
                score = alphaBeta(game.getNewGameState(move), alpha, beta);
                if(score > alpha) {
                    alpha = score;
                    bestChoice = move;
                }
                if(alpha >= beta) {
                    //choice = move;
                    choice = bestChoice;
                    return alpha;
                }
            }
            //System.out.println();
            choice = bestChoice;
            return alpha;
        }
        else {

            for(Move move: game.getAvailableMoves()) {
                score =  alphaBeta(game.getNewGameState(move), alpha, beta);
                if(score < beta) {
                    beta = score;
                    bestChoice = move;
                }
                if(alpha >= beta) {
                    //choice = move;
                    choice = bestChoice;
                    return beta;
                }
            }
            choice = bestChoice;
            return beta;
        }


    }

	public int minimax(Game game) {
		//game.printGame();
		if(game.isGameOver()) return score(game);
		
		ArrayList<Move> moveList = new ArrayList<Move>();
		
		for(Move move: game.getAvailableMoves()) {
			Game possibleGame = game.getNewGameState(move);
			move.setScore(minimax(possibleGame));
			moveList.add(move);
			//possibleGame.printGame();
		}
		
		
		
		if(game.getActiveTurn() == player){
			int maxScore = Integer.MIN_VALUE;
			Move maxMove = null;
			for(Move move: moveList) {
				if(move.getScore() > maxScore) {
					maxMove = move;
					maxScore = move.getScore();
				}
			}
			choice = maxMove;
			return maxScore;
		}
		else {
			int minScore = Integer.MAX_VALUE;
			Move minMove = null;
			for(Move move: moveList) {
				if(move.getScore() < minScore) {
					minMove = move;
					minScore = move.getScore();
				}
			}
			choice = minMove;
			return minScore;
		}
		
	}
	
	public Move getChoice() {
		return choice;
	}
}
