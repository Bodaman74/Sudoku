package sudoku;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ValidationResult {
    private final String unitType;
    private final int unitIndex;
    private final boolean valid;
    private final Map<Integer, List<Integer>> duplicates;

    public ValidationResult(String unitType, int unitIndex, boolean valid, Map<Integer, List<Integer>> duplicates) {
        this.unitType = unitType;
        this.unitIndex = unitIndex;
        this.valid = valid;
        this.duplicates = duplicates == null ? Collections.emptyMap() : new LinkedHashMap<>(duplicates);
    }

    public String getUnitType() {
        return unitType;
    }

    public int getUnitIndex() {
        return unitIndex;
    }

    public boolean isValid() {
        return valid;
    }

    public Map<Integer, List<Integer>> getDuplicates() {
        return duplicates;
    }

    public String toString() {
        if (valid) {
            return unitType + " " + unitIndex + ": VALID";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> e : duplicates.entrySet()) {
            sb.append(unitType).append(" ").append(unitIndex)
              .append(", #").append(e.getKey())
              .append(", ").append(e.getValue())
              .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
