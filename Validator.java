package sudoku;

/**
 * Common validator interface for rows/columns/boxes.
 */
public interface Validator {
    /**
     * Perform validation and return a ValidationResult describing duplicates (if any).
     */
    ValidationResult validate();
}
