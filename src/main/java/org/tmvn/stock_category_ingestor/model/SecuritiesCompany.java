package org.tmvn.stock_category_ingestor.model;


import java.util.List;

public record SecuritiesCompany(int id, String name, String description, List<String> supportedContentTypeImportFiles) {

    public static SecuritiesCompany ofVND() {
        return new SecuritiesCompany(1, "VNDIRECT", "VNDIRECT Securities Corporation",
                List.of("pdf", "xlsx"));
    }

    public static SecuritiesCompany ofTCBS() {
        return new SecuritiesCompany(2, "TCBS", "Techcom Securities Joint Stock Company",
                List.of("xlsx"));
    }
}
