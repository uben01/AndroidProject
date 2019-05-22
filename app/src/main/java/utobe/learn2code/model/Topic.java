package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.util.Constants;
import utobe.learn2code.util.EntityManager;

public class Topic extends AbstractEntity {
    private String title;
    private Boolean isTest;
    private String parent;
    private Long serialNumber;

    private String result = null;

    private ArrayList<Page> pages = new ArrayList<>();

    private Topic(QueryDocumentSnapshot document) throws PersistenceException {
        super(document.getId());
        title = document.getString(Constants.TOPIC_FIELD_TITLE);
        isTest = document.getBoolean(Constants.TOPIC_FIELD_IS_TEST);
        parent = document.getString(Constants.TOPIC_FIELD_PARENT);
        serialNumber = document.getLong(Constants.TOPIC_FIELD_SERIAL_NUMBER);

        if (title == null || isTest == null || parent == null || serialNumber == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {0}", getId()));

    }

    private Topic(String title, Boolean isTest, String parent) throws PersistenceException {
        this.title = title;
        this.isTest = isTest;
        this.parent = parent;
        this.serialNumber = (long) ((Language) entityManager.getEntity(parent)).getTopicCount();

        if (title == null || isTest == null || parent == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {0}", getId()));
    }

    public static ArrayList<Topic> buildTopicsFromDB(QuerySnapshot documents) throws PersistenceException {
        ArrayList<Topic> topics = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Topic top = (Topic) EntityManager.getInstance().getEntity(document.getId());
            if (top == null)
                topics.add(new Topic(document));
            else
                topics.add(top);
        }

        return topics;
    }

    public static Topic buildTopic(String title, Boolean isTest, String parent) throws PersistenceException {
        return new Topic(title, isTest, parent);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String resultID) {
        result = resultID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getTest() {
        return isTest;
    }

    public void setTest(Boolean test) {
        isTest = test;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ArrayList<? extends Page> getPages() {
        return pages;
    }

    public <T extends Page> void addPage(T page) {
        pages.add(page);
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }

    public Integer getPageNumber(){
        if(pages == null)
            return 0;
        return pages.size();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.TOPIC_FIELD_IS_TEST, isTest);
        map.put(Constants.TOPIC_FIELD_PARENT, parent);
        map.put(Constants.TOPIC_FIELD_SERIAL_NUMBER, serialNumber);
        map.put(Constants.TOPIC_FIELD_TITLE, title);

        return map;
    }
}