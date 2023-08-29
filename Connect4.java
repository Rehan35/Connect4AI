import java.util.Scanner;

public class Connect4 {

    public static int PLIES = 9;

    //X is user and O is cpu
    public static int min(Board currentState, int plies, Board.Player player) throws Board.MoveException {
        if (currentState.win(Board.Player.X)) {
            return Integer.MIN_VALUE;
        } else if (currentState.win(Board.Player.O)) {
            return Integer.MAX_VALUE;
        } else if (currentState.isDraw()) {
            return 0;
        } else if (plies == PLIES) {
            return currentState.heuristic();
        }


        int trueValue = Integer.MAX_VALUE;

        for (int move : currentState.getNextMoves()) {
            Board nextBoard = currentState.move(move, player);
            int value = max(nextBoard, plies + 1, player.other());
            if (value < trueValue) {
                trueValue = value;
            }
        }

        return trueValue;
    }

    public static int max(Board currentState, int plies, Board.Player player) throws Board.MoveException {
        if (currentState.win(Board.Player.X)) {
            return Integer.MIN_VALUE;
        } else if (currentState.win(Board.Player.O)) {
            return Integer.MAX_VALUE;
        } else if (currentState.isDraw()) {
            return 0;
        } else if (plies == PLIES) {
            return currentState.heuristic();
        }


        int trueValue = Integer.MIN_VALUE;

        for (int move : currentState.getNextMoves()) {
            Board nextBoard = currentState.move(move, player);
            int value = min(nextBoard, plies + 1, player.other());
            if (value > trueValue) {
                trueValue = value;
            }
        }

        return trueValue;
    }
    public static int getNextMove(Board currentState) throws Board.MoveException {
        int trueValue = Integer.MIN_VALUE;
        int col = -1;

        for (int move : currentState.getNextMoves()) {
            Board nextBoard = currentState.move(move, Board.Player.O);
            int value = min(nextBoard, 1, Board.Player.X);
            if (value == Integer.MAX_VALUE) {
                return col;
            }
            if (value >= trueValue) {
                trueValue = value;
                col = move;
            }
        }
        return col;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        while (!board.isDraw() && !board.win(Board.Player.X) && !board.win(Board.Player.O)) {
            try {
                System.out.println("CPU is thinking");
                int col = getNextMove(board);
                board = board.move(col, Board.Player.O);
                board.printBoard();
            } catch (Board.MoveException e) {
                System.out.println(e.getMessage());
            }

            if (board.isDraw() || board.win(Board.Player.O)) {
                System.out.println("CPU wins");
                break;
            }

            System.out.println();

            System.out.print("Enter Row: ");
            String colString = scanner.nextLine();

            int column = Integer.valueOf(colString);
            try {
                board = board.move(column, Board.Player.X);
                board.printBoard();
            } catch (Board.MoveException e) {
                System.out.println(e.getMessage());
            }

            System.out.println();

        }
        board.printBoard();
        board.printBoardArray();

//        board.winningBoardConfiguration();

    }

}
