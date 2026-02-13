package org.tmvn.stock_category_ingestor;

import com.google.cloud.firestore.Firestore;
import lombok.extern.slf4j.Slf4j;
import org.tmvn.lib.GitInfo;
import org.tmvn.stock_category_ingestor.config.AppConfig;
import org.tmvn.stock_category_ingestor.repository.FirestoreRepository;
import org.tmvn.stock_category_ingestor.service.SecuritiesCompanyService;
import org.tmvn.stock_category_ingestor.service.StockLinkService;

@Slf4j
public class Main {
    private static final SecuritiesCompanyService securitiesCompanyService;
    private static final StockLinkService stockLinkService;

    static {
        log.info("Config Bean...");
        Firestore firestore = AppConfig.firestore;
        FirestoreRepository repository = new FirestoreRepository(firestore);
        securitiesCompanyService = new SecuritiesCompanyService(repository);
        String stockLinkPath = AppConfig.stockLinkPath;
        stockLinkService = new StockLinkService(stockLinkPath, repository);
    }

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.equals("--info")) {
                GitInfo gitInfo = new GitInfo(Main.class);
                gitInfo.showInfo();
                return;
            }
        }

        log.info("Start App...");
        securitiesCompanyService.updateSecuritiesCompanies();
        stockLinkService.updateToFirebase();
    }
}
