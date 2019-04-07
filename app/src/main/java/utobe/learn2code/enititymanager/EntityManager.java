package utobe.learn2code.enititymanager;

import java.util.HashMap;

import utobe.learn2code.model.AbstractEntity;

public class EntityManager {
    private static EntityManager em = null;
    private final HashMap<String, AbstractEntity> mEntities = new HashMap<>();

    public static EntityManager getInstance() {
        if (em == null)
            em = new EntityManager();

        return em;
    }

    public <T extends AbstractEntity> void addEntity(T entity) {
        mEntities.put(entity.getId(), entity);
    }

    public AbstractEntity getEntity(String id) {
        return mEntities.get(id);
    }
}
