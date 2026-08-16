package org.tmvn.stock_category_ingestor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tmvn.stock_category_ingestor.model.SecuritiesCompany;
import org.tmvn.stock_category_ingestor.repository.FirestoreRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class SecuritiesCompanyService {
    private static final String COLLECTION_NAME = "securities-companies";
    private final FirestoreRepository repository;


    public void updateSecuritiesCompanies() {
        log.info("Start updating securities companies");
        List<SecuritiesCompany> securitiesCompanyList = List.of(SecuritiesCompany.ofVND(), SecuritiesCompany.ofTCBS());
        Map<String, SecuritiesCompany> securitiesCompanyMap = securitiesCompanyList.stream()
                .collect(Collectors.toMap(SecuritiesCompany::id, Function.identity()));
        repository.updateBatch(COLLECTION_NAME, securitiesCompanyMap);
    }
}
