package org.tmvn.stock_category_ingestor.util;


import java.util.UUID;

public class StringUtils {

    public static String genUniqueId() {
        return UUID.randomUUID() + "-" + System.currentTimeMillis();
    }
}
