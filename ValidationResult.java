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
        List<Integer> rows = new ArrayList<>(details.keySet());
        Collections.sort(rows);
        return Collections.unmodifiableList(rows);
    }


    public List<String> getDetails(int rowIndex) {
        return details.getOrDefault(rowIndex, Collections.emptyList());
    }


    
    public void merge(ValidationResult other) {
        for (int row : other.details.keySet()) {
            for (String reason : other.details.get(row)) {
                addInvalidRow(row, reason);
            }
        }
    }


    @Override
    public String toString() {
        if (allValid()) return "All rows are VALID.";

        StringBuilder sb = new StringBuilder();
        sb.append("INVALID rows found: ").append(details.size()).append("\n");

        for (int r : getInvalidRows()) {
            for (String reason : details.get(r)) {
                sb.append(reason).append("\n");
            }
        }
        return sb.toString();
    }
}
