package sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RowValidatorManager {

    private final int[][] board;

    public RowValidatorManager(int[][] board) {
        this.board = board;
    }

    // Mode 0: sequential
    public ValidationResult validateMode0() {
        ConcurrentLinkedQueue<Integer> invalidRows = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> reasons = new ConcurrentLinkedQueue<>();

        RowThread task = new RowThread(board, 0, 8, invalidRows, reasons);
        task.run(); // sequential

        return buildResult(invalidRows, reasons);
    }

    // Mode 3
    public ValidationResult validateMode3() throws InterruptedException {
        return validateWithThreadRanges(new int[][]{{0, 2}, {3, 5}, {6, 8}});
    }

    // Mode 27
    public ValidationResult validateMode27() throws InterruptedException {
        int[][] ranges = new int[9][2];
        for (int i = 0; i < 9; i++) ranges[i] = new int[]{i, i};
        return validateWithThreadRanges(ranges);
    }

    
    private ValidationResult validateWithThreadRanges(int[][] ranges) throws InterruptedException {
        ConcurrentLinkedQueue<Integer> invalidRows = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> reasons = new ConcurrentLinkedQueue<>();
        List<Thread> threads = new ArrayList<>();

        for (int[] range : ranges) {
            Thread t = new Thread(new RowThread(board, range[0], range[1], invalidRows, reasons));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) t.join();

        return buildResult(invalidRows, reasons);
    }

    private ValidationResult buildResult(ConcurrentLinkedQueue<Integer> invalidRows,
                                         ConcurrentLinkedQueue<String> reasons) {
        ValidationResult result = new ValidationResult();
        for (String reason : reasons) {
            result.addInvalidRow(-1, reason);
        }
        return result;
    }
}
