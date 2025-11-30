package sudoku;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BoxThread implements Runnable {

    private final int[][] board;
    private final int start;
    private final int end;
    private final ConcurrentLinkedQueue<Integer> invalidBoxes;
    private final ConcurrentLinkedQueue<String> reasons;

    public BoxThread(int[][] board, int start, int end,
                     ConcurrentLinkedQueue<Integer> invalidBoxes,
                     ConcurrentLinkedQueue<String> reasons) {
        this.board = board;
        this.start = start;
        this.end = end;
        this.invalidBoxes = invalidBoxes;
        this.reasons = reasons;
    }

    private List<String> checkBox(int boxIndex) {
        Map<Integer,List<String>> dup = new HashMap<>();
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
                        if (board[r][c] == v)
                            pos.add("(" + (r+1) + "," + (c+1) + ")");
                    }
                }
                dup.put(v, pos);
            }
        }

        List<String> out = new ArrayList<>();
        for (Map.Entry<Integer,List<String>> e : dup.entrySet()) {
            out.add(String.format("BOX %d, #%d, %s",
                    boxIndex + 1, e.getKey(), e.getValue()));
        }
        return out;
    }

    @Override
    public void run() {
        for (int b = start; b <= end; b++) {
            List<String> res = checkBox(b);
            if (!res.isEmpty()) {
                invalidBoxes.add(b);
                reasons.addAll(res);
            }
        }
    }
}
