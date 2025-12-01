package sudoku;

import java.util.List;

public class ValidatorTask extends Thread {
    private final Validator validator;
    private final List<ValidationResult> results;

    public ValidatorTask(Validator validator, List<ValidationResult> results) {
        this.validator = validator;
        this.results = results;
    }

    public void run() {
        results.add(validator.validate());
    }
}
