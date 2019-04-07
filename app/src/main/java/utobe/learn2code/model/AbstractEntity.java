package utobe.learn2code.model;

public abstract class AbstractEntity {
    private final String id;

    AbstractEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
