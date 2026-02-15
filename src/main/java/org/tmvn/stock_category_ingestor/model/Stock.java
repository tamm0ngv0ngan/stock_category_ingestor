package org.tmvn.stock_category_ingestor.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Stock {
    private String url;
    private String code;
    private String name;
    private String industry;
    private String exchange;
    private String description;
    private Boolean updated;
}
