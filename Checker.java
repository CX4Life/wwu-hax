// Iris Larsen
// Sara J. Johnson
// Tim Woods
// Wyatt Chapman

public class Checker {
    private int ID;
    private int i;
    private int j;

    //type:
    //1 = black
    //2 = black king
    //3 = red
    //4 = red king
    private int type;

    public Checker(int ID, int type, int i, int j) {
        this.ID = ID;
        this.type = type;
        this.i = i;
        this.j = j;
    }

    public int getID() {
        return this.ID;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int newt) {
        this.type = newt;
    }

    public int getI() {
        return this.i;
    }

    public int getJ() {
        return this.j;
    }

    public void setLocation(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
