// Iris Larsen
// Sara J. Johnson
// Tim Woods
// Wyatt Chapman
import java.util.*;

public class Game {

    Checker[] board = new Checker[10][10];
    public static void main(String args[]) {

    }

    public static void initBoard() {
        int ID = 0;
        int i;
        int j;
        for (i = 0; i < 4; i++) {
            for(j = 0; j < 10; j++) {
                if ((i & 1) ^ (j & 1) == 1) {
                    board[i][j] = new Checker(ID, 'b', 'p', i, j);
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
                    board[i][j] = new Checker(ID, 'r', 'p', i, j);
                    ID++;
                } else {
                    board[i][j] = NULL;
                }
            }
        }
        return;

    }

}
