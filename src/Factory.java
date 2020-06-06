
//-----------------------------------------
// NAME		: AL Fahiyan Siyam
// STUDENT NUMBER	: 7852355
// COURSE		: COMP 2150
// INSTRUCTOR	: Mike Domaratzki
// ASSIGNMENT	: Take Home
// QUESTION	: question 2
//
// REMARKS: Create a factory design for the connect 4 game
//
//-----------------------------------------



public class Factory {

    public static Player factoryPattern(String playerName, GameLogic myGameLogic, int boardSize) {

        Player firstPlayer = null;

        if (playerName.equalsIgnoreCase("AI")) {
            firstPlayer = new AIPlayer();

            firstPlayer.setInfo(boardSize, myGameLogic);
            firstPlayer.lastMove(-1);
            firstPlayer.gameOver(Status.NEITHER);
        } else if(playerName.equalsIgnoreCase("HUMAN")) {
            firstPlayer = new HumanPlayer();

            firstPlayer.setInfo(boardSize, myGameLogic);
            firstPlayer.lastMove(-1);
            firstPlayer.gameOver(Status.NEITHER);
        }


        return firstPlayer;
    }

    public static void main(String[] args) {
        GameLogic gl=new MyGameLogic();
        Factory.factoryPattern("AI",gl,((MyGameLogic) gl).getSize());



    }
}
