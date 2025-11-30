package sudoku;

import java.util.*;

public class RowValidator implements Validator {
    private int[][] grid;
    private int rowIndex;

    // Constructor with parameters
    public RowValidator(int[][] grid, int rowIndex) {
        this.grid = grid;
        this.rowIndex = rowIndex;
    }

   @Override
public ValidationResult validate() {
    ValidationResult result = new ValidationResult();

    int row = this.rowIndex;  // فقط الصف المخصص لهذه الـ instance
    Map<Integer, List<Integer>> occurrences = new HashMap<>();
    boolean added = false;

    for (int col = 0; col < 9; col++) {
        int val = grid[row][col];
        if (val < 1 || val > 9) continue;

        occurrences.putIfAbsent(val, new ArrayList<>());
        occurrences.get(val).add(col + 1); // 1-based index
    }

    for (Map.Entry<Integer, List<Integer>> entry : occurrences.entrySet()) {
        if (!added && entry.getValue().size() > 1) {
            result.addInvalidRow(row + 1,
                    "ROW " + (row + 1) + ", #" + entry.getKey() + ", " +
                    entry.getValue());
            added = true;
            break;
        }
    }

    return result;
}


}
