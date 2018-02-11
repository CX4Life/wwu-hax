// Iris Larsen
// Sara J. Johnson
// Tim Woods
// Wyatt Chapman
import java.util.*;
import java.io.*;

public class Game {

    static Checker[][] board = new Checker[8][8];
    static ArrayList<String> moveList = new ArrayList<String>();

    public static void main(String args[]) {

        //true: black's turn
        //false: red's turn
        //black goes first
        boolean bTurn = true;

        File gameLog;
        FileWriter fw;
        BufferedWriter bw = null;

        if (args.length < 1) {
            System.out.println("Please input log file name.");
            return;
        }
        try {
            gameLog = new File(args[0]);
            if (!gameLog.exists()) {
                gameLog.createNewFile();
            }
            fw = new FileWriter(gameLog);
            bw = new BufferedWriter(fw);
        } catch (Exception ex) {
            System.out.println("File IO issue.\n");
            System.exit(1);
        }

        //true until game over
        boolean gameOn = true;
	int totalMoves = 0;

        initBoard();
        while (gameOn) {
            if (hasPieces(bTurn) && (totalMoves < 50)) {
                moveList.clear();
                if (possibleStates(bTurn)) {
                    System.out.println("?");
                    // move function here
                    try {
                        if (attemptMove(bw) == true) {
			    totalMoves = 0;
			} else {
			    totalMoves++;
			}
                    } catch (Exception ex) {
                        System.out.println("File IO issue.\n");
                        System.out.println(ex);
                        return;
                    }
                    //System.exit(0);
                    bTurn = !bTurn;
                } else {
                    gameOn = false;
                }
            } else {
                gameOn = false;
            }
        }
	printWinner();
        return;
    }

    public static boolean attemptMove(BufferedWriter bw) throws IOException {
	boolean capture = false;
	Scanner scan = new Scanner(System.in);
	String stuff = scan.nextLine();
	if (stuff.equals("e")) {
	    bw.close();
	    System.exit(1);
	}
	int input = Integer.parseInt(stuff);
	String move = moveList.get(input);
	String[] series = move.split(" ");

	int index1;
	int index2;
	int index3;
	int index4;

	for (int i=0; i<series.length-1; i+=2) {
	    index1 = Integer.parseInt(series[i].charAt(0)+"");
	    index2 = Integer.parseInt(series[i].charAt(2)+"");
	    index3 = Integer.parseInt(series[i+1].charAt(0)+"");
	    index4 = Integer.parseInt(series[i+1].charAt(2)+"");
	    int id = board[index1][index2].getID();

	    bw.write(board[index1][index2].getID() + " " + index3 + "," + index4);
	    bw.newLine();

	    board[index3][index4] = board[index1][index2];
	    board[index1][index2] = null;

	    // becomes king
	    becomesKing(index3, index4);

	    // check for a jump
	    if (((index3 - index1) > 1) || ((index3 - index1) < -1)) {
		// up left
		if (((index1 - index3) > 0) && (((index2 - index4) > 0))) {
		    //System.out.println("remove " + board[index1-1][index2-1].getID());
		    bw.write("remove " + board[index1-1][index2-1].getID());
		    bw.newLine();
		    board[index1-1][index2-1] = null;
		    capture = true;
		// up right
		} else if (((index1 - index3) < 0) && (((index2 - index4)) > 0)) {
		    //System.out.println("remove " + board[index1+1][index2-1].getID());
		    bw.write("remove " + board[index1+1][index2-1].getID());
		    bw.newLine();
		    board[index1+1][index2-1] = null;
		    capture = true;
		// down left
		} else if (((index1 - index3) > 0) && (((index2 - index4)) < 0)) {
		    //System.out.println("remove " + board[index1-1][index2+1].getID());
		    bw.write("remove " + board[index1-1][index2+1].getID());
		    bw.newLine();
		    board[index1-1][index2+1] = null;
		    capture = true;
		// down right
		} else {
		    //System.out.println("remove " + board[index1+1][index2+1].getID());
		    bw.write("remove " + board[index1+1][index2+1].getID());
		    bw.newLine();
		    board[index1+1][index2+1] = null;
		    capture = true;
		}
	    }
	}
	return capture;
    }

    public static void printWinner() {
	boolean bTurn = true;
	if (!hasPieces(bTurn)) {
	    // red wins
	    for (int i=0; i<8; i++) {
		System.out.println("R");
	    }
	    System.out.println();
	} else if (!hasPieces(!bTurn)) {
	    // black wins
	    for (int i=0; i<8; i++) {
		System.out.println("B");
	    }
	    System.out.println();
	} else {
	    // stalemate
	    for (int i=0; i<8; i++) {
		System.out.println("S");
	    }
	}
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
			    if (board[i+1][j-1].getType() > 2) {
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
	boolean kingChange = false;
 	if (board[i-1][j+1] == null) {
	    // move piece here
	    moveList.add(i + "," + j + " " + (i-1) + "," + (j+1));
	    board[i-1][j+1] = board[i][j];
	    board[i][j] = null;
	    // CHECK IF BECOME KING
	    kingChange = becomesKing(i-1,j+1);
	    printBoard();
	    // reset board
	    board[i][j] = board[i-1][j+1];
	    board[i-1][j+1] = null;
	    if (kingChange == true) {
		board[i][j].setType(board[i][j].getType()-1);
	    }
	    yes = 1;
	}
	return yes;
    }

    public static int moveDownRight(int i, int j) {
	int yes = 0;
	boolean kingChange = false;
	if (board[i+1][j+1] == null) {
	    // move piece here
	    moveList.add(i + "," + j + " " + (i+1) + "," + (j+1));
	    board[i+1][j+1] = board[i][j];
	    board[i][j] = null;
	    // CHECK IF BECOMES KING
	    kingChange = becomesKing(i+1,j+1);
	    printBoard();
	    // reset board
	    board[i][j] = board[i+1][j+1];
	    board[i+1][j+1] = null;
	    if (kingChange == true) {
		board[i][j].setType(board[i][j].getType()-1);
	    }
	    yes = 1;
	}
	return yes;
    }

    public static int moveUpLeft(int i, int j) {
	int yes = 0;
	boolean kingChange = false;
	if (board[i-1][j-1] == null) {
	    // move piece here
	    moveList.add(i + "," + j + " " + (i-1) + "," + (j-1));
	    board[i-1][j-1] = board[i][j];
	    board[i][j] = null;
	    // CHECK IF BECOMES KING
	    kingChange = becomesKing(i-1,j-1);
	    printBoard();
	    // reset board
	    board[i][j] = board[i-1][j-1];
	    board[i-1][j-1] = null;
	    if (kingChange == true) {
		board[i][j].setType(board[i][j].getType()-1);
	    }
	    yes = 1;
	}
	return yes;
    }

    public static int moveUpRight(int i, int j) {
	int yes = 0;
	boolean kingChange = false;
	if (board[i+1][j-1] == null) {
	    // move piece here
	    moveList.add(i + "," + j + " " + (i+1) + "," + (j-1));
	    board[i+1][j-1] = board[i][j];
	    board[i][j] = null;
	    // CHECK IF BECOMES KING
	    kingChange = becomesKing(i+1,j-1);
	    printBoard();
	    // reset board
	    board[i][j] = board[i+1][j-1];
	    board[i+1][j-1] = null;
	    if (kingChange == true) {
		board[i][j].setType(board[i][j].getType()-1);
	    }
	    yes = 1;
	}
	return yes;
    }

    public static boolean becomesKing(int i, int j) {
	if (board[i][j] != null) {
	    if (board[i][j].getType() == 1) {
		if (j==7) {
		    board[i][j].setType(2);
		    return true;
		}
	    } else if (board[i][j].getType() == 3) {
		if (j==0) {
		    board[i][j].setType(4);
		    return true;
		}
	    }
	}
	return false;
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
