package sudoku;

import java.util.List;

public class ColumnThread extends Thread {
    private ColumnValidator validator;
    private List<ValidationResult> out;

    public ColumnThread(int[][] board, int col, List<ValidationResult> out) {
        validator = new ColumnValidator(board, col);
        this.out = out;
    }

    public void run() {
        out.add(validator.validate());
    }
}
