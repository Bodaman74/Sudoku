/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author DELL
 */
    public class RowThread implements Runnable {
    private  final int[][] board;
    private  final int start;
    private  final int end;
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
    
    List<String> checkRowReason(int row) {
    Map<Integer, List<Integer>> duplicates = new HashMap<>();
    int[] counts = new int[10]; // 1..9

    // count the val
    for (int col = 0; col < 9; col++) {
        int val = board[row][col];
        counts[val]++;
    }

    
    for (int val = 1; val <= 9; val++) {
        if (counts[val] > 1) {
            List<Integer> positions = new ArrayList<>();
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == val) positions.add(col + 1);
            }
            duplicates.put(val, positions);
        }
    }

    List<String> reasonsList = new ArrayList<>();
    for (Map.Entry<Integer, List<Integer>> entry : duplicates.entrySet()) {
        reasonsList.add(String.format("ROW %d, #%d, %s", row + 1, entry.getKey(), entry.getValue()));
    }

    return reasonsList; 
}
 
    
    @Override
    public void run() {
        for (int r = start; r <= end; r++) {
    List<String> reasonsList = checkRowReason(r);
    if (!reasonsList.isEmpty()) {
        invalidRows.add(r);
        reasons.addAll(reasonsList); 
}
        
    }
    
    
}
    }
    
    
