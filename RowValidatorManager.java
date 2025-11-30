/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author DELL
 */
public class RowValidatorManager {

    private final int[][] board;

    public RowValidatorManager(int[][] board) {
        this.board = board;
    }

    public ValidationResult validateMode0() {
        ValidationResult result = new ValidationResult();

        for (int r = 0; r < 9; r++) {
            RowValidator validator = new RowValidator(board, r);
            ValidationResult rowResult = validator.validate();

            result.merge(rowResult);
        }

        return result;
    }

    public ValidationResult validateMode3() throws InterruptedException {
        return validateWithThreadRanges(new int[][]{
                {0, 2}, {3, 5}, {6, 8}
        });
    }

    public ValidationResult validateMode27() throws InterruptedException {
        int[][] ranges = new int[9][2];
        for (int i = 0; i < 9; i++) ranges[i] = new int[]{i, i};
        return validateWithThreadRanges(ranges);
    }

    private ValidationResult validateWithThreadRanges(int[][] ranges)
            throws InterruptedException {

        ValidationResult result = new ValidationResult();
        ConcurrentLinkedQueue<ValidationResult> partialResults = new ConcurrentLinkedQueue<>();

        List<Thread> threads = new ArrayList<>();

        for (int[] range : ranges) {
            Thread t = new Thread(() -> {
                for (int r = range[0]; r <= range[1]; r++) {
                    RowValidator validator = new RowValidator(board, r);
                    partialResults.add(validator.validate());
                }
            });
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) t.join();

        for (ValidationResult r : partialResults)
            result.merge(r);

        return result;
    }
}
