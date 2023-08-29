import java.util.ArrayList;

public class Board {

    public enum Player {
        X,
        O;

        public Player other() {
            if (this == Player.X) {
                return Player.O;
            }
            return Player.X;
        }
    }

    public static class MoveException extends Exception {

        private String message;
        public MoveException(int col, Player player) {
            message = player.name() + " - Column: " + col + " is not a valid position.";
        }

        public String getMessage() {
            return message;
        }
    }

    private static ArrayList<Board> winningBoards = new ArrayList<Board>();
    private static final int ROWS = 6;
    private static final int COLS = 7;

    private static final int WIN = 4;

    private static final int X = 1;
    private static final int O = -1;

    private int[] board;

    public Board() {
        board = new int[ROWS * COLS];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = 0;
        }
    }

    public Board(Board board) {
        this.board = new int[board.board.length];
        for (int i = 0; i < board.board.length; i++) {
            this.board[i] = board.board[i];
        }
    }

    public int heuristic() {
        int heuristic = 0;
        for (int i = 1; i < WIN - 1; i++) {
            heuristic -= boardPatterns(i, Player.X);
            heuristic += boardPatterns(i, Player.O);
        }
        return heuristic;
    }

    public boolean marked(int position) {
        return this.board[position] != 0;
    }

    public int getPosition(int row, int col) {
        return (row - 1) * COLS + (col - 1);
    }

    public int getRow(int position) {
        return position/COLS + 1;
    }

    public int getCol(int position) {
        return (position -  (getRow(position) - 1) * COLS) + 1;
    }

    public Board move(int col, Player player) throws MoveException {
        Board newBoard = new Board(this);
        int row = isOpen(col);
        if (row != -1 && (col >= 1 && col <= COLS )) {
            if (player == Player.X) {
                newBoard.board[getPosition(row, col)] = X;
            } else {
                newBoard.board[getPosition(row, col)] = O;
            }
        } else {
            throw new MoveException(col, player);
        }
        return newBoard;
    }

    //Row and Col are start 1,1
    //position is start 0

    public int isOpen(int col) {
        for (int i = ROWS; i > 0; i--) {
            if (this.isEmpty(i, col)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty(int row, int col) {
        return this.board[getPosition(row, col)] == 0;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            if (i%COLS == 0) {
                System.out.println();
            }
            String value = this.board[i] == X ? "X" : this.board[i] == O ? "O" : ".";
            System.out.print(value);
        }
        System.out.println();
    }

    public void printBoardArray() {
        String output = "[";
        for (int val : this.board) {
            output += val + ", ";
        }
        output = output.substring(0, output.length() - 2) + "]";
        System.out.println(output);
    }

    public int boardPatterns(int number, Board.Player player) {
        int value = player == Player.X ? X : O;
        int heuristic = 0;
        for (int i = 0; i < COLS * ROWS; i++) {
            int count = 0;
            if (i%7 <= (COLS - WIN)) {
                for (int x = 0; x < number; x++) {
                    if (this.board[i + x] == value) {
                        count++;
                    } else {
                        break;
                    }
                }

                if (count == number) {
                    heuristic += number;
                }
            }
            count = 0;
            if (i/7 <= (ROWS - WIN)) {
                for (int x = 0; x < number; x++) {
                    if (this.board[i + x*7] == value) {
                        count++;
                    } else {
                        break;
                    }
                }

                if (count == number) {
                    heuristic += number;
                }

                count = 0;

                if (i%7 <= (COLS - WIN)) {
                    for (int x = 0; x < number; x++) {
                        if (this.board[i + x*8] == value) {
                            count++;
                        } else {
                            break;
                        }
                    }

                    if (count == number) {
                        heuristic += number;
                    }
                }

                count = 0;

                if (i%7 >= (COLS - WIN)) {
                    for (int x = 0; x < number; x++) {
                        if (this.board[i + x*6] == value) {
                            count++;
                        } else {
                            break;
                        }
                    }

                    if (count == number) {
                        heuristic += number;
                    }
                }
            }
        }
        return heuristic;
    }

    public ArrayList<Integer> getNextMoves() {
        ArrayList<Integer> moves = new ArrayList<Integer>();
        for (int i = 0; i < COLS; i++) {
            if (this.isOpen(i + 1) != -1) {
                moves.add(i + 1);
            }
        }
        return moves;
    }

    public boolean isDraw() {
        for (int i = 0; i < COLS * ROWS; i++) {
            if (this.board[i] == 0) {
                return false;
            }
        }

        return !win(Board.Player.X) && !win(Board.Player.O);
    }

    public boolean win(Player player) {
        int value = 0;

        if (player == Player.X) {
            value = X;
        } else {
            value = O;
        }

        for (int i = 0; i < COLS * ROWS; i++) {
            if (i%7 <= (COLS - WIN)) {
                if (this.board[i] == value && this.board[i + 1] == value && this.board[i + 2] == value && this.board[i + 3] == value) {
                    return true;
                }
            }

            if (i/7 <= (ROWS - WIN)) {
                if (this.board[i] == value && this.board[i + 7] == value && this.board[i + 7*2] == value && this.board[i + 7*3] == value) {
                    return true;
                }

                if (i%7 <= (COLS - WIN)) {
                    if (this.board[i] == value && this.board[i + 8] == value && this.board[i + 8*2] == value && this.board[i + 8*3] == value) {
                        return true;
                    }
                }

                if (i%7 >= (COLS - WIN)) {
                    if (this.board[i] == value && this.board[i + 6] == value && this.board[i + 6*2] == value && this.board[i + 6*3] == value) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
