package sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColumnValidator implements Validator {
    private final int[][] board;
    private final int colIndex;

    public ColumnValidator(int[][] board, int colIndex) {
        this.board = board;
        this.colIndex = colIndex;
    }

    public ValidationResult validate() {
        int[] count = new int[10];
        List<List<Integer>> pos = new ArrayList<List<Integer>>();
        for (int i = 0; i <= 9; i++) pos.add(new ArrayList<Integer>());

        for (int r = 0; r < 9; r++) {
            int v = board[r][colIndex];
            count[v]++;
            pos.get(v).add(r + 1);
        }

        boolean valid = true;
        Map<Integer, List<Integer>> dup = new HashMap<Integer, List<Integer>>();
        for (int v = 1; v <= 9; v++) {
            if (count[v] > 1) {
                valid = false;
                dup.put(v, pos.get(v));
            }
        }

        return new ValidationResult("COL", colIndex + 1, valid, dup);
    }
}
