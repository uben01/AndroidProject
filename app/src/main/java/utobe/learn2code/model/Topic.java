package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Topic extends AbstractEntity {
    private final String title;
    private final Boolean isTest;
    private final Boolean isUnlocked;
    private final String parent;
    private final Long serialNumber;

    private Topic(QueryDocumentSnapshot document) {
        super(document.getId());
        title = document.getString("title");
        isTest = document.getBoolean("isTest");
        parent = document.getString("parent");
        isUnlocked = document.getBoolean("isUnlocked");
        serialNumber = document.getLong("serialNumber");
    }

    public static ArrayList<Topic> buildTopics(QuerySnapshot documents) {
        ArrayList<Topic> topics = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Topic topic = new Topic(document);
            topics.add(topic);
        }

        return topics;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getTest() {
        return isTest;
    }

    public Boolean getUnlocked() {
        return isUnlocked;
    }

    public String getParent() {
        return parent;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }
}