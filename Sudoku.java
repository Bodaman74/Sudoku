/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sudoku;

/**
 *
 * @author DELL
 */
public class Sudoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        int[][] board = {
            {1,2,3,4,5,6,7,0,0},
            {1,1,3,4,5,6,7,8,9}, 
            {3,4,0,6,7,8,9,1,2},
            {4,5,6,7,8,9,1,2,3},
            {5,6,7,8,9,1,2,3,4},
            {6,7,8,9,1,2,3,4,5},
            {7,8,9,1,2,3,4,5,6},
            {8,9,1,2,3,4,5,6,7},
            {9,1,2,3,4,5,6,7,8}
        };

        
        RowValidatorManager validator = new RowValidatorManager(board);

       
        System.out.println("=== Mode 0 (Sequential) ===");
        ValidationResult result0 = validator.validateMode0();
        System.out.println(result0);

        System.out.println("=== Mode 3 (3 Threads) ===");
        ValidationResult result3 = validator.validateMode3();
        System.out.println(result3);

        System.out.println("=== Mode 27 (1 Thread per Row) ===");
        ValidationResult result27 = validator.validateMode27();
        System.out.println(result27);
    }
    }
    

