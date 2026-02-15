package org.tmvn.stock_category_ingestor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tmvn.stock_category_ingestor.crawler.impl.VietStockCrawlerRunner;
import org.tmvn.stock_category_ingestor.model.Stock;
import org.tmvn.stock_category_ingestor.repository.FirestoreRepository;
import org.tmvn.stock_category_ingestor.thread.ThreadExecutor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class StockService {
    private static final String COLLECTION_NAME = "stocks";
    private final FirestoreRepository repository;

    public void updateStocks() {
        List<Stock> notUpdatedStocks = repository.queryEquals(COLLECTION_NAME, "updated", false, Stock.class);
        List<VietStockCrawlerRunner> runners = notUpdatedStocks.stream().map(VietStockCrawlerRunner::new).toList();
        ThreadExecutor executor = new ThreadExecutor(30, runners);
        executor.execute();
        Map<String, Stock> updatedStockMap = runners.stream().map(VietStockCrawlerRunner::stock)
                .collect(Collectors.toMap(Stock::getCode, Function.identity()));
        repository.updateBatch(COLLECTION_NAME, updatedStockMap);
    }
}
