package utobe.learn2code.model;

import utobe.learn2code.enititymanager.EntityManager;

public abstract class AbstractEntity {
    private String id;

    public AbstractEntity(){};

    AbstractEntity(String id) {
        this.id = id;

        EntityManager.getInstance().addEntity(this);
    }

    public String getId() {
        return id;
    }

}
