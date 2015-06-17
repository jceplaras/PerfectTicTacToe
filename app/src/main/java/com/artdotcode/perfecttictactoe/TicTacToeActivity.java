package com.artdotcode.perfecttictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TicTacToeActivity extends ActionBarActivity implements View.OnClickListener {

    LinearLayout tile[][] = new LinearLayout[3][3];
    TextView tile_text[][] = new TextView[3][3];

    Game game;
    PerfectPlayer perfectPlayer;

    Button playerStart;
    Button aiStart;

    Button newGame;
    Button about;
    int playerIndicator;
    int board[][] = new int[3][3];


    LinearLayout layout_select_player;
    LinearLayout layout_new_game;

    boolean gameStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        for(int r=0;r<3;r++) {
            for(int c=0;c<3;c++){
                tile[r][c] = (LinearLayout) findViewById(getResources().getIdentifier("tile_r"+r+"_c"+c,"id",getPackageName()));
                tile[r][c].setOnClickListener(this);
                tile_text[r][c] = (TextView) findViewById(getResources().getIdentifier("text_tile_r"+r+"_c"+c,"id",getPackageName()));
            }
        }

        playerStart = (Button) findViewById(R.id.button_player_start);
        playerStart.setOnClickListener(this);
        aiStart = (Button) findViewById(R.id.button_ai_start);
        aiStart.setOnClickListener(this);
        newGame = (Button) findViewById(R.id.button_new_game);
        newGame.setOnClickListener(this);
        about = (Button) findViewById(R.id.button_about);
        about.setOnClickListener(this);
        layout_new_game = (LinearLayout) findViewById(R.id.layout_new_game);
        layout_select_player = (LinearLayout) findViewById(R.id.layout_select_player);


        layout_select_player.setVisibility(View.GONE);
    }



    @Override
    public void onClick(View v) {


        if(v.getId() == newGame.getId()) {

            layout_new_game.setVisibility(View.GONE);
            layout_select_player.setVisibility(View.VISIBLE);
            clearBoard();
            deactivateBoard();



            gameStart = false;
            board = new int[3][3];
            game = new Game(Game.X_PLAYER, board);
            perfectPlayer = new PerfectPlayer(Game.X_PLAYER);


        }
        if(v.getId() == about.getId()) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        if(v.getId() == playerStart.getId()) {

            gameStart = true;
            playerIndicator = Game.X_PLAYER;
            activateBoard();
            layout_new_game.setVisibility(View.VISIBLE);
            layout_select_player.setVisibility(View.GONE);
            return;
        }

        if(v.getId() == aiStart.getId()) {
            gameStart = true;
            playerIndicator = Game.O_PLAYER;
            activateBoard();
            layout_new_game.setVisibility(View.VISIBLE);
            layout_select_player.setVisibility(View.GONE);

            perfectPlayer.alphaBeta(game,Integer.MIN_VALUE,Integer.MAX_VALUE);
            Move m = perfectPlayer.getChoice();
            game = game.getNewGameState(m);
            showMoveOnBoard(m);


            return;
        }


        if(gameStart) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (tile[r][c].getId() == v.getId() && tile_text[r][c].getText().equals("")) {
                        Move m = new Move(r, c, playerIndicator);
                        game = game.getNewGameState(m);
                        showMoveOnBoard(m);

                        if(game.isGameOver()) {
                            gameStart = false;
                            if(game.isWin(game.X_PLAYER))
                                colorWin(game.X_PLAYER);
                            else if(game.isWin(game.O_PLAYER))
                                colorWin(game.O_PLAYER);
                            else
                                colorDraw();
                            return;
                        }

                        perfectPlayer.alphaBeta(game, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        m = perfectPlayer.getChoice();
                        game = game.getNewGameState(m);
                        showMoveOnBoard(m);

                        if(game.isGameOver()) {
                            gameStart = false;
                            if(game.isWin(game.X_PLAYER))
                                colorWin(game.X_PLAYER);
                            else if(game.isWin(game.O_PLAYER))
                                colorWin(game.O_PLAYER);
                            else
                                colorDraw();
                                return;
                        }

                        return;
                    }
                }
            }
        }
    }

    public void colorDraw() {
        for(int r=0;r<3;r++)
            for(int c=0;c<3;c++)
                tile[r][c].setBackgroundResource(R.drawable.shape_board_draw);
    }

    public void colorWin(int player) {
        int color = getResources().getColor(R.color.win);
        int board[][] = game.getBoard();
        for(int row=0;row<3;row++)
            if(board[row][0]==player&&board[row][1]==player&&board[row][2]==player) {
               tile_text[row][0].setTextColor(color);
                tile_text[row][1].setTextColor(color);
                tile_text[row][2].setTextColor(color);
                return;
            }
        for(int col=0;col<3;col++)
            if(board[0][col]==player&&board[1][col]==player&&board[2][col]==player) {
                tile_text[0][col].setTextColor(color);
                tile_text[1][col].setTextColor(color);
                tile_text[2][col].setTextColor(color);
                return;
            }
        if(board[0][0]==player&&board[1][1]==player&&board[2][2]==player) {
            tile_text[0][0].setTextColor(color);
            tile_text[1][1].setTextColor(color);
            tile_text[2][2].setTextColor(color);
            return;
        }
        if(board[2][0]==player&&board[1][1]==player&&board[0][2]==player) {
            tile_text[2][0].setTextColor(color);
            tile_text[1][1].setTextColor(color);
            tile_text[0][2].setTextColor(color);
            return;
        }
    }

    public void clearBoard() {
        for(int r=0;r<3;r++)
            for(int c=0;c<3;c++) {
                tile_text[r][c].setText("");
                tile_text[r][c].setTextColor(getResources().getColor(R.color.white));
            }
    }

    public void activateBoard() {
        for(int r=0;r<3;r++)
            for(int c=0;c<3;c++)
                tile[r][c].setBackgroundResource(R.drawable.shape_board_active);
    }

    public void deactivateBoard() {
        for(int r=0;r<3;r++)
            for(int c=0;c<3;c++)
                tile[r][c].setBackgroundResource(R.drawable.shape_board_idle);
    }

    public void showMoveOnBoard(Move move) {
        if(move.getActivePlayer() == Game.X_PLAYER)
            tile_text[move.getRow()][move.getColumn()].setText("X");
        else if(move.getActivePlayer() == Game.O_PLAYER)
            tile_text[move.getRow()][move.getColumn()].setText("O");
    }

}
