package sudoku;

import java.util.*;
import java.util.concurrent.*;

public class BoxValidator {

    private final int[][] board;

    public BoxValidator(int[][] board) {
        this.board = board;
    }

    // --------- Mode 0 ---------
    public List<String> checkBox(int boxIndex) {
        Map<Integer, List<String>> duplicates = new HashMap<>();
        int[] count = new int[10];

        int rStart = (boxIndex / 3) * 3;
        int cStart = (boxIndex % 3) * 3;

        for (int r = rStart; r < rStart + 3; r++) {
            for (int c = cStart; c < cStart + 3; c++) {
                int v = board[r][c];
                count[v]++;
            }
        }

        for (int v = 1; v <= 9; v++) {
            if (count[v] > 1) {
                List<String> pos = new ArrayList<>();
                for (int r = rStart; r < rStart + 3; r++) {
                    for (int c = cStart; c < cStart + 3; c++) {
                        if (board[r][c] == v) {
                            pos.add("(" + (r+1) + "," + (c+1) + ")");
                        }
                    }
                }
                duplicates.put(v, pos);
            }
        }

        List<String> out = new ArrayList<>();
        for (Map.Entry<Integer,List<String>> e : duplicates.entrySet()) {
            out.add(String.format("BOX %d, #%d, %s",
                    boxIndex + 1, e.getKey(), e.getValue()));
        }

        return out;
    }

    public ValidationResult validateMode0() {
        ValidationResult vr = new ValidationResult();
        for (int box = 0; box < 9; box++) {
            List<String> list = checkBox(box);
            for (String s : list) {
                vr.addInvalidBox(box, s);
            }
        }
        return vr;
    }

    // --------- Mode 3 ---------
    public ValidationResult validateMode3() throws InterruptedException {
        int[][] ranges = { {0,2}, {3,5}, {6,8} };
        return runThreads(ranges);
    }

    // --------- Mode 27 ---------
    public ValidationResult validateMode27() throws InterruptedException {
        int[][] ranges = new int[9][2];
        for (int i = 0; i < 9; i++) ranges[i] = new int[]{i,i};
        return runThreads(ranges);
    }

    // --------- Generic threaded validator ---------
    private ValidationResult runThreads(int[][] ranges) throws InterruptedException {

        ValidationResult vr = new ValidationResult();

        ConcurrentLinkedQueue<Integer> invalidBoxes = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> reasons = new ConcurrentLinkedQueue<>();

        List<Thread> list = new ArrayList<>();

        for (int[] r : ranges) {
            BoxThread bt = new BoxThread(board, r[0], r[1], invalidBoxes, reasons);
            Thread t = new Thread(bt);
            t.start();
            list.add(t);
        }

        for (Thread t : list) t.join();

        List<Integer> sorted = new ArrayList<>(invalidBoxes);
        Collections.sort(sorted);

        for (int b : sorted) {
            for (String s : reasons) {
                if (s.startsWith("BOX " + (b+1))) {
                    vr.addInvalidBox(b, s);
                }
            }
        }
        return vr;
    }
}
