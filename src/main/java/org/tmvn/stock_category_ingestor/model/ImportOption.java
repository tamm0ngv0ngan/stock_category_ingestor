package org.tmvn.stock_category_ingestor.model;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public record ImportOption(String code, String name, int order, List<String> supportedContentTypeImportFiles) {
    public static ImportOption ofStockTransaction() {
        return new ImportOption("transaction.file", "Stock Transaction", 1, List.of("pdf", "xlsx"));
    }

    public static ImportOption ofExerciseType() {
        return new ImportOption("exercise.file", "Exercise Type", 2, List.of("xlsx"));
    }

    public String id() {
        return DigestUtils.sha256Hex(code);
    }
}
