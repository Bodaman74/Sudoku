package sudoku;

import java.util.*;

public class ColumnValidationRunner {
    public static List<ValidationResult> run(int[][] board, int mode) throws InterruptedException {
        List<ValidationResult> res = Collections.synchronizedList(new ArrayList<ValidationResult>());

        if (mode == 0) {
            for (int c = 0; c < 9; c++) res.add(new ColumnValidator(board, c).validate());
        }
        else if (mode == 3) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    for (int c = 0; c < 9; c++) res.add(new ColumnValidator(board, c).validate());
                }
            });
            t.start();
            t.join();
        }
        else if (mode == 27) {
            List<Thread> threads = new ArrayList<Thread>();
            for (int c = 0; c < 9; c++) {
                Thread th = new ColumnThread(board, c, res);
                threads.add(th);
                th.start();
            }
            for (int i = 0; i < threads.size(); i++) threads.get(i).join();
        }

        Collections.sort(res, new Comparator<ValidationResult>() {
            public int compare(ValidationResult a, ValidationResult b) {
                return a.getUnitIndex() - b.getUnitIndex();
            }
        });

        return res;
    }
}
