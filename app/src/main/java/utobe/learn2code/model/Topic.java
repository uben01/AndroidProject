package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;

import utobe.learn2code.exception.PersistenceException;

public class Topic extends AbstractEntity {
    private final String title;
    private final Boolean isTest;
    private final Boolean isUnlocked;
    private final String parent;
    private final Long serialNumber;

    private ArrayList<Page> pages = null;

    private Topic(QueryDocumentSnapshot document) throws PersistenceException {
        super(document.getId());
        title = document.getString("title");
        isTest = document.getBoolean("isTest");
        parent = document.getString("parent");
        isUnlocked = document.getBoolean("isUnlocked");
        serialNumber = document.getLong("serialNumber");

        if (title == null || isTest == null || parent == null || isUnlocked == null || serialNumber == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {}", getId()));
    }

    public static ArrayList<Topic> buildTopics(QuerySnapshot documents) throws PersistenceException {
        ArrayList<Topic> topics = new ArrayList<>();
        try {
            for (QueryDocumentSnapshot document : documents) {
                Topic topic = new Topic(document);
                topics.add(topic);
            }
        } catch (PersistenceException e) {
            throw new PersistenceException(MessageFormat.format("Error while persisting elements: ", e.getLocalizedMessage()));
        }

        return topics;
    }

    public Double getResult() {
        Double result = 0.0;
        for (Page page : pages) {
            result += page.getResult();
        }
        result /= pages.size();

        return result;
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

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }
}