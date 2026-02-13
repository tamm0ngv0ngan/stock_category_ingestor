package org.tmvn.stock_category_ingestor;

import com.google.cloud.firestore.Firestore;
import lombok.extern.slf4j.Slf4j;
import org.tmvn.stock_category_ingestor.config.AppConfig;
import org.tmvn.stock_category_ingestor.repository.FirestoreRepository;
import org.tmvn.stock_category_ingestor.service.SecuritiesCompanyService;

@Slf4j
public class Main {
    private static final SecuritiesCompanyService securitiesCompanyService;

    static {
        log.info("Config Bean...");
        Firestore firestore = AppConfig.firestore;
        FirestoreRepository repository = new FirestoreRepository(firestore);
        securitiesCompanyService = new SecuritiesCompanyService(repository);
    }

    public static void main(String[] args) {
        log.info("Start App...");
        securitiesCompanyService.updateSecuritiesCompanies();
    }
}
