package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sudoku {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("Usage: java -jar Sudoku.jar <file.csv> <mode>");
            return;
        }

        System.out.println("VALIDATING SUDOKU BOARD...");
        System.out.println("------------------------------------------");

        CSVreader reader = new CSVreader(args[0]);
        int[][] grid = reader.readBoard();

        if (grid == null) {
            System.out.println("FINAL RESULT: INVALID SUDOKU");
            return;
        }

        Board board = new Board(grid);
        int mode = Integer.parseInt(args[1]);

        List<ValidationResult> rowResults = validateRows(board.getGrid(), mode);
        List<ValidationResult> colResults = validateCols(board.getGrid(), mode);
        List<ValidationResult> boxResults = validateBoxes(board.getGrid(), mode);

        print("ROWS", rowResults);
        print("COLS", colResults);
        print("BOXES", boxResults);

        boolean valid = allValid(rowResults) && allValid(colResults) && allValid(boxResults);

        System.out.println("------------------------------------------");
        System.out.println("FINAL RESULT: " + (valid ? "VALID SUDOKU" : "INVALID SUDOKU"));
        System.out.println("------------------------------------------");
    }

    private static List<ValidationResult> validateRows(int[][] grid, int mode) throws InterruptedException {
        return runValidators(grid, mode, "ROW");
    }

    private static List<ValidationResult> validateCols(int[][] grid, int mode) throws InterruptedException {
        return runValidators(grid, mode, "COL");
    }

    private static List<ValidationResult> validateBoxes(int[][] grid, int mode) throws InterruptedException {
        return runValidators(grid, mode, "BOX");
    }

    private static List<ValidationResult> runValidators(int[][] grid, int mode, String type)
            throws InterruptedException {

        List<ValidationResult> results = Collections.synchronizedList(new ArrayList<ValidationResult>());
        List<Thread> tasks = new ArrayList<Thread>();

        for (int i = 0; i < 9; i++) {
            Validator v;
            if (type.equals("ROW")) v = ValidatorFactory.createRow(grid, i);
            else if (type.equals("COL")) v = ValidatorFactory.createColumn(grid, i);
            else v = ValidatorFactory.createBox(grid, i);

            if (mode == 27) {
                Thread t = new ValidatorTask(v, results);
                tasks.add(t);
                t.start();
            } else {
                results.add(v.validate());
            }
        }

        for (Thread t : tasks) t.join();

        Collections.sort(results, new Comparator<ValidationResult>() {
            public int compare(ValidationResult a, ValidationResult b) {
                return a.getUnitIndex() - b.getUnitIndex();
            }
        });

        return results;
    }

    private static void print(String title, List<ValidationResult> results) {
        boolean valid = true;
        for (ValidationResult r : results) {
            System.out.println(r.toString());
            if (!r.isValid()) valid = false;
        }
        System.out.println("------------------------------------------");
        System.out.println(title + ": " + (valid ? "VALID" : "INVALID"));
        System.out.println("------------------------------------------");
    }

    private static boolean allValid(List<ValidationResult> list) {
        for (ValidationResult r : list) {
            if (!r.isValid()) return false;
        }
        return true;
    }
}
