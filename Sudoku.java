/*read command-line arguments
call the CSVReader
call the factory later (not your part right now)*/
package sudoku;

public class Sudoku {

    public static void main(String[] args) {

        //check args
        System.out.println("Args count = " + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = '" + args[i] + "'");
        }

        if (args.length != 2) {
            System.out.println("Usage: java -jar app.jar <filepath.csv> <mode>");
            return;
        }
//------------------------------------------------------
        String filepath = args[0];

        /*      Helper statment        
        System.out.println("Looking for: " + new java.io.File(filepath).getAbsolutePath());
         */
        String modeString = args[1];

        int mode;
        try {
            mode = Integer.parseInt(modeString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid mode. Must only select from(0-3-27).");
            return;
        }

        if (mode != 0 && mode != 3 && mode != 27) {
            System.out.println("Invalid mode. Only select from (0-3-27).");
            return;
        }

        CSVreader reader = new CSVreader(filepath);
        System.out.println("------Loading board-----");
//        int[][] board = reader.readBoard(); // before doing board class
        Board board = new Board(reader.readBoard());
        if (board == null) {
            return;
        }
        System.out.println("Board loaded successfully!");

        /*Helper statment for method
        printBoard(board);
         */
        Validator v = ValidatorFactory.createValidator(mode, board);
        v.validate();

    }

    /* Helper method
    private static void printBoard(Board board) {
        int[][] g = board.getGrid();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(g[i][j] + " ");
            }
            System.out.println();
        }
    }
    */
}
