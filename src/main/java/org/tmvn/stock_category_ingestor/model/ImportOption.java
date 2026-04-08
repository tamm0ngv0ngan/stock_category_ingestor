package org.tmvn.stock_category_ingestor.model;

public record ImportOption(int id, String code, String value) {
    public static ImportOption ofStockTransaction() {
        return new ImportOption(1, "transaction.file", "Stock Transaction");
    }

    public static ImportOption ofExerciseType() {
        return new ImportOption(2, "exercise.file", "Exercise Type");
    }
}
