package sudoku;

public class Board {
    private int[][] grid;

    public Board(int[][] grid) {
        this.grid = grid;
    }

    public int[][] getGrid() {
        return grid;
    }
}
