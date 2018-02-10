// Iris Larsen
// Sara J. Johnson
// Tim Woods
// Wyatt Chapman

public class Checker {
    int ID;
    int i;
    int j;
    char color;
    char type;

    public Checker(int i, char c, char t, int i, int j) {
        this.ID = i;
        this.color = c;
        this.type = t;
        this.i = i;
        this.j = j;
    }

    public int getID() {
        return this.ID;
    }

    public char getColor() {
        return this.color;
    }

    public char getType() {
        return this.type;
    }

    public void setType(char newt) {
        this.type = newt;
    }

    public int geti() {
        return this.i;
    }

    public int getj() {
        return this.j;
    }

    public void setLocation(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
