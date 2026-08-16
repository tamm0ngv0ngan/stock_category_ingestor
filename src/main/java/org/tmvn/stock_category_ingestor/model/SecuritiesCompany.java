package org.tmvn.stock_category_ingestor.model;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public record SecuritiesCompany(String code, String name, int order, List<String> supportedContentTypeImportFiles) {

    public static SecuritiesCompany ofVND() {
        return new SecuritiesCompany("VNDIRECT", "VNDIRECT Securities Corporation", 1,
                List.of("pdf", "xlsx"));
    }

    public static SecuritiesCompany ofTCBS() {
        return new SecuritiesCompany("TCBS", "Techcom Securities Joint Stock Company", 2,
                List.of("xlsx"));
    }

    public String id() {
        return DigestUtils.sha256Hex(code);
    }
}
