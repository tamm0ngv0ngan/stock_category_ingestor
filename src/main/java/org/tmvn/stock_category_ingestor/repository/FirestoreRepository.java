package org.tmvn.stock_category_ingestor.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class FirestoreRepository {
    private final Firestore firestore;

    public <T>Map<String, T> getAll(String collection, Class<T> clazz) {
        try {
            QuerySnapshot querySnapshot = firestore.collection(collection).get().get();
            return querySnapshot.getDocuments().stream()
                    .collect(Collectors.toMap(DocumentSnapshot::getId, doc -> doc.toObject(clazz)));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("Cannot get data");
        }
    }

    public <T> void insert(String collection, T data) {
        try {
            firestore.collection(collection).add(data).get();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("Cannot update data");
        }
    }

    public void delete(String collection, String docId) {
        try {
            firestore.collection(collection).document(docId).delete().get();
        } catch (InterruptedException | ExecutionException ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("Cannot delete data");
        }
    }
}
