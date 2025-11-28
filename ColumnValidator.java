package sudoku;

import java.util.*;

public class ColumnValidator implements Validator {
    private int[][] board;
    private int col;

    public ColumnValidator(int[][] board, int col) {
        this.board = board;
        this.col = col;
    }

    public ValidationResult validate() {
        Map<Integer, List<Integer>> dup = new HashMap<>();
        int[] count = new int[10];
        List<List<Integer>> pos = new ArrayList<>();
        for (int i = 0; i <= 9; i++) pos.add(new ArrayList<>());

        for (int r = 0; r < 9; r++) {
            int val = board[r][col];
            if (val >= 1 && val <= 9) {
                count[val]++;
                pos.get(val).add(r + 1);
            }
        }

        boolean valid = true;
        for (int i = 1; i <= 9; i++) {
            if (count[i] > 1) {
                valid = false;
                dup.put(i, pos.get(i));
            }
        }

        return new ValidationResult("COL", col + 1, valid, dup);
    }
}
