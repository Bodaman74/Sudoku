package sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowValidator implements Validator {
    private final int[][] board;
    private final int rowIndex;

    public RowValidator(int[][] board, int rowIndex) {
        this.board = board;
        this.rowIndex = rowIndex;
    }

    public ValidationResult validate() {
        int[] count = new int[10];
        List<List<Integer>> pos = new ArrayList<List<Integer>>();
        for (int i = 0; i <= 9; i++) pos.add(new ArrayList<Integer>());

        for (int c = 0; c < 9; c++) {
            int v = board[rowIndex][c];
            count[v]++;
            pos.get(v).add(c + 1);
        }

        boolean valid = true;
        Map<Integer, List<Integer>> dup = new HashMap<Integer, List<Integer>>();
        for (int v = 1; v <= 9; v++) {
            if (count[v] > 1) {
                valid = false;
                dup.put(v, pos.get(v));
            }
        }

        return new ValidationResult("ROW", rowIndex + 1, valid, dup);
    }
}
