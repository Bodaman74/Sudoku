package sudoku;

import java.util.*;

public class ValidationResult {
    private String type;
    private int index;
    private boolean valid;
    private Map<Integer, List<Integer>> duplicates;

    public ValidationResult(String type, int index, boolean valid, Map<Integer, List<Integer>> duplicates) {
        this.type = type;
        this.index = index;
        this.valid = valid;
        this.duplicates = duplicates;
    }

    public boolean isValid() {
        return valid;
    }

    public int getUnitIndex() {
        return index;
    }

    public String toString() {
        if (valid) return type + " " + index + ": VALID";
        StringBuilder s = new StringBuilder();
        for (Integer v : duplicates.keySet()) {
            if (s.length() > 0) s.append("\n");
            s.append(type + " " + index + ", #" + v + ", " + duplicates.get(v));
        }
        return s.toString();
    }
}
