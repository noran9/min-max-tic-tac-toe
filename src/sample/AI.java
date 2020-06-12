package sample;

public class AI{

    //Recursive minimax function
    public State limitedMinimax(int [][] current, int player, int depth, int alpha, int beta){
        State minMax;

        //Check if it's the end of the tree or depth is 0
        if(isLeaf(current) || depth == 0) {
            return new State(getCost(current), current);
        }
        int value;

        //player 1 is MAX player
        if(player == 1) value = -1000;
        else  value = 1000;

        //Declare the move
        int [][] move = new int[3][3];

        //Generate all possible moves
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(current[i][j] == 0){

                    //Copy current state in generated
                    int[][] generated = new int[3][3];
                    for(int m = 0; m < 3; m++){
                        for(int n = 0; n < 3; n++)
                            generated[m][n] = current[m][n];
                    }
                    //Execute move in generated
                    generated[i][j] = player;

                    //Recursive call for other player
                    int nextPlayer = (player == 1 ? 2 : 1);
                    minMax = limitedMinimax(generated, nextPlayer, depth - 1, -beta, -alpha);

                    //Choose the move if the value of it is better than this state for either player
                    if(((player == 1) && (minMax.value > value)) || ((player == 2) && (minMax.value < value))){
                        value = minMax.value;
                        move = generated;

                        //Alpha - Beta pruning
                        if(value > alpha)
                            alpha = value;

                        //Cut nodes where alpha is bigger or equal to beta
                        if(alpha >= beta)
                            break;

                        //Copy generated into current move
                        for(int m = 0; m < 3; m++){
                            for(int n = 0; n < 3; n++)
                                move[m][n] = generated[m][n];
                        }
                    }
                }
            }
        }

        //Return new move
        return new State(value, move);
    }

    //Calculate heuristics
    public int getCost(int[][] state){
        if(hasEnded(state) == 1)
            return 100;
        if(hasEnded(state) == 2)
            return -100;
        return 0;
        //return openRows(state, 1) + openColumns(state, 1) + openDiagonals(state, 1) - openRows(state, 2) - openColumns(state, 2) - openDiagonals(state, 2);
    }

    public int openRows(int[][] state, int player){
        int num = 3;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++)
                if(state[i][j] != 0 && state[i][j] != player) {
                    num--;
                    break;
                }
        }
        return num;
    }

    public int openColumns(int[][] state, int player){
        int num = 3;
        for(int j = 0; j < 3; j++){
            for(int i = 0; i < 3; i++)
                if(state[i][j] != 0 && state[i][j] != player) {
                    num--;
                    break;
                }
        }
        return num;
    }

    public int openDiagonals(int[][] state, int player){
        int num = 2;
        if(state[0][0] != 0 && state[0][0] != player && state[1][1] != 0 && state[1][1] != player && state[2][2] != 0 && state[2][2] != player)
            num--;

        if(state[0][2] != 0 && state[0][2] != player && state[1][1] != 0 && state[1][1] != player && state[2][0] != 0 && state[2][0] != player)
            num--;

        return num;
    }

    //Check if the move is a leaf
    public boolean isLeaf(int [][] move){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(move[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    //Check for victory
    public int hasEnded(int[][] state){
        for(int i = 0; i < 3; i++){
            if(state[0][i] == 1 && state[1][i] == 1 && state[2][i] == 1)
                return 1;

            if(state[0][i] == 2 && state[1][i] == 2 && state[2][i] == 2)
                return 2;
        }

        for(int i = 0; i < 3; i++){
            if(state[i][0] == 1 && state[i][1] == 1 && state[i][2] == 1)
                return 1;
            if(state[i][0] == 2 && state[i][1] == 2 && state[i][2] == 2)
                return 2;
        }

        if(state[0][0] == 1 && state[1][1] == 1 && state[2][2] == 1)
            return 1;
        if(state[0][2] == 1 && state[1][1] == 1 && state[2][0] == 1)
            return 1;

        if(state[0][0] == 2 && state[1][1] == 2 && state[2][2] == 2)
            return 2;
        if(state[0][2] == 2 && state[1][1] == 2 && state[2][0] == 2)
            return 2;

        return 0;
    }
}
