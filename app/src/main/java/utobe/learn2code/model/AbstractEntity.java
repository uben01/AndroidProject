package utobe.learn2code.model;

import com.google.firebase.database.annotations.NotNull;

import java.text.MessageFormat;

import utobe.learn2code.enititymanager.EntityManager;
import utobe.learn2code.exception.PersistenceException;

public abstract class AbstractEntity {
    private String id;
    final EntityManager entityManager = EntityManager.getInstance();

    AbstractEntity(@NotNull String id) {
        this.id = id;

        EntityManager.getInstance().addEntity(this);
    }

    AbstractEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws PersistenceException {
        if (this.id != null)
            throw new PersistenceException(MessageFormat.format("An element already persisted with id {}", id));

        this.id = id;

        EntityManager.getInstance().addEntity(this);
    }
}
