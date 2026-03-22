package org.tmvn.stock_category_ingestor.model;

public record ImportOption(int id, String value) {
    public static ImportOption ofStockTransaction() {
        return new ImportOption(1, "Stock Transaction");
    }

    public static ImportOption ofExerciseType() {
        return new ImportOption(2, "Exercise Type");
    }
}
