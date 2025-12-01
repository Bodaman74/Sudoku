package sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxValidator implements Validator {
    private final int[][] board;
    private final int boxIndex;

    public BoxValidator(int[][] board, int boxIndex) {
        this.board = board;
        this.boxIndex = boxIndex;
    }

    public ValidationResult validate() {
        int[] count = new int[10];
        List<List<Integer>> pos = new ArrayList<List<Integer>>();
        for (int i = 0; i <= 9; i++) pos.add(new ArrayList<Integer>());

        int rStart = (boxIndex / 3) * 3;
        int cStart = (boxIndex % 3) * 3;

        for (int r = rStart; r < rStart + 3; r++) {
            for (int c = cStart; c < cStart + 3; c++) {
                int v = board[r][c];
                count[v]++;
                int position = (r - rStart) * 3 + (c - cStart) + 1;
                pos.get(v).add(position);
            }
        }

        boolean valid = true;
        Map<Integer, List<Integer>> dup = new HashMap<Integer, List<Integer>>();
        for (int v = 1; v <= 9; v++) {
            if (count[v] > 1) {
                valid = false;
                dup.put(v, pos.get(v));
            }
        }

        return new ValidationResult("BOX", boxIndex + 1, valid, dup);
    }
}
