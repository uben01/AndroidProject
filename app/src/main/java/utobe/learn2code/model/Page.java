package utobe.learn2code.model;


import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Page extends AbstractEntity {
    private String title;
    private String text;
    private String parent;
    private Long serialNumber;

    public Page() {}

    private Page(QueryDocumentSnapshot document) {
        super(document.getId());
        title = document.getString("title");
        text = document.getString("text");
        parent = document.getString("parent");

        serialNumber = document.getLong("text");
    }

    public Page buildPage(QueryDocumentSnapshot document) {
        Page page = new Page(document);

        return page;
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

