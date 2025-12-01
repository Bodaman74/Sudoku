package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVreader {
    private final String filepath;

    public CSVreader(String filepath) {
        this.filepath = filepath;
    }

    public int[][] readBoard() {
        int[][] board = new int[9][9];

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {
                if (row >= 9) {
                    System.out.println("ERROR: CSV contains more than 9 rows.");
                    return null;
                }

                String[] cells = line.trim().split(",");
                if (cells.length != 9) {
                    System.out.println("ERROR: Row " + (row + 1) + " does not contain 9 values.");
                    return null;
                }

                for (int col = 0; col < 9; col++) {
                    String value = cells[col].trim();

                    if (!value.matches("\\d+")) {
                        System.out.println("ERROR: Non-integer value at row "
                                + (row + 1) + ", column " + (col + 1)
                                + " → \"" + value + "\"");
                        return null;
                    }

                    int num = Integer.parseInt(value);

                    if (num < 1 || num > 9) {
                        System.out.println("ERROR: Value out of range (1-9) at row "
                                + (row + 1) + ", column " + (col + 1)
                                + " → " + num);
                        return null;
                    }

                    board[row][col] = num;
                }
                row++;
            }

            if (row < 9) {
                System.out.println("ERROR: CSV does not contain 9 rows.");
                return null;
            }

        } catch (IOException e) {
            System.out.println("ERROR reading CSV: " + e.getMessage());
            return null;
        }

        return board;
    }
}
