// Iris Larsen
// Sara J. Johnson
// Tim Woods
// Wyatt Chapman
import java.util.*;

public class Game {

    Checker[] board = new Checker[10][10];

    public static void main(String args[]) {
        initBoard();
        System.out.println("Hi! welcome to the game of checkers!");
        printBoard();
        return;

    }


    /*initBoard creates clean game board; sets piece IDs,
     *colors, and starting positions
     */
    public static void initBoard() {
        int ID = 0;
        int i;
        int j;
        for (i = 0; i < 4; i++) {
            for(j = 0; j < 10; j++) {
                if ((i & 1) ^ (j & 1) == 1) {
                    board[i][j] = new Checker(ID, 1, i, j);
                    ID++;
                } else {
                    board[i][j] = NULL;
                }
            }
        }
        for (i = 4; i < 6; i++) {
            for (j = 0; j < 10; j++) {
                board[i][j] = NULL;
            }
        }
        for (i = 6; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                if ((i & 1) ^ (j & 1) == 1) {
                    board[i][j] = new Checker(ID, 3, i, j);
                    ID++;
                } else {
                    board[i][j] = NULL;
                }
            }
        }
        return;
    }

    /*printBoard outputs ASCII representation of game board
     *to the terminal
     */
    public static void printBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == NULL) {
                    System.out.print("0");
                }
                System.out.print(board[i][j].getType());
            }
            System.out.println();
        }
    }

}
