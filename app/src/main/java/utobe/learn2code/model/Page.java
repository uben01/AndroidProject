package utobe.learn2code.model;


import com.google.firebase.firestore.QueryDocumentSnapshot;

import utobe.learn2code.enititymanager.EntityManager;

public class Page extends AbstractEntity {
    private final String title;
    private final String text;

    private Page(QueryDocumentSnapshot document) {
        super(document.getId());
        title = document.getString("title");
        text = document.getString("text");
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Page buildPage(QueryDocumentSnapshot document) {
        Page page = new Page(document);
        EntityManager.getInstance().addEntity(page);

        return page;
    }
}

