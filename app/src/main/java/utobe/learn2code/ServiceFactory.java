package utobe.learn2code;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class ServiceFactory {
    private static FirebaseFirestore db = null;
    private static FirebaseStorage storage = null;

    public static FirebaseFirestore getFirebaseFirestore() {
        if (db == null)
            db = FirebaseFirestore.getInstance();
        return db;
    }

    public static FirebaseStorage getFirebaseStorage() {
        if (storage == null)
            storage = FirebaseStorage.getInstance();
        return storage;
    }
}
