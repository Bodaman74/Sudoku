/*read the .csv file and return a int[9][9] Sudoku board*/
/*checks (9 values, valid ints, etc.) not required.*/
package sudoku;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class CSVreader {

    private String filepath;

    public CSVreader(String filepath) {
        this.filepath = filepath;
    }
    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int[][] readBoard() {
        int[][] board = new int[9][9];//should specify size
        int row = 0;
        String[] parts;
        int value;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            while (row < 9) {
                
                String line = br.readLine();
                
                //check for missing lines
                if (line == null) {
                    System.out.println("Error: The CSV file doesn't contain 9 lines");
                    br.close();
                    return null;
                }
                
                parts = line.split(",");
                
                //check for numbers != 9
                if(parts.length != 9)
                {
                    System.out.println("Error: Row " + (row + 1) + " doesn't contain 9 values.");
                    br.close();
                    return null;
                }
                
                for (int col = 0; col <= 8; col++) {
                    try{
                    value = Integer.parseInt(parts[col]);
                    board[row][col] = value;
                    }catch(NumberFormatException e){
                        System.out.println("Error: Invalid number at row " + (row + 1) + ", column " + (col + 1));
                    br.close();
                    return null;
                    }
                }
                row++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        return board;
    }
}
