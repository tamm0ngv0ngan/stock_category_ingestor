package org.tmvn.stock_category_ingestor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tmvn.stock_category_ingestor.model.StockLink;
import org.tmvn.stock_category_ingestor.repository.FirestoreRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class StockLinkService {
    private static final String COLLECTION_NAME = "stocks";
    private final String stockLinkPath;
    private final FirestoreRepository repository;

    private Map<String, StockLink> getStockLinkMap() {
        try (Stream<String> lines = Files.lines(Path.of(stockLinkPath))) {
            return lines.map(line -> line.split(","))
                    .filter(parts -> parts.length == 2)
                    .filter(parts -> parts[0].length() == 3)
                    .map(parts -> new StockLink(parts[0], parts[1]))
                    .collect(Collectors.toMap(StockLink::code, Function.identity()));
        } catch (Exception ex) {
            log.error("Cannot read stock link file");
            throw new RuntimeException("Cannot read stock link file");
        }
    }

    public void updateToFirebase() {
        Map<String, StockLink> stockLinkMap = getStockLinkMap();
        repository.updateBatch(COLLECTION_NAME, stockLinkMap);
    }
}
