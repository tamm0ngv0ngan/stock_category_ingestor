package org.tmvn.stock_category_ingestor.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.*;
import java.util.Properties;

public class AppConfig {
    public static Firestore firestore;
    public static String stockLinkPath;

    static {
        String filePath = getConfigFilePath();
        Properties properties = new Properties();
        try (InputStream is = new FileInputStream(filePath)) {
            properties.load(is);
            String credentialsPath = properties.getProperty("firebase.credentials.path");
            updateFirestore(credentialsPath);
            stockLinkPath = properties.getProperty("stock.link.path");
        } catch (IOException ex) {
            throw new RuntimeException("Cannot read config file with error: " + ex.getMessage());
        }
    }

    private static String getConfigFilePath() {
        String path = System.getProperty("user.dir");
        String serverFilePath = path + "/config/application.properties";
        if (new File(serverFilePath).exists()) {
            return serverFilePath;
        }
        String localFilePath = path + "/src/main/resources/application.properties";
        if (new File(localFilePath).exists()) {
            return localFilePath;
        }
        throw new RuntimeException("Config file not found ...");
    }

    private static void updateFirestore(String credentialsPath) throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }
        FileInputStream serviceAccount = new FileInputStream(credentialsPath);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
        firestore = FirestoreClient.getFirestore();
    }
}
