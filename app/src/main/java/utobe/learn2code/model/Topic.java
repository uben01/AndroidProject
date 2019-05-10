package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import utobe.learn2code.util.Constants;

public class Topic extends AbstractEntity {
    private String title;
    private Boolean isTest;
    private String parent;
    private Long serialNumber;

    private String result = null;

    private ArrayList<Page> pages = new ArrayList<>();

    private Topic(QueryDocumentSnapshot document) {
        super(document.getId());
        title = document.getString(Constants.TOPIC_FIELD_TITLE);
        isTest = document.getBoolean(Constants.TOPIC_FIELD_IS_TEST);
        parent = document.getString(Constants.TOPIC_FIELD_PARENT);
        serialNumber = document.getLong(Constants.TOPIC_FIELD_SERIAL_NUMBER);
    }

    public static ArrayList<Topic> buildTopicsFromDB(QuerySnapshot documents) {
        ArrayList<Topic> topics = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            topics.add(new Topic(document));
        }

        return topics;
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
}