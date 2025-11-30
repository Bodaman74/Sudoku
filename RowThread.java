package sudoku;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RowThread implements Runnable {
    private final int[][] board;
    private final int start;
    private final int end;
    private final ConcurrentLinkedQueue<Integer> invalidRows;
    private final ConcurrentLinkedQueue<String> reasons;

    public RowThread(int[][] board, int start, int end,
                     ConcurrentLinkedQueue<Integer> invalidRows,
                     ConcurrentLinkedQueue<String> reasons) {
        this.board = board;
        this.start = start;
        this.end = end;
        this.invalidRows = invalidRows;
        this.reasons = reasons;
    }

   public void run() {
    for (int r = start; r <= end; r++) {
        RowValidator validator = new RowValidator(board, r);
        ValidationResult rowResult = validator.validate();

        if (!rowResult.allValid()) {
            invalidRows.add(r + 1); 
            
            for (int row : rowResult.getInvalidRows()) {
                reasons.addAll(rowResult.getReasonsForRow(row));
            }
        }
    }
}
}
