package com.capmu.firestore.service;

import com.capmu.firestore.entity.Product;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductService {

    private static final String COLLECTION_NAME = "products";

    public String saveProduct(Product product) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = db.collection(COLLECTION_NAME)

                //if use .document() "no any argument inside" the record will be generated with auto-id
                .document(product.getName()).set(product);

        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public Product getProductByName(String name) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection(COLLECTION_NAME).document(name); //because we're using product name as an ID
        ApiFuture<DocumentSnapshot> futureDocument = documentReference.get();
        DocumentSnapshot document = futureDocument.get();

        if (document.exists()) {
            return document.toObject(Product.class);
        }
        return null;
    }

    public List<Product> getAllProducts() throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReferences = db.collection(COLLECTION_NAME).listDocuments();
        Iterator<DocumentReference> documentReferenceIterator = documentReferences.iterator();

        List<Product> productList = new ArrayList<>();
        while (documentReferenceIterator.hasNext()) {
            DocumentReference documentReference = documentReferenceIterator.next();
            ApiFuture<DocumentSnapshot> futureDocument = documentReference.get();
            DocumentSnapshot document = futureDocument.get();
            productList.add(document.toObject(Product.class));
        }
        return productList;
    }

    public String updateProduct(Product product) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = db.collection(COLLECTION_NAME)

                //if use .document() "no any argument inside" the record will be generated with auto-id
                .document(product.getName()).set(product);

        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteProductByName(String name) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = db.collection(COLLECTION_NAME).document(name).delete();

        return "Deleted the product, " + name;
    }
}
