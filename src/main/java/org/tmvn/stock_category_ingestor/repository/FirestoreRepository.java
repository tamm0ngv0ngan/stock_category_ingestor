package org.tmvn.stock_category_ingestor.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteBatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public class FirestoreRepository {
    private final Firestore firestore;
    private static final int MAX_BATCH_SIZE = 469;

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

    public <T> void updateBatch(String collection, Map<String, T> dataMap) {
        List<String> keys = new ArrayList<>(dataMap.keySet());
        List<List<String>> subKeyLists = IntStream.range(0, (keys.size() + MAX_BATCH_SIZE - 1) / MAX_BATCH_SIZE)
                .mapToObj(i -> keys.subList(i * MAX_BATCH_SIZE, Math.min((i + 1) * MAX_BATCH_SIZE, keys.size())))
                .toList();
        for (List<String> subKeyList: subKeyLists) {
            WriteBatch batch = firestore.batch();
            for (String key: subKeyList) {
                var docRef = firestore.collection(collection).document(key);
                batch.set(docRef, dataMap.get(key));
            }

            try {
                batch.commit().get();
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                throw new RuntimeException("Cannot update batch");
            }
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
