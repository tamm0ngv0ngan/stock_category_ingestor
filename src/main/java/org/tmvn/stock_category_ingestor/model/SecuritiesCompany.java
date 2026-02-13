package org.tmvn.stock_category_ingestor.model;


import org.tmvn.stock_category_ingestor.util.StringUtils;

public record SecuritiesCompany(String id, String name, String description) {
    public static SecuritiesCompany ofVND() {
        return new SecuritiesCompany(StringUtils.genUniqueId(), "VNDIRECT", "VNDIRECT Securities Corporation");
    }

    public static SecuritiesCompany ofTCBS() {
        return new SecuritiesCompany(StringUtils.genUniqueId(), "TCBS", "Techcom Securities Joint Stock Company");
    }
}
