package utobe.learn2code.model;

import com.google.firebase.firestore.Exclude;

import utobe.learn2code.enititymanager.EntityManager;

public abstract class AbstractEntity {
    private String id = null;

    AbstractEntity(String id) {
        this.id = id;

        EntityManager.getInstance().addEntity(this);
    }

    AbstractEntity() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    // TODO: new Exception type
    public void setId(String id) throws Exception {
        if (this.id != null)
            throw new Exception("Element already persisted");
        this.id = id;

        EntityManager.getInstance().addEntity(this);
    }
}
