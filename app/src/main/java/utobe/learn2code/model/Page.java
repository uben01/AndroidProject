package utobe.learn2code.model;


import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;

import utobe.learn2code.exception.PersistenceException;

public class Page extends AbstractEntity {
    private final String title;
    private final String text;
    private final String parent;
    private final Long serialNumber;

    private Double result;

    Page(QueryDocumentSnapshot document) throws PersistenceException {
        super(document.getId());
        title = document.getString("title");
        text = document.getString("text");
        parent = document.getString("parent");

        serialNumber = document.getLong("serialNumber");
        if (title == null || text == null || parent == null || serialNumber == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {}", getId()));
    }

    public static ArrayList<Page> buildPages(QuerySnapshot documents) throws PersistenceException {
        ArrayList<Page> pages = new ArrayList<>();
        try {
            for (QueryDocumentSnapshot document : documents) {
                Page page = new Page(document);
                pages.add(page);
            }
        } catch (PersistenceException e) {
            throw new PersistenceException(MessageFormat.format("Error while persisting elements: {}", e.getLocalizedMessage()));
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

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}

