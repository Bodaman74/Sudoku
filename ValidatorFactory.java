package sudoku;

public class ValidatorFactory {

    public static Object create(String type, int[][] board) {

        switch(type) {

            case "row":
                return new RowValidator(board);

            case "column":
                return new ColumnValidator(board);

            case "box":
                return new BoxValidator(board);

            default:
                throw new IllegalArgumentException("Unknown validator type: " + type);
        }
    }
}
