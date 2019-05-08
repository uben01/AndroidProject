package utobe.learn2code.util;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import utobe.learn2code.model.AbstractEntity;

public class EntityManager {
    private static EntityManager em = null;
    private final HashMap<String, AbstractEntity> mEntities = new HashMap<>();
    private FirebaseUser loggedInUser;

    public static EntityManager getInstance() {
        if (em == null)
            em = new EntityManager();

        return em;
    }

    public <T extends AbstractEntity> void addEntity(T entity) {
        mEntities.put(entity.getId(), entity);
    }

    public AbstractEntity getEntity(String navProperty) {
        return mEntities.get(navProperty);
    }

    public FirebaseUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(FirebaseUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
