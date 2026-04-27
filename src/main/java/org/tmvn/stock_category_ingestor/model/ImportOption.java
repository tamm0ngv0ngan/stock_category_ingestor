package org.tmvn.stock_category_ingestor.model;

import java.util.List;

public record ImportOption(int id, String code, String value, List<String> supportedContentTypeImportFiles) {
    public static ImportOption ofStockTransaction() {
        return new ImportOption(1, "transaction.file", "Stock Transaction", List.of("pdf", "xlsx"));
    }

    public static ImportOption ofExerciseType() {
        return new ImportOption(2, "exercise.file", "Exercise Type", List.of("xlsx"));
    }
}
