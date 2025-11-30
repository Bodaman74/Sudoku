package sudoku;
import java.util.*;
import java.util.concurrent.*;

public class RowValidator {
    private final int[][] board; 

    public RowValidator(int[][] board) {
        if (board == null || board.length != 9 || board[0].length != 9) {
            throw new IllegalArgumentException("Board must be 9x9");
        }
        this.board = board;
    }

    //  Mode 0: 
    public List<String> checkRowReason(int rowIndex) {
        Map<Integer, List<Integer>> duplicates = new HashMap<>();
        int[] counts = new int[10]; // 1..9

        for (int col = 0; col < 9; col++) {
            int val = board[rowIndex][col];
            counts[val]++;
        }

        for (int val = 1; val <= 9; val++) {
            if (counts[val] > 1) {
                List<Integer> positions = new ArrayList<>();
                for (int col = 0; col < 9; col++) {
                    if (board[rowIndex][col] == val) positions.add(col + 1);
                }
                duplicates.put(val, positions);
            }
        }

        List<String> reasonsList = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : duplicates.entrySet()) {
            reasonsList.add(String.format("ROW %d, #%d, %s",
                    rowIndex + 1, entry.getKey(), entry.getValue()));
        }

        return reasonsList;
    }

    // Mode 0: 
    public ValidationResult validateMode0() {
        ValidationResult result = new ValidationResult();

        for (int r = 0; r < 9; r++) {
            List<String> reasonsList = checkRowReason(r);
            for (String reason : reasonsList) {
                result.addInvalidRow(r, reason);
            }
        }

        return result;
    }

    // Mode 3
    public ValidationResult validateMode3() throws InterruptedException {
        int[][] ranges = { {0,2}, {3,5}, {6,8} };
        return validateWithThreadRanges(ranges);
    }

    //  Mode 27
    public ValidationResult validateMode27() throws InterruptedException {
        int[][] ranges = new int[9][2];
        for (int i = 0; i < 9; i++) ranges[i] = new int[] {i,i};
        return validateWithThreadRanges(ranges);
    }

    // Threads 
    private ValidationResult validateWithThreadRanges(int[][] ranges) throws InterruptedException {
        ValidationResult result = new ValidationResult();
        ConcurrentLinkedQueue<Integer> invalidRows = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> reasons = new ConcurrentLinkedQueue<>();

        List<Thread> threads = new ArrayList<>();
        for (int[] r : ranges) {
            RowThread worker = new RowThread(board, r[0], r[1], invalidRows, reasons);
            Thread t = new Thread(worker);
            threads.add(t);
            t.start();
        }

        // wait all threads to finish
        for (Thread t : threads) t.join();

        
        List<Integer> rows = new ArrayList<>(invalidRows);
        Collections.sort(rows);
        for (int rIndex : rows) {
            for (String s : reasons) {
                if (s.startsWith("ROW " + (rIndex+1) + ",")) {
                    result.addInvalidRow(rIndex, s);
                }
            }
        }

        return result;
    }

  
}
