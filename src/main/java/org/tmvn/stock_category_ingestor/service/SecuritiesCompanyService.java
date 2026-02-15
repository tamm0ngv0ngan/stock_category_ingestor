package org.tmvn.stock_category_ingestor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tmvn.stock_category_ingestor.model.SecuritiesCompany;
import org.tmvn.stock_category_ingestor.repository.FirestoreRepository;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class SecuritiesCompanyService {
    private static final String COLLECTION_NAME = "securities-companies";
    private final FirestoreRepository repository;


    public void updateSecuritiesCompanies() {
        log.info("Start updating securities companies");
        Map<String, SecuritiesCompany> securitiesCompanyMap = repository.getAll(COLLECTION_NAME, SecuritiesCompany.class);
        if (securitiesCompanyMap.size() != 2) {
            securitiesCompanyMap.keySet().forEach(id -> repository.delete(COLLECTION_NAME, id));
            repository.insert(COLLECTION_NAME, SecuritiesCompany.ofVND());
            repository.insert(COLLECTION_NAME, SecuritiesCompany.ofTCBS());
        }
    }

}
