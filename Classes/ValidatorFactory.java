package sudoku;

public final class ValidatorFactory {
    private ValidatorFactory() {}

    public static Validator createRow(int[][] board, int index) {
        return new RowValidator(board, index);
    }

    public static Validator createColumn(int[][] board, int index) {
        return new ColumnValidator(board, index);
    }

    public static Validator createBox(int[][] board, int index) {
        return new BoxValidator(board, index);
    }
}
