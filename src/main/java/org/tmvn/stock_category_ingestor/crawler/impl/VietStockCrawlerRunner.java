package org.tmvn.stock_category_ingestor.crawler.impl;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tmvn.stock_category_ingestor.crawler.WebCrawlerRunner;
import org.tmvn.stock_category_ingestor.model.Stock;

import java.util.Objects;

@Slf4j
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class VietStockCrawlerRunner extends WebCrawlerRunner {
    private final Stock.StockBuilder stockBuilder = Stock.builder();
    private final static int MAX_TRY = 5;
    private final String symbol;
    private final String url;

    private void setCode(Element companyInfoElement) {
        String code = companyInfoElement.getElementsByClass("company-general-stock-code").getFirst().text();
        stockBuilder.symbol(code);
    }

    private void setName(Element companyInfoElement) {
        String name = companyInfoElement.getElementsByClass("company-general-name").getFirst().text();
        stockBuilder.name(name);
    }

    private void setExchange(Element companyInfoElement) {
        String exchange = companyInfoElement.getElementsByClass("company-general-stock-exchange").getFirst().text();
        stockBuilder.exchange(exchange.toUpperCase());
    }

    private void setIndustry(Element companyInfoElement) {
        StringBuilder industry = new StringBuilder();
        Element companySector = companyInfoElement.getElementsByClass("company-general-sector").getFirst();
        Elements linkElements = companySector.getElementsByTag("a");
        for (Element linkElement : linkElements) {
            industry.append(linkElement.text());
            industry.append(">>");
        }
        if (industry.length() >= 2) {
            stockBuilder.industry(industry.substring(0, industry.length() - 2));
        } else {
            stockBuilder.industry("");
        }
    }

    private void setDescription(Document document) {
        Element summaryElement = document.getElementsByClass("overview-summary__body").getFirst();
        Element descriptionElement = summaryElement.getElementsByTag("p").first();
        if (Objects.isNull(descriptionElement)) {
            stockBuilder.description("");
        } else {
            stockBuilder.description(descriptionElement.text());
        }

    }

    @Override
    public void run() {
        try {
            log.info("Start Crawler For: {}", symbol);
            stockBuilder.url(url);
            Document document = downloadPage(url, MAX_TRY);
            Elements companyInfoElements = document.getElementsByClass("company-general-info");
            if (companyInfoElements.size() != 1) {
                log.error("Page update layout, please update code");
                throw new RuntimeException();
            }
            Element companyInfoElement = companyInfoElements.getFirst();
            setCode(companyInfoElement);
            setName(companyInfoElement);
            setExchange(companyInfoElement);
            setIndustry(document);
            setDescription(document);
            stockBuilder.updated(true);
        } catch (Exception ex) {
            log.error("Get Stock Data from URL: {} for {} error: {}", url, symbol, ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
