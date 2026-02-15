package org.tmvn.stock_category_ingestor.crawler;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public abstract class WebCrawlerRunner implements Runnable {

    protected Document downloadPage(String url, int maxTry) {
        return downloadPage(url, 0, maxTry);
    }

    protected Document downloadPage(String url, int index, int maxTry) {
        if (index >= maxTry) {
            log.error("Cannot get page with url: {}", url);
            throw new RuntimeException(String.format("Cannot get page with url: %s", url));
        }
        try {
            return Jsoup.connect(url).get();
        } catch (Exception ex) {
            return downloadPage(url, index + 1, maxTry);
        }
    }
}
