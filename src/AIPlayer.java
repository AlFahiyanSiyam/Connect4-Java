
//-----------------------------------------
// NAME		: AL Fahiyan Siyam
// STUDENT NUMBER	: 7852355
// COURSE		: COMP 2150
// INSTRUCTOR	: Mike Domaratzki
// ASSIGNMENT	: assignment 3
// QUESTION	: question 1
//
// REMARKS: This class is responsible to maintain rules for the AI Playe.
//          Check possibles cases to stop human player, check possible causes to win the game
//
//-----------------------------------------


public class AIPlayer implements Player {
    private int boardSize;
    private GameLogic aiGameLogic;
    private int lastMoveHuman;
    private Status[][] aiBoard;
    private boolean inputForRightLeft;
    private boolean inputForBottomUp;
    private boolean inputForUpperDiagonals;
    private boolean inputForLowerDiagonals;
    private int aiLastMove;

    //constructor
    public AIPlayer() {
        inputForBottomUp = false;
        inputForLowerDiagonals = false;
        inputForRightLeft = false;
        inputForUpperDiagonals = false;
        aiLastMove = -1;
    }


    //providing AI's best move
    //blocking Human's possible winning space
    //sending report to game logic
    public void lastMove(int lastCol) {

        if (lastCol != -1) {
            lastMoveHuman = lastCol;
            int x = -1;

            int dropValueForHuman = drop(lastCol);

            aiBoard[dropValueForHuman][lastCol] = Status.ONE;


            if (checkAiWinning() != -1) {
                x = checkAiWinning();
                int dropValueForAi = drop(x);
                aiBoard[dropValueForAi][x] = Status.TWO;

                aiGameLogic.setAnswer(x);
            } else if (humanAboutToWin() != -1) {


                if (inputForRightLeft) {
                    x = humanAboutToWin();
                    int dropValueForAi = drop(x);

                    aiBoard[dropValueForAi][x] = Status.TWO;
                    // printBoard();
                    inputForRightLeft = false;
                    aiGameLogic.setAnswer(x);
                } else if (inputForUpperDiagonals) {
                    x = humanAboutToWin();
                    int dropValueForAi = drop(x);
                    aiBoard[dropValueForAi][x] = Status.TWO;
                    //   printBoard();
                    inputForUpperDiagonals = false;
                    aiGameLogic.setAnswer(x);
                } else if (inputForLowerDiagonals) {
                    x = humanAboutToWin();
                    int dropValueForAi = drop(x);
                    aiBoard[dropValueForAi][x] = Status.TWO;
                    //   printBoard();
                    inputForLowerDiagonals = false;
                    aiGameLogic.setAnswer(x);

                } else if (inputForBottomUp) {
                    x = humanAboutToWin();
                    //   int dropValueForAi = drop(x);
                    aiBoard[x][lastMoveHuman] = Status.TWO;
                    //  printBoard();
                    inputForBottomUp = false;
                    aiGameLogic.setAnswer(lastMoveHuman);

                }
                aiLastMove = x;


            } else {

                if (verifyCol(aiLastMove)) {


                    x = aiLastMove;
                    int dropValueForAi = drop(x);
                    aiBoard[dropValueForAi][x] = Status.TWO;
                    aiGameLogic.setAnswer(x);


                } else {

                    x = randomBoardSize();
                    int dropValueForAi = drop(x);
                    while (!verifyCol(x)) {
                        x = randomBoardSize();
                        dropValueForAi = drop(x);
                    }
                    aiBoard[dropValueForAi][x] = Status.TWO;

                    aiGameLogic.setAnswer(x);
                }

            }


        } else {

            int setAiMove = boardSize / 2;
            if (verifyCol(setAiMove)) {
                int setAiMoveRow = drop(setAiMove);
                aiBoard[setAiMoveRow][setAiMove] = Status.TWO;
                aiLastMove = setAiMove;
                aiGameLogic.setAnswer(setAiMove);
            }
        }
    }

    //Checking up-left Direction to find  winning move for AI
    private int checkAiWinning() {
        boolean winnerFound = false;
        int temp = -1;

        for (int i = aiBoard.length - 1; i >= 0; i--) {
            for (int j = aiBoard[i].length - 1; j >= 0 && !winnerFound; j--) {
                if (aiBoard[i][j] != Status.NEITHER && aiBoard[i][j] != Status.ONE) {
                    if (checkAiUp(aiBoard[i][j], i, j) != -1) {
                        temp = checkAiUp(aiBoard[i][j], i, j);
                        winnerFound = true;
                    } else if (checkAiLeft(aiBoard[i][j], i, j) != -1) {
                        temp = checkAiLeft(aiBoard[i][j], i, j);
                        winnerFound = true;
                    } else if (checkAiUpLeft(aiBoard[i][j], i, j) != -1) {
                        temp = checkAiUpLeft(aiBoard[i][j], i, j);
                        winnerFound = true;
                    } else if (checkUpRight(aiBoard[i][j], i, j) != -1) {
                        temp = checkUpRight(aiBoard[i][j], i, j);
                        winnerFound = true;
                    }


                }
            }
        }
        return temp;
    }

    //Checking up-left Direction to find  winning move for AI
    private int checkAiUp(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        for (int i = 0; i < 3 && (row - 3) >= 0; i++) {
            if (aiBoard[row - i][col] == status) {
                counter++;
            }
        }
        if (counter == 3) {

            temp = col;
        }
        return temp;
    }


    //Checking up-left Direction to find  winning move for AI
    private int checkAiLeft(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        for (int i = 0; i < 3 && (col - 3) >= 0; i++) {
            if (aiBoard[row][col - i] == status) {
                counter++;
            }
        }
        if (counter == 3 && verifyCol(col - 3) && drop(col - 3) == row) {

            temp = col - 3;
        }
        return temp;
    }

    //Checking up-left Direction to find  winning move for AI
    private int checkAiUpLeft(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        for (int i = 0; i < 3 && ((row - 3) >= 0) && ((col - 3) >= 0); i++) {
            if (aiBoard[row - i][col - i] == status) {
                counter++;
            }
        }
        if (counter == 3 && verifyCol(col - 3) && drop(col - 3) == row - 3) {
            temp = col - 3;
        }
        return temp;
    }
    //Checking up-right Direction to find winner

    private int checkUpRight(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        for (int i = 0; i < 3 && ((row + 3) <= boardSize - 1) && ((col - 3) >= 0); i++) {
            if (aiBoard[row + i][col - i] == status) {
                counter++;
            }
        }
        if (counter == 3 && verifyCol(col - 3) && drop(col - 3) == row + 3) {
            temp = col - 3;
        }
        return temp;
    }

    //checking Human's possible winning move
    private int humanAboutToWin() {
        boolean winnerFound = false;
        int temp = -1;
        for (int i = aiBoard.length - 1; i >= 0; i--) {
            for (int j = aiBoard[i].length - 1; j >= 0 && !winnerFound; j--) {
                if (aiBoard[i][j] != Status.NEITHER) {
                    if (checkRightToLeftRow(aiBoard[i][j], i, j) != -1) {
                        temp = checkRightToLeftRow(aiBoard[i][j], i, j);
                        winnerFound = true;
                    } else if (checkLeftToRightRow(aiBoard[i][j], i, j) != -1) {
                        temp = checkLeftToRightRow(aiBoard[i][j], i, j);
                        winnerFound = true;
                    } else if (checkUpperRight(aiBoard[i][j], i, j) != -1) {
                        temp = checkUpperRight(aiBoard[i][j], i, j);
                        winnerFound = true;
                    } else if (checkUpperLeft(aiBoard[i][j], i, j) != -1) {
                        temp = checkUpperLeft(aiBoard[i][j], i, j);
                        winnerFound = true;
                    } else if (checkDownLeft(aiBoard[i][j], i, j) != -1) {
                        temp = checkDownLeft(aiBoard[i][j], i, j);
                        winnerFound = true;
                    } else if (checkDownRight(aiBoard[i][j], i, j) != -1) {
                        temp = checkDownRight(aiBoard[i][j], i, j);
                        winnerFound = true;
                    } else if (checkBottomToUpRow(aiBoard[i][j], i, j) != -1) {
                        temp = checkBottomToUpRow(aiBoard[i][j], i, j);
                        winnerFound = true;
                    }
                }
            }
        }
        return temp;

    }
    //checking Human's possible winning move

    private int checkRightToLeftRow(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        for (int i = 0; i < 2 && (col - 3) >= 0; i++) {
            if (aiBoard[row][col - i] == status) {
                counter++;
            }
        }
        if (counter == 2 && aiBoard[row][col - 2] == Status.NEITHER) {

            if (verifyCol(row + 1) && aiBoard[row + 1][col - 2] != Status.NEITHER) {
                temp = col - 2;
            } else {
                temp = col - 2;
            }
            inputForBottomUp = false;
            inputForLowerDiagonals = false;
            inputForRightLeft = true;
            inputForUpperDiagonals = false;


        }
        return temp;
    }

    //checking Human's possible winning move

    private int checkLeftToRightRow(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        for (int i = 0; i < 2 && (col + 3) <= boardSize - 1; i++) {
            if (aiBoard[row][col + i] == status) {
                counter++;
            }
        }
        if (counter == 2 && aiBoard[row][col + 2] == Status.NEITHER) {

            if (verifyCol(row + 1)) {
                temp = col + 2;
            } else {
                temp = col + 2;
            }
            inputForBottomUp = false;
            inputForLowerDiagonals = false;
            inputForRightLeft = true;
            inputForUpperDiagonals = false;
        }

        return temp;
    }

    //checking Human's possible winning move

    private int checkBottomToUpRow(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        for (int i = 0; i < 3 && (row - 3) >= 0; i++) {
            if (aiBoard[row - i][col] == status) {
                counter++;
            }
        }

        if (counter == 3 && aiBoard[row - 3][col] == Status.NEITHER) {
            temp = row - 3;
            inputForBottomUp = true;
            inputForLowerDiagonals = false;
            inputForRightLeft = false;
            inputForUpperDiagonals = false;
        }
        return temp;
    }


    //checking Human's possible winning move

    private int checkUpperRight(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        //row--,col++
        for (int i = 0; i < 2 && (row - 2) >= 0 && (col + 2) <= boardSize - 1 && verifyCol(col + 2); i++) {
            if (aiBoard[row - i][col + i] == status) {
                counter++;
            }
        }
        if (counter == 2 && aiBoard[row - 2][col + 2] == Status.NEITHER && aiBoard[row - 1][col + 2] != Status.NEITHER) {
            temp = col + 2;
            inputForBottomUp = false;
            inputForLowerDiagonals = false;
            inputForRightLeft = false;
            inputForUpperDiagonals = true;
        }
        return temp;
    }


    //checking Human's possible winning move
    private int checkUpperLeft(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        //row--,col++
        for (int i = 0; i < 2 && (row - 2) >= 0 && (col - 2) >= 0 && verifyCol(col - 2); i++) {
            if (aiBoard[row - i][col - i] == status) {
                counter++;
            }
        }
        if (counter == 2 && aiBoard[row - 2][col - 2] == Status.NEITHER && aiBoard[row - 1][col - 2] != Status.NEITHER) {
            temp = col - 2;
            inputForBottomUp = false;
            inputForLowerDiagonals = false;
            inputForRightLeft = false;
            inputForUpperDiagonals = true;
        }
        return temp;
    }

    //checking Human's possible winning move
    private int checkDownLeft(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        //row++,col--
        for (int i = 0; i < 2 && verifyCol(col - 2) && (row + 2) <= boardSize - 1 && (col - 2) >= 0; i++) {
            if (aiBoard[row + i][col - i] == status) {
                counter++;
            }
        }
        if (counter == 2 && verifyCol(row + 3) && verifyCol(col - 2) && aiBoard[row + 2][col - 2] == Status.NEITHER && aiBoard[row + 3][col - 2] != Status.NEITHER) {
            temp = col - 2;
            inputForBottomUp = false;
            inputForLowerDiagonals = true;
            inputForRightLeft = false;
            inputForUpperDiagonals = false;
        }
        return temp;
    }

    //checking Human's possible winning move
    private int checkDownRight(Status status, int row, int col) {
        int temp = -1;
        int counter = 0;
        //row++,col--
        for (int i = 0; i < 2 && verifyCol(col + 2) && (row + 2) <= boardSize - 1 && (col - 2) >= 0; i++) {
            if (aiBoard[row + i][col + i] == status) {
                counter++;
            }
        }
        if (counter == 2 && verifyCol(row + 3) && verifyCol(col + 2) && aiBoard[row + 2][col - 2] == Status.NEITHER && aiBoard[row + 3][col - 2] != Status.NEITHER) {
            temp = col + 2;
            inputForBottomUp = false;
            inputForLowerDiagonals = true;
            inputForRightLeft = false;
            inputForUpperDiagonals = false;
        }
        return temp;
    }


    private int randomBoardSize() {
        return (int) (Math.random() * (boardSize - 6));

    }

    public void gameOver(Status winner) {


    }

    //storing information  from game logic
    public void setInfo(int size, GameLogic gl) {
        boardSize = size;
        aiGameLogic = gl;
        aiBoard = new Status[size][size];
        initializeAiBoard();
    }

    //Initializing AI's board
    private void initializeAiBoard() {
        for (int i = 0; i < aiBoard.length; i++) {
            for (int j = 0; j < aiBoard[i].length; j++) {
                aiBoard[i][j] = Status.NEITHER;
            }
        }
    }

    /**
     * verifyCol - private helper method to determine if an integer is a valid
     * column that still has spots left.
     *
     * @param col - integer (potential column number)
     * @return - is the column valid?
     */
    private boolean verifyCol(int col) {
        return (col >= 0 && col < aiBoard[0].length && aiBoard[0][col] == Status.NEITHER);
    }

    /**
     * drop - a private helper method that finds the position of a marker
     * when it is dropped in a column.
     *
     * @param col the column where the piece is dropped
     * @return the row where the piece lands
     */
    private int drop(int col) {
        int posn = 0;
        while (posn < aiBoard.length && aiBoard[posn][col] == Status.NEITHER) {
            posn++;
        }
        return posn - 1;
    }

    /**
     * rowToString - private helper method to print a single
     * row of the board
     *
     * @param s - an array from the board to be printed.
     * @return - String representation of the board.
     */
    private String rowToString(Status[] s) {

        String out = "";
        for (Status curr : s) {
            switch (curr) {
                case ONE:
                    out += "O";
                    break;
                case TWO:
                    out += "X";
                    break;
                case NEITHER:
                    out += "=";
                    break;
            }
        }
        return out;
    }

    /**
     * printBoard - private helper method to print the board.
     */
    private void printBoard() {

        for (Status[] s : aiBoard) {
            System.out.println(rowToString(s));
        }
    }

}
