package sudoku;

public class ColumnValidatorFactory {
    public Validator createSingle(int[][] board, int col) {
        return new ColumnValidator(board, col);
    }
}
