
//-----------------------------------------
// NAME		: AL Fahiyan Siyam
// STUDENT NUMBER	: 7852355
// COURSE		: COMP 2150
// INSTRUCTOR	: Mike Domaratzki
// ASSIGNMENT	: assignment 3
// QUESTION	: question 1
//
// REMARKS: This class is responsible to maintain rules for the connect4 Games.
//          Maintain the player turn, check possible causes to announce the winner
//
//-----------------------------------------


public class MyGameLogic implements GameLogic {

    private HumanPlayer human;
    private AIPlayer aiPlayer;
    private Status[][] myStatus;
    private int boardSize;
    private Status playersTurn;

    //Constructor
    public MyGameLogic() {
        this.human = new HumanPlayer();
        this.aiPlayer = new AIPlayer();
        boardSize = randomBoardSize();

        myStatus = new Status[boardSize][boardSize];
        initializeMyStatus();
        human.setInfo(boardSize, this);
        aiPlayer.setInfo(boardSize, this);

        playersTurn = setFirstTurn();
        this.setAnswer(-1);
    }

    //Setting first turn randomly
    private Status setFirstTurn() {
        Status status = null;
        if (isBoardFull()) {
            status = Status.NEITHER;
        } else {
            if (randomPlayerSelect() == 0) {
                status = Status.ONE;
            } else {
                status = Status.TWO;
            }
        }
        return status;
    }

    //When game set who is winner
    private Status setWinner() {
        Status status = null;
        if (playersTurn == Status.ONE) {
            status = Status.TWO;
        } else if (playersTurn == Status.TWO) {
            status = Status.ONE;
        }
        return status;
    }

    //storing inputs of AI and Human
    //Checking who is winner
    //checking empty board
    //Setting up who's turn next
    @Override
    public void setAnswer(int col) {


        if (gameOver()) {

            human.gameOver(setWinner());
        } else {
            if (verifyCol(col)) {
                int position = drop(col);
                if (playersTurn == Status.ONE) {
                    myStatus[position][col] = Status.ONE;

                    // printBoard();
                    playersTurn = Status.TWO;
                    aiPlayer.lastMove(col); ///Fix to AI turn


                } else if (playersTurn == Status.TWO) {
                    myStatus[position][col] = Status.TWO;
                    playersTurn = Status.ONE;

                    human.lastMove(col);
                }
            } else {
                //   printBoard();
                if (playersTurn == Status.ONE) {
                    human.lastMove(col);
                } else if (playersTurn == Status.TWO) {
                    aiPlayer.lastMove(col);
                }
            }
        }
    }

    //Initializing board for Game Logic
    private void initializeMyStatus() {
        for (int i = 0; i < myStatus.length; i++) {
            for (int j = 0; j < myStatus[i].length; j++) {
                myStatus[i][j] = Status.NEITHER;
            }
        }
    }

    //To print the board
    private void printBoard() {

        for (Status[] s : myStatus) {
            System.out.println(rowToString(s));
        }
    }

    //To print the game logic board
    private String rowToString(Status[] s) {

        String out = "";
        for (Status curr : s) {
            switch (curr) {
                case ONE:
                    out += "H";
                    break;
                case TWO:
                    out += "A";
                    break;
                case NEITHER:
                    out += "-";
                    break;
            }
        }
        return out;
    }

    //Checking game ended or not
    private boolean gameOver() {
        boolean winnerFound = false;

        if (!isBoardFull()) {
            for (int i = myStatus.length - 1; i >= 0; i--) {
                for (int j = myStatus[i].length - 1; j >= 0 && !winnerFound; j--) {
                    if (myStatus[i][j] != Status.NEITHER) {
                        if (checkLeft(myStatus[i][j], i, j) || checkUp(myStatus[i][j], i, j) ||
                                checkUpLeft(myStatus[i][j], i, j) || checkUpRight(myStatus[i][j], i, j)) {
                            winnerFound = true;
                        }
                    }
                }
            }
        } else {
            human.gameOver(Status.NEITHER);
        }
        return winnerFound;
    }


    //Checking left Direction to find winner
    private boolean checkLeft(Status status, int row, int col) {
        boolean winnerFound = false;
        int counter = 0;
        for (int i = 0; i < 4 && (col - 3) >= 0; i++) {
            if (myStatus[row][col - i] == status) {
                counter++;
            }
        }
        if (counter == 4) {
            winnerFound = true;
        }
        return winnerFound;
    }
    //Checking Up Direction to find winner
    private boolean checkUp(Status status, int row, int col) {
        boolean winnerFound = false;
        int counter = 0;
        for (int i = 0; i < 4 && (row - 3) >= 0; i++) {
            if (myStatus[row - i][col] == status) {
                counter++;
            }
        }
        if (counter == 4) {
            winnerFound = true;
        }
        return winnerFound;
    }

    public int getSize(){
        return boardSize;
    }

    //Checking up-left Direction to find winner
    private boolean checkUpLeft(Status status, int row, int col) {
        boolean winnerFound = false;
        int counter = 0;
        for (int i = 0; i < 4 && ((row - 3) >= 0) && ((col - 3) >= 0); i++) {
            if (myStatus[row - i][col - i] == status) {
                counter++;
            }
        }
        if (counter == 4) {
            winnerFound = true;
        }
        return winnerFound;
    }
    //Checking up-right Direction to find winner
    private boolean checkUpRight(Status status, int row, int col) {
        boolean winnerFound = false;
        int counter = 0;
        for (int i = 0; i < 4 && ((row + 3) <= boardSize - 1) && ((col - 3) >= 0); i++) {
            if (myStatus[row + i][col - i] == status) {
                counter++;
            }
        }
        if (counter == 4) {
            winnerFound = true;
        }
        return winnerFound;
    }

    //checking board full or not
    private boolean isBoardFull() {
        boolean isFull = false;
        boolean notFull = false;
        for (int i = 0; i < myStatus.length && !notFull; i++) {
            if (myStatus[0][i] == Status.NEITHER) {
                notFull = true;
            }
        }
        if (!notFull) {
            isFull = true;
        }
        return isFull;
    }

    //choosing random player
    private int randomPlayerSelect() {
        return (int) (Math.random() * 2);
    }


    //choosing random board size(6-12)
    private int randomBoardSize() {
        return (int) (Math.random() * 5) + 3;
    }
//
//    private int randomBoardSize() {
//        return (int) (Math.random() * 7) + 6;
//    }

    /**
     * drop - a private helper method that finds the position of a marker
     * when it is dropped in a column.
     * @param col the column where the piece is dropped
     * @return the row where the piece lands
     */
    private int drop(int col) {
        int posn = 0;

        System.out.println(col);

        while (posn < myStatus.length && myStatus[posn][col] == Status.NEITHER) {
            posn++;
        }
        return posn - 1;
    }

    /**
     * verifyCol - private helper method to determine if an integer is a valid
     * column that still has spots left.
     * @param col - integer (potential column number)
     * @return - is the column valid?
     */
    private boolean verifyCol(int col) {
        return (col >= 0 && col < myStatus[0].length && myStatus[0][col] == Status.NEITHER);
    }

}
