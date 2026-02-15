package org.tmvn.stock_category_ingestor.crawler.impl;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.tmvn.stock_category_ingestor.crawler.WebCrawlerRunner;
import org.tmvn.stock_category_ingestor.model.Stock;

import java.util.Objects;

@Slf4j
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class VietStockCrawlerRunner extends WebCrawlerRunner {
    private final static int MAX_TRY = 5;
    private final Stock stock;

    private void setCode(Element titleElement) {
        String code = titleElement.getElementsByClass("title-link").getFirst().text();
        stock.setCode(code);
    }

    private void setName(Element titleElement) {
        String name = titleElement.getElementsByTag("b").getFirst().text();
        stock.setName(name);
    }

    private void setExchange(Element titleElement) {
        String exchange = titleElement.getElementsByTag("b").get(1).text();
        stock.setExchange(exchange.substring(0, exchange.indexOf(":")).toUpperCase());
    }

    private void setIndustry(Document document) {
        Element industryElement = document.getElementsByClass("sector-level").getFirst();
        String industry = industryElement.getElementsByClass("title-link").getFirst().text();
        stock.setIndustry(industry);
    }

    private void setDescription(Document document) {
        Element summaryElement = document.getElementById("summary-full");
        if (Objects.isNull(summaryElement)) {
            stock.setDescription("");
        } else {
            Element spanElement = summaryElement.getElementsByTag("span").getFirst();
            if (spanElement.getElementsByTag("p").isEmpty()) {
                stock.setDescription(spanElement.text());
            } else {
                Element descriptionElement = spanElement.getElementsByTag("p").getFirst();
                if (!descriptionElement.getElementsByTag("a").isEmpty()) {
                    descriptionElement.getElementsByTag("a").remove();
                }
                stock.setDescription(descriptionElement.text());
            }
        }

    }

    @Override
    public void run() {
        try {
            log.info("Start Crawler For: {}", stock.getCode());
            Document document = downloadPage(stock.getUrl(), MAX_TRY);
            Element titleElement = document.getElementsByClass("h1-title").getFirst();
            setCode(titleElement);
            setName(titleElement);
            setExchange(titleElement);
            setIndustry(document);
            setDescription(document);
            stock.setUpdated(true);
        } catch (Exception ex) {
            log.error("Get Stock Data from URL: {} for {} error: {}", stock.getUrl(), stock.getCode(), ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
