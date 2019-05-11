package utobe.learn2code.model;


import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;

import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.util.Constants;

public class Page extends AbstractEntity {
    private final String title;
    private final String text;
    private final String parent;
    private final Long serialNumber;

    private Double subResult = 0.0;

    Page(QueryDocumentSnapshot document) throws PersistenceException {
        super(document.getId());
        title = document.getString(Constants.PAGE_FIELD_TITLE);
        text = document.getString(Constants.PAGE_FIELD_TEXT);
        parent = document.getString(Constants.PAGE_FIELD_PARENT);
        serialNumber = document.getLong(Constants.PAGE_FIELD_SERIAL_NUMBER);

        if (title == null || text == null || parent == null || serialNumber == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {}", getId()));

        ((Topic) entityManager.getEntity(parent)).addPage(this);
    }

    public static ArrayList<Page> buildPagesFromDB(QuerySnapshot documents) throws PersistenceException {
        ArrayList<Page> pages = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Page page = new Page(document);
            pages.add(page);
        }

        return pages;
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
}

