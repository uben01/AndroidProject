package utobe.learn2code.model;

import com.google.firebase.database.annotations.NotNull;

import java.text.MessageFormat;

import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.util.EntityManager;

public abstract class AbstractEntity {
    private String id;
    final protected EntityManager entityManager = EntityManager.getInstance();

    AbstractEntity(@NotNull String id) throws PersistenceException {
        this.id = id;

        if (entityManager.getEntity(id) != null) {
            throw new PersistenceException(MessageFormat.format("Document already persisted with id {0}", id));
        }

        entityManager.addEntity(this);
    }

    AbstractEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws PersistenceException {
        if (this.id != null)
            throw new PersistenceException(MessageFormat.format("An element already persisted with id {0}", id));

        this.id = id;

        EntityManager.getInstance().addEntity(this);
    }
}
