package sudoku;

import java.util.*;

public class ValidationResult {
    private final Map<Integer, List<String>> details = new HashMap<>();

    public void addInvalidRow(int rowIndex, String reason) {
        details.computeIfAbsent(rowIndex, k -> new ArrayList<>()).add(reason);
    }

    public boolean allValid() {
        return details.isEmpty();
    }

    public List<Integer> getInvalidRows() {
        return new ArrayList<>(details.keySet());
    }

    public List<String> getReasonsForRow(int row) {
        return details.getOrDefault(row, new ArrayList<>());
    }

    public void merge(ValidationResult other) {
        for (Map.Entry<Integer, List<String>> entry : other.details.entrySet()) {
            details.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                   .addAll(entry.getValue());
        }
    }
    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    if (details.isEmpty()) {
        sb.append("All rows valid");
    } else {
        for (int row : details.keySet()) {
            for (String reason : details.get(row)) {
                sb.append(reason).append("\n");
            }
        }
    }
    return sb.toString();
}
}
