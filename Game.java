// Iris Larsen
// Sara J. Johnson
// Tim Woods
// Wyatt Chapman
import java.util.*;

public class Game {

    static Checker[][] board = new Checker[8][8];
    static ArrayList<String> moveList = new ArrayList<String>();

    public static void main(String args[]) {

        //true: black's turn
        //false: red's turn
        //black goes first
        boolean bTurn = true;

        //true until game over
        boolean gameOn = true;

        initBoard();
        //System.out.println("Hi! welcome to the game of checkers!");
        while (gameOn) {
            if (hasPieces(bTurn)) {
		//ArrayList<String> moveList = new ArrayList<String>();
		moveList.clear();
                if (possibleStates(bTurn)) {
		    System.out.println("?");
                    // move function here
		    attemptMove();
		    //System.exit(0);
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

    public static void attemptMove() {
	Scanner scan = new Scanner(System.in);
	int input = scan.nextInt();
	String move = moveList.get(input);
	System.out.println(move);
    }

    /*initBoard creates clean game board; sets piece IDs,
     *colors, and starting positions
     */
    public static void initBoard() {
        int ID = 0;
        int i;
        int j;
        for (j = 0; j < 3; j++) {
            for(i = 0; i < 8; i++) {
                if (((i & 1) ^ (j & 1)) == 1) {
                    board[i][j] = new Checker(ID, 1, i, j);
                    ID++;
                } else {
                    board[i][j] = null;
                }
            }
        }
        for (j = 3; j < 5; j++) {
            for (i = 0; i < 8; i++) {
                board[i][j] = null;
            }
        }
        for (j = 5; j < 8; j++) {
            for (i = 0; i < 8; i++) {
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
		    if (board[i][j] != null) {
			if (board[i][j].getType() == 1) {
			    return true;
			} else if (board[i][j].getType() == 2) {
			    return true;
			}
		    }
                }
            }
            // check red pieces
        } else {
            for (int i=0; i<8; i++) {
                for (int j=0; j<8; j++) {
		    if (board[i][j] != null) {
			if (board[i][j].getType() == 3) {
			    return true;
			} else if (board[i][j].getType() == 4) {
			    return true;
			}
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
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                if (board[i][j] == null) {
                    System.out.print("0");
                } else {
                    System.out.print(board[i][j].getType());
                }
            }
            System.out.println();
        }
	System.out.println();
    }

    public static boolean possibleStates(boolean bTurn) {
        int i;
        int j;
        boolean yesJump = false;
        //boolean canMove = false;
        int moveCount = 0;
	String mlist = "";

        if (bTurn) {
            for (i = 0; i < 8; i++) {
                for (j = 0; j < 8; j++) {
		    if (board[i][j] != null) {
			if (board[i][j].getType() == 1) {
			    // if you can jump
			    if (canJump(i,j)) {
				yesJump = true;
				moveCount++;
				String nlist = new String(mlist);
				checkJump(i,j, nlist);
			    }
			} else if (board[i][j].getType() == 2) {
			    // if you can jump
			    if (canJump(i,j)) {
				yesJump = true;
				moveCount++;
				String nlist = new String(mlist);
				checkJump(i,j, nlist);
			    }
			}
                    }
                }
            }
        } else {
            for (i = 0; i < 8; i++) {
                for (j = 0; j < 8; j++) {
		    if (board[i][j] != null) {
			if (board[i][j].getType() == 3) {
			    // if you can jump
			    if (canJump(i,j)) {
				yesJump = true;
				moveCount++;
				String nlist = new String(mlist);
				checkJump(i,j,nlist);
			    }
			} else if (board[i][j].getType() == 4) {
			    // if you can jump
			    if (canJump(i,j)) {
				yesJump = true;
				moveCount++;
				String nlist = new String(mlist);
				checkJump(i,j,nlist);
			    }
			}
                    }
                }
            }
	}

	if (yesJump == false) {
	    if (bTurn) {
		for (i = 0; i < 8; i++) {
		    for (j = 0; j < 8; j++) {
			if (board[i][j] != null) {
			    if (board[i][j].getType() < 3) {
				if (j < 7) {
				    if (i > 0) {
					moveCount += moveDownLeft(i,j);
				    }
				    if (i < 7) {
					moveCount += moveDownRight(i,j);
				    }
				}
			    }
			    //if (board[i][j] != null) {
			    if (board[i][j].getType() == 2) {
				if (j > 0) {
				    if (i > 0) {
					moveCount += moveUpLeft(i,j);
				    }
				    if (i<7) {
					moveCount += moveUpRight(i,j);
				    }
				}
			    }
			}
		    }
		}
	    } else {
		for (i = 0; i < 8; i++) {
		    for (j = 0; j < 8; j++) {
			if (board[i][j] != null) {
			    if (board[i][j].getType() > 2) {
				if (j>0) {
				    if (i>0) {
					moveCount += moveUpLeft(i,j);
				    }
				    if (i<7) {
					moveCount += moveUpRight(i,j);
				    }
				}
			    }
			//if (board[i][j] != null) {
			    if (board[i][j].getType() == 4) {
				if (j<7) {
				    if (i>0) {
					moveCount += moveDownLeft(i,j);
				    }
				    if (i<7) {
					moveCount += moveDownRight(i,j);
				    }
				}
			    }
			}
		    }
		}
	    }
	}

	if (moveCount > 0) {
	    return true;
	}
	return false;
    }

    public static boolean canJump(int i, int j) {
	if (board[i][j].getType() < 3) {
	    if (j<6) {
		// move down left
		if (i>1) {
		    if (board[i-1][j+1] != null) {
			if (board[i-1][j+1].getType() > 2) {
			    if (board[i-2][j+2] == null) {
				return true;
			    }
			}
		    }
		// move down right
		} else if (i<6) {
		    if (board[i+1][j+1] != null) {
			if (board[i+1][j+1].getType() > 2) {
			    if (board[i+2][j+2] == null) {
				return true;
			    }
			}
		    }
		}
	    }
	    if (board[i][j].getType() == 2) {
		if (j>2) {
		    // move up left
		    if (i>1) {
			if (board[i-1][j-1] != null) {
			    if (board[i-1][j-1].getType() > 2) {
				if (board[i-2][j-2] == null) {
				    return true;
				}
			    }
			}
		    // move up right
		    } else if (i<6) {
			if (board[i+1][j-1] != null) {
			    if (board[i+1][j-2].getType() > 2) {
				if (board[i+2][j-2] == null) {
				    return true;
				}
			    }
			}
		    }
		}
	    }
	} else {
	    if (j>2) {
		// move up left
		if (i>1) {
		    if (board[i-1][j-1] != null) {
			if (board[i-1][j-1].getType() < 3) {
			    if (board[i-2][j-2] == null) {
				return true;
			    }
			}
		    }
		// move up right
		} else if (i<6) {
		    if (board[i+1][j-1] != null) {
			if (board[i+1][j-1].getType() < 3) {
			    if (board[i+2][j-2] == null) {
				return true;
			    }
			}
		    }
		}
		if (board[i][j].getType() == 4) {
		    if (j<6) {
		    // move down left
			if (i>1) {
			    if (board[i-1][j+1] != null) {
				if (board[i-1][j+1].getType() < 3) {
				    if (board[i-2][j+2] == null) {
					return true;
				    }
				}
			    }
			// move down right
			} else if (i<6) {
			    if (board[i+1][j+1] != null) {
				if (board[i+1][j+1].getType() < 3) {
				    if (board[i+2][j+2] == null) {
					return true;
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return false;
    }

    public static int moveDownLeft(int i, int j) {
	int yes = 0;
 	if (board[i-1][j+1] == null) {
	    // move piece here
	    moveList.add(i + "," + j + " " + (i-1) + "," + (j+1));
	    board[i-1][j+1] = board[i][j];
	    board[i][j] = null;
	    // CHECK IF BECOME KING
	    becomesKing(i-1,j+1);
	    printBoard();
	    // reset board
	    board[i][j] = board[i-1][j+1];
	    board[i-1][j+1] = null;
	    yes = 1;
	}
	return yes;
    }

    public static int moveDownRight(int i, int j) {
	int yes = 0;
	if (board[i+1][j+1] == null) {
	    // move piece here
	    moveList.add(i + "," + j + " " + (i+1) + "," + (j+1));
	    board[i+1][j+1] = board[i][j];
	    board[i][j] = null;
	    // CHECK IF BECOMES KING
	    becomesKing(i+1,j+1);
	    printBoard();
	    // reset board
	    board[i][j] = board[i+1][j+1];
	    board[i+1][j+1] = null;
	    yes = 1;
	}
	return yes;
    }

    public static int moveUpLeft(int i, int j) {
	int yes = 0;
	if (board[i-1][j-1] == null) {
	    // move piece here
	    moveList.add(i + "," + j + " " + (i-1) + "," + (j-1));
	    board[i-1][j-1] = board[i][j];
	    board[i][j] = null;
	    // CHECK IF BECOMES KING
	    becomesKing(i-1,j-1);
	    printBoard();
	    // reset board
	    board[i][j] = board[i-1][j-1];
	    board[i-1][j-1] = null;
	    yes = 1;
	}
	return yes;
    }

    public static int moveUpRight(int i, int j) {
	int yes = 0;
	if (board[i+1][j-1] == null) {
	    // move piece here
	    moveList.add(i + "," + j + " " + (i+1) + "," + (j-1));
	    board[i+1][j-1] = board[i][j];
	    board[i][j] = null;
	    // CHECK IF BECOMES KING
	    becomesKing(i+1,j-1);
	    printBoard();
	    // reset board
	    board[i][j] = board[i+1][j-1];
	    board[i+1][j-1] = null;
	    yes = 1;
	}
	return yes;
    }

    public static void becomesKing(int i, int j) {
	if (board[i][j] != null) {
	    if (board[i][j].getType() == 1) {
		if (j==7) {
		    board[i][j].setType(2);
		}
	    } else if (board[i][j].getType() == 3) {
		if (j==0) {
		    board[i][j].setType(4);
		}
	    }
	}
    }


    public static void checkJump(int i, int j, String moves) {
	//System.out.println("CHECKJUMP");
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
				    moves += i + "," + j + " " + (i-2) + "," + (j+2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i - 2, j + 2, nlist);

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
				    moves += i + "," + j + " " + (i+2) + "," + (j+2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i + 2, j + 2, nlist);

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
				    moves += i + "," + j + " " + (i-2) + "," + (j+2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i - 2, j + 2, nlist);

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
				    moves += i + "," + j + " " + (i+2) + "," + (j+2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i + 2, j + 2, nlist);

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
				    moves += i + "," + j + " " + (i-2) + "," + (j-2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i - 2, j - 2, nlist);

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
				    moves += i + "," + j + " " + (i+2) + "," + (j-2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i + 2, j - 2, nlist);

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
				    moves += i + "," + j + " " + (i-2) + "," + (j-2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i - 2, j - 2, nlist);

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
				    moves += i + "," + j + " " + (i+2) + "," + (j-2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i + 2, j - 2, nlist);

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
				    moves += i + "," + j + " " + (i-2) + "," + (j+2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i - 2, j + 2, nlist);

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
				    moves += i + "," + j + " " + (i+2) + "," + (j+2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i + 2, j + 2, nlist);

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
				    moves += i + "," + j + " " + (i-2) + "," + (j-2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i - 2, j - 2, nlist);

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
				    moves += i + "," + j + " " + (i+2) + "," + (j-2) + " ";
				    String nlist = new String(moves);
                                    checkJump(i + 2, j - 2, nlist);

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
	    moveList.add(moves);
            printBoard();
        }
    }

}