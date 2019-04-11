package utobe.learn2code.model;

import utobe.learn2code.enititymanager.EntityManager;

public abstract class AbstractEntity {
    private final String id;

    AbstractEntity(String id) {
        this.id = id;

        EntityManager.getInstance().addEntity(this);
    }

    public String getId() {
        return id;
    }

}
