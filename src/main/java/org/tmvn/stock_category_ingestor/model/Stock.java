package org.tmvn.stock_category_ingestor.model;

import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Builder;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

@Builder
public record Stock(String symbol, String name, String industry, String exchange, String description, String url,
                    Boolean updated, @ServerTimestamp Date updatedAt) {
    public String id() {
        return DigestUtils.sha256Hex(symbol);
    }
}
