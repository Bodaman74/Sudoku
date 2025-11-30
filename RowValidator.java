package sudoku;

import java.util.ArrayList;
import java.util.List;

public class RowValidator implements Validator {
    private final int[][] board;
    private final int rowIndex;

    public RowValidator(int[][] board, int rowIndex) {
        this.board = board;
        this.rowIndex = rowIndex;
    }

    @Override
    public ValidationResult validate() {
        ValidationResult result = new ValidationResult();
        int[] counts = new int[10]; // 1..9

        // counts
        for (int col = 0; col < 9; col++) {
            int val = board[rowIndex][col];
            if (val >= 1 && val <= 9)
                counts[val]++;
        }

        
        for (int val = 1; val <= 9; val++) {
            if (counts[val] > 1) {
                List<Integer> positions = new ArrayList<>();
                for (int col = 0; col < 9; col++) {
                    if (board[rowIndex][col] == val) positions.add(col + 1);
                }
                result.addInvalidRow(rowIndex + 1,
                        String.format("ROW %d, #%d, %s", rowIndex + 1, val, positions));
            }
        }

        return result;
    }
}
