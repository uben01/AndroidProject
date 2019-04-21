package utobe.learn2code.model;

import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.Exclude;

import java.text.MessageFormat;

import utobe.learn2code.enititymanager.EntityManager;
import utobe.learn2code.exception.PersistenceException;

public abstract class AbstractEntity {
    private String id;

    AbstractEntity(@NotNull String id) {
        this.id = id;

        EntityManager.getInstance().addEntity(this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    // TODO: new Exception type
    public void setId(String id) throws PersistenceException {
        if (this.id != null)
            throw new PersistenceException(MessageFormat.format("Element already persisted with id {}", id));

        this.id = id;

        EntityManager.getInstance().addEntity(this);
    }
}
