
//-----------------------------------------
// NAME		: AL Fahiyan Siyam
// STUDENT NUMBER	: 7852355
// COURSE		: COMP 2150
// INSTRUCTOR	: Mike Domaratzki
// ASSIGNMENT	: assignment 3
// QUESTION	: question 1
//
// REMARKS: This class is responsible to maintain rules for the connect4 Games.
//          Maintain the player turn
//
//-----------------------------------------


public class HumanPlayer implements Human, Player {
    private GameLogic humanGame;
    private int boardSize;
    private UI gui;

    //constructor
    //Storing Ui instance to communicate
    public HumanPlayer()
    {
        gui=new SwingGUI();
    }

    //sending human's last move to game Logic
    public void setAnswer(int col) {
        humanGame.setAnswer(col);

    }


    //Sending information about winner
    @Override
    public void gameOver(Status winner) {
     gui.gameOver(winner);
    }


    //sending Ai's last move to UI
    @Override
    public void lastMove(int lastCol) {
        gui.lastMove(lastCol);



    }

    //String game logic information
    @Override
    public void setInfo(int size, GameLogic gl) {

        boardSize = size;
        humanGame = gl;
        gui.setInfo(this, boardSize);
    }


}
