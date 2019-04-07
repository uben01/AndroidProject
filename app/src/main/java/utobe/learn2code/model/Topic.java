package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import utobe.learn2code.enititymanager.EntityManager;

public class Topic extends AbstractEntity {
    private String title;
    private Boolean isTest;
    private ArrayList<Page> pages;
    private String parent;

    private Topic(QueryDocumentSnapshot document) {
        super(document.getId());
        title = document.getString("name");
        isTest = document.getBoolean("isTest");
        parent = document.getString("parent");

        pages = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public Boolean isUnlocked() {
        return true;
    }

    public Page getPageById(Integer id) {
        return pages.get(id);
    }

    public Topic buildTopic(QueryDocumentSnapshot document) {
        Topic topic = new Topic(document);
        EntityManager.getInstance().addEntity(topic);

        return topic;
    }
}