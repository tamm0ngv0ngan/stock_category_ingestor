package org.tmvn.stock_category_ingestor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tmvn.stock_category_ingestor.model.ImportOption;
import org.tmvn.stock_category_ingestor.repository.FirestoreRepository;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ImportOptionService {
    private static final String COLLECTION_NAME = "import-options";
    private final FirestoreRepository repository;

    public void updateImportOptions() {
        log.info("Start updating import types");
        Map<String, ImportOption> importOptions = repository.getAll(COLLECTION_NAME, ImportOption.class);
        if (importOptions.size() != 2) {
            importOptions.keySet().forEach(id -> repository.delete(COLLECTION_NAME, id));
            repository.insert(COLLECTION_NAME, ImportOption.ofStockTransaction());
            repository.insert(COLLECTION_NAME, ImportOption.ofExerciseType());
        }
    }
}
