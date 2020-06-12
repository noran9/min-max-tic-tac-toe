package sample;

public class State {
    int value;
    int [][] move;

    public State(int value, int[][] move) {
        this.value = value;
        this.move = move;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int[][] getMove() {
        return move;
    }

    public void setMove(int[][] move) {
        this.move = move;
    }

}
