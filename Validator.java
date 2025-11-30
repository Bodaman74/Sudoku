/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

/**
 *
 * @author DELL
 */
public interface Validator {
    /**
     * Perform validation and return a ValidationResult describing duplicates (if any).
     */
    ValidationResult validate();
}
