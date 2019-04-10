package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Topic extends AbstractEntity {
    private String title;
    private Boolean isTest;
    private Boolean isUnlocked;
    private String parent;

    public Topic(){};

    private Topic(QueryDocumentSnapshot document) {
        super(document.getId());
        title = document.getString("title");
        isTest = document.getBoolean("isTest");
        parent = document.getString("parent");
        isUnlocked = document.getBoolean("isUnlocked");
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

}