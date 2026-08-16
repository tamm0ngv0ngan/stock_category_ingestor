package org.tmvn.stock_category_ingestor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tmvn.stock_category_ingestor.model.ImportOption;
import org.tmvn.stock_category_ingestor.repository.FirestoreRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ImportOptionService {
    private static final String COLLECTION_NAME = "import-options";
    private final FirestoreRepository repository;

    public void updateImportOptions() {
        log.info("Start updating import types");
        List<ImportOption> importOptionList = List.of(ImportOption.ofStockTransaction(), ImportOption.ofExerciseType());
        Map<String, ImportOption> importOptionMap = importOptionList.stream().collect(Collectors.toMap(ImportOption::id, Function.identity()));
        repository.updateBatch(COLLECTION_NAME, importOptionMap);
    }
}
