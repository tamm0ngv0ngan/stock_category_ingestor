package org.tmvn.stock_category_ingestor.model;


public record SecuritiesCompany(int id, String name, String description) {
    public static SecuritiesCompany ofVND() {
        return new SecuritiesCompany(1, "VNDIRECT", "VNDIRECT Securities Corporation");
    }

    public static SecuritiesCompany ofTCBS() {
        return new SecuritiesCompany(2, "TCBS", "Techcom Securities Joint Stock Company");
    }
}
