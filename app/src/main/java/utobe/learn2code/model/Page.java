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

public class Page extends AbstractEntity {
    final String title;
    final String text;
    final String parent;
    Long serialNumber;

    private Double subResult = 0.0;

    Page(QueryDocumentSnapshot document) throws PersistenceException {
        super(document.getId());
        title = document.getString(Constants.PAGE_FIELD_TITLE);
        text = document.getString(Constants.PAGE_FIELD_TEXT);
        parent = document.getString(Constants.PAGE_FIELD_PARENT);
        serialNumber = document.getLong(Constants.PAGE_FIELD_SERIAL_NUMBER);

        if (title == null || text == null || parent == null || serialNumber == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {0}", getId()));

        ((Topic) entityManager.getEntity(parent)).addPage(this);
    }

    Page(String parent, String title, String text, Long serialNumber) {
        this.parent = parent;
        this.title = title;
        this.text = text;
        this.serialNumber = serialNumber;
    }

    public static ArrayList<Page> buildPagesFromDB(QuerySnapshot documents) throws PersistenceException {
        ArrayList<Page> pages = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Page pg = (Page) EntityManager.getInstance().getEntity(document.getId());
            if (pg == null)
                pages.add(new Page(document));
            else
                pages.add(pg);
        }

        return pages;
    }

    public static Page buildPage(String parent, String title, String text, Long serialNumber) {
        return new Page(parent, title, text, serialNumber);
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getParent() {
        return parent;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public Double getSubResult() {
        return subResult;
    }

    public void setSubResult(Double subResult) {
        this.subResult = subResult;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.PAGE_FIELD_PARENT, parent);
        map.put(Constants.PAGE_FIELD_TEXT, text);
        map.put(Constants.PAGE_FIELD_TITLE, title);
        map.put(Constants.PAGE_FIELD_SERIAL_NUMBER, serialNumber);

        return map;
    }
}

