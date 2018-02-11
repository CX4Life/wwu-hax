// Iris Larsen
// Sara J. Johnson
// Tim Woods
// Wyatt Chapman
import java.util.*;

public class Game {

    static Checker[][] board = new Checker[8][8];

    public static void main(String args[]) {

        //true: black's turn
        //false: red's turn
        //black goes first
        boolean bTurn = true;

        //true until game over
        boolean gameOn = true;

        initBoard();
        System.out.println("Hi! welcome to the game of checkers!");
        while (gameOn) {
            if (hasPieces(bTurn)) {
                if (possibleStates(bTurn)) {
                    // move function here
                    bTurn = !bTurn;
                } else {
                    gameOn = false;
                }
            } else {
                gameOn = false;
            }
        }
        //printBoard();
        return;
    }

    /*initBoard creates clean game board; sets piece IDs,
     *colors, and starting positions
     */
    public static void initBoard() {
        int ID = 0;
        int i;
        int j;
        for (i = 0; i < 3; i++) {
            for(j = 0; j < 8; j++) {
                if (((i & 1) ^ (j & 1)) == 1) {
                    board[i][j] = new Checker(ID, 1, i, j);
                    ID++;
                } else {
                    board[i][j] = null;
                }
            }
        }
        for (i = 3; i < 5; i++) {
            for (j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
        for (i = 5; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                if (((i & 1) ^ (j & 1)) == 1) {
                    board[i][j] = new Checker(ID, 3, i, j);
                    ID++;
                } else {
                    board[i][j] = null;
                }
            }
        }
        return;
    }

    public static boolean hasPieces(boolean bTurn) {
	// check black pieces
        if (bTurn) {
            for (int i=0; i<8; i++) {
                for (int j=0; j<8; j++) {
                    if (board[i][j].getType() == 1) {
                        return true;
                    } else if (board[i][j].getType() == 2) {
                        return true;
                    }
                }
            }
            // check red pieces
        } else {
            for (int i=0; i<8; i++) {
                for (int j=0; j<8; j++) {
                    if (board[i][j].getType() == 3) {
                        return true;
                    } else if (board[i][j].getType() == 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*printBoard outputs ASCII representation of game board
     *to the terminal
     */
    public static void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print("0");
                } else {
                    System.out.print(board[i][j].getType());
                }
            }
            System.out.println();
        }
    }

    public static boolean possibleStates(boolean bTurn) {
        int i;
        int j;
        if (bTurn) {
            for (i = 0; i < 10; i++) {
                for (j = 0; j < 10; j++) {
                    if (board[i][j].getType() == 1) {

                    } else if (board[i][j].getType() == 2) {

                    }
                }
            }
        } else {
            for (i = 0; i < 10; i++) {
                for (j = 0; j < 10; j++) {
                    if (board[i][j].getType() == 3) {

                    } else if (board[i][j].getType() == 4) {

                    }
                }
            }

    }

    public static boolean checkJump(int i, int j) {
        Checker save;
        boolean tail = true; //signals end of jump chain
        switch (board[i][j].getType()) {
            case 1:
                if (j < 6) {

                    /* check down-left jump */
                    if (i > 1) {
                        if (board[i - 1][j + 1] != null) {
                            if (board[i - 1][j + 1].getType() > 2) {
                                if (board[i - 2][j + 2] == null) {

                                    /* left jump valid */
                                    tail = false;
                                    board[i - 2][j + 2] = board[i][j];
                                    board[i - 2][j + 2].setLocation(i - 2, j + 2);
                                    if (j + 2 == 7) {
                                        /* end of board; king the piece */
                                        board[i - 2][j + 2].setType(2);
                                    }
                                    board[i][j] = null;
                                    save = board[i - 1][j + 1];
                                    board[i - 1][j + 1] = null;
                                    checkJump(i - 2, j + 2);

                                    /* reset state */
                                    board[i][j] = board[i - 2][j + 2];
                                    board[i][j].setLocation(i, j);
                                    board[i][j].setType(1);
                                    board[i - 2][j + 2] = null;
                                    board[i - 1][j + 1] = save;
                                }
                            }
                        }
                    }

                    /* check down-right jump */
                    if (i < 6) {
                        if (board[i + 1][j + 1] != null) {
                            if (board[i + 1][j + 1].getType() > 2) {
                                if (board[i + 2][j + 2] == null) {
                                    /* right jump valid */
                                    tail = false;
                                    board[i + 2][j + 2] = board[i][j];
                                    board[i + 2][j + 2].setLocation(i + 2, j + 2);
                                    if (j + 2 == 7) {
                                        /* end of board; king the piece */
                                        board[i + 2][j + 2].setType(2);
                                    }
                                    board[i][j] = null;
                                    save = board[i + 1][j + 1];
                                    board[i + 1][j + 1] = null;
                                    checkJump(i + 2, j + 2);

                                    /* reset state */
                                    board[i][j] = board[i + 2][j + 2];
                                    board[i][j].setLocation(i, j);
                                    board[i][j].setType(1);
                                    board[i + 2][j + 2] = null;
                                    board[i + 1][j + 1] = save;
                                }
                            }
                        }
                    }


                }
                break;
            case 2:
                if (j < 6) {

                    /* check down-left jump */
                    if (i > 1) {
                        if (board[i - 1][j + 1] != null) {
                            if (board[i - 1][j + 1].getType() > 2) {
                                if (board[i - 2][j + 2] == null) {

                                    /* down-left jump valid */
                                    tail = false;
                                    board[i - 2][j + 2] = board[i][j];
                                    board[i - 2][j + 2].setLocation(i - 2, j + 2);
                                    board[i][j] = null;
                                    save = board[i - 1][j + 1];
                                    board[i - 1][j + 1] = null;
                                    checkJump(i - 2, j + 2);

                                    /* reset state */
                                    board[i][j] = board[i - 2][j + 2];
                                    board[i][j].setLocation(i, j);
                                    board[i - 2][j + 2] = null;
                                    board[i - 1][j + 1] = save;
                                }
                            }
                        }
                    }

                    /* check down-right jump */
                    if (i < 6) {
                        if (board[i + 1][j + 1] != null) {
                            if (board[i + 1][j + 1].getType() > 2) {
                                if (board[i + 2][j + 2] == null) {
                                    /* right jump valid */
                                    tail = false;
                                    board[i + 2][j + 2] = board[i][j];
                                    board[i + 2][j + 2].setLocation(i + 2, j + 2);
                                    board[i][j] = null;
                                    save = board[i + 1][j + 1];
                                    board[i + 1][j + 1] = null;
                                    checkJump(i + 2, j + 2);

                                    /* reset state */
                                    board[i][j] = board[i + 2][j + 2];
                                    board[i][j].setLocation(i, j);
                                    board[i + 2][j + 2] = null;
                                    board[i + 1][j + 1] = save;
                                }
                            }
                        }
                    }


                }

                if (j > 1) {

                    /* check up-left jump */
                    if (i > 1) {
                        if (board[i - 1][j - 1] != null) {
                            if (board[i - 1][j - 1].getType() > 2) {
                                if (board[i - 2][j - 2] == null) {

                                    /* up-left jump valid */
                                    tail = false;
                                    board[i - 2][j - 2] = board[i][j];
                                    board[i - 2][j - 2].setLocation(i - 2, j - 2);
                                    board[i][j] = null;
                                    save = board[i - 1][j - 1];
                                    board[i - 1][j - 1] = null;
                                    checkJump(i - 2, j - 2);

                                    /* reset state */
                                    board[i][j] = board[i - 2][j - 2];
                                    board[i][j].setLocation(i, j);
                                    board[i - 2][j - 2] = null;
                                    board[i - 1][j - 1] = save;
                                }
                            }
                        }
                    }

                    /* check up-right jump */
                    if (i < 6) {
                        if (board[i + 1][j - 1] != null) {
                            if (board[i + 1][j - 1].getType() > 2) {
                                if (board[i + 2][j - 2] == null) {
                                    /* up-right jump valid */
                                    tail = false;
                                    board[i + 2][j - 2] = board[i][j];
                                    board[i + 2][j - 2].setLocation(i + 2, j - 2);
                                    board[i][j] = null;
                                    save = board[i + 1][j - 1];
                                    board[i + 1][j - 1] = null;
                                    checkJump(i + 2, j - 2);

                                    /* reset state */
                                    board[i][j] = board[i + 2][j - 2];
                                    board[i][j].setLocation(i, j);
                                    board[i + 2][j - 2] = null;
                                    board[i + 1][j - 1] = save;
                                }
                            }
                        }
                    }


                }
                break;
            case 3:
                if (j > 1) {

                    /* check up-left jump */
                    if (i > 1) {
                        if (board[i - 1][j - 1] != null) {
                            if (board[i - 1][j - 1].getType() == 1 || board[i - 1][j - 1].getType() == 2) {
                                if (board[i - 2][j - 2] == null) {

                                    /* up-left jump valid */
                                    tail = false;
                                    board[i - 2][j - 2] = board[i][j];
                                    board[i - 2][j - 2].setLocation(i - 2, j - 2);
                                    if (j - 2 == 0) {
                                        /* end of board; king the piece */
                                        board[i - 2][j - 2].setType(4);
                                    }
                                    board[i][j] = null;
                                    save = board[i - 1][j - 1];
                                    board[i - 1][j - 1] = null;
                                    checkJump(i - 2, j - 2);

                                    /* reset state */
                                    board[i][j] = board[i - 2][j - 2];
                                    board[i][j].setLocation(i, j);
                                    board[i][j].setType(3);
                                    board[i - 2][j - 2] = null;
                                    board[i - 1][j - 1] = save;
                                }
                            }
                        }
                    }

                    /* check up-right jump */
                    if (i < 6) {
                        if (board[i + 1][j - 1] != null) {
                            if (board[i + 1][j - 1].getType() == 1 || board[i + 1][j - 1].getType() == 2) {
                                if (board[i + 2][j - 2] == null) {
                                    /* up-right jump valid */
                                    tail = false;
                                    board[i + 2][j - 2] = board[i][j];
                                    board[i + 2][j - 2].setLocation(i + 2, j - 2);
                                    if (j - 2 == 0) {
                                        /* end of board; king the piece */
                                        board[i + 2][j - 2].setType(4);
                                    }
                                    board[i][j] = null;
                                    save = board[i + 1][j - 1];
                                    board[i + 1][j - 1] = null;
                                    checkJump(i + 2, j - 2);

                                    /* reset state */
                                    board[i][j] = board[i + 2][j - 2];
                                    board[i][j].setLocation(i, j);
                                    board[i][j].setType(3);
                                    board[i + 2][j - 2] = null;
                                    board[i + 1][j - 1] = save;
                                }
                            }
                        }
                    }


                }
                break;
            case 4:
                if (j < 6) {

                    /* check down-left jump */
                    if (i > 1) {
                        if (board[i - 1][j + 1] != null) {
                            if (board[i - 1][j + 1].getType() == 1 || board[i - 1][j + 1].getType() == 2) {
                                if (board[i - 2][j + 2] == null) {

                                    /* down-left jump valid */
                                    tail = false;
                                    board[i - 2][j + 2] = board[i][j];
                                    board[i - 2][j + 2].setLocation(i - 2, j + 2);
                                    board[i][j] = null;
                                    save = board[i - 1][j + 1];
                                    board[i - 1][j + 1] = null;
                                    checkJump(i - 2, j + 2);

                                    /* reset state */
                                    board[i][j] = board[i - 2][j + 2];
                                    board[i][j].setLocation(i, j);
                                    board[i - 2][j + 2] = null;
                                    board[i - 1][j + 1] = save;
                                }
                            }
                        }
                    }

                    /* check down-right jump */
                    if (i < 6) {
                        if (board[i + 1][j + 1] != null) {
                            if (board[i - 1][j + 1].getType() == 1 || board[i - 1][j + 1].getType() == 2) {
                                if (board[i + 2][j + 2] == null) {
                                    /* right jump valid */
                                    tail = false;
                                    board[i + 2][j + 2] = board[i][j];
                                    board[i + 2][j + 2].setLocation(i + 2, j + 2);
                                    board[i][j] = null;
                                    save = board[i + 1][j + 1];
                                    board[i + 1][j + 1] = null;
                                    checkJump(i + 2, j + 2);

                                    /* reset state */
                                    board[i][j] = board[i + 2][j + 2];
                                    board[i][j].setLocation(i, j);
                                    board[i + 2][j + 2] = null;
                                    board[i + 1][j + 1] = save;
                                }
                            }
                        }
                    }


                }

                if (j > 1) {

                    /* check up-left jump */
                    if (i > 1) {
                        if (board[i - 1][j - 1] != null) {
                            if (board[i - 1][j + 1].getType() == 1 || board[i - 1][j + 1].getType() == 2) {
                                if (board[i - 2][j - 2] == null) {

                                    /* up-left jump valid */
                                    tail = false;
                                    board[i - 2][j - 2] = board[i][j];
                                    board[i - 2][j - 2].setLocation(i - 2, j - 2);
                                    board[i][j] = null;
                                    save = board[i - 1][j - 1];
                                    board[i - 1][j - 1] = null;
                                    checkJump(i - 2, j - 2);

                                    /* reset state */
                                    board[i][j] = board[i - 2][j - 2];
                                    board[i][j].setLocation(i, j);
                                    board[i - 2][j - 2] = null;
                                    board[i - 1][j - 1] = save;
                                }
                            }
                        }
                    }

                    /* check up-right jump */
                    if (i < 6) {
                        if (board[i + 1][j - 1] != null) {
                            if (board[i - 1][j + 1].getType() == 1 || board[i - 1][j + 1].getType() == 2) {
                                if (board[i + 2][j - 2] == null) {
                                    /* up-right jump valid */
                                    tail = false;
                                    board[i + 2][j - 2] = board[i][j];
                                    board[i + 2][j - 2].setLocation(i + 2, j - 2);
                                    board[i][j] = null;
                                    save = board[i + 1][j - 1];
                                    board[i + 1][j - 1] = null;
                                    checkJump(i + 2, j - 2);

                                    /* reset state */
                                    board[i][j] = board[i + 2][j - 2];
                                    board[i][j].setLocation(i, j);
                                    board[i + 2][j - 2] = null;
                                    board[i + 1][j - 1] = save;
                                }
                            }
                        }
                    }


                }
                break;
        }
        if (tail) {
            printBoard();
        }
    }

}
