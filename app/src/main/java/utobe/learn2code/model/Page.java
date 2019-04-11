package utobe.learn2code.model;


import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Page extends AbstractEntity {
    private final String title;
    private final String text;
    private final String parent;
    private final Long serialNumber;

    protected Page(QueryDocumentSnapshot document) {
        super(document.getId());
        title = document.getString("title");
        text = document.getString("text");
        parent = document.getString("parent");

        serialNumber = document.getLong("serialNumber");
    }

    public static ArrayList<Page> buildPages(QuerySnapshot documents) {
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

}

