package utobe.learn2code.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import utobe.learn2code.enititymanager.EntityManager;

public class Result extends AbstractEntity {
    private String user;
    private String topic;
    private Double result;

    private Integer pageCounter;

    Result(DocumentSnapshot document) {
        super(document.getId());

        user = document.getString("user");
        topic = document.getString("topic");
        result = document.getDouble("result");
        pageCounter = ((Topic) EntityManager.getInstance().getEntity(topic)).getPageNumber();
    }

    Result(String user, String topic) {
        this.user = user;
        this.topic = topic;
        this.result = 0.0;
        pageCounter = ((Topic) EntityManager.getInstance().getEntity(topic)).getPageNumber();
    }

    public static Result buildResult(DocumentSnapshot document) {
        return new Result(document);
    }

    // have to persist later
    public static Result buildResult(String user, String page) {
        return new Result(user, page);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Double getResult() {
        return result;
    }

    public void updateResult() {
        Topic t = (Topic) EntityManager.getInstance().getEntity(topic);
        Double result = 0.0;
        for( Page p : t.getPages())
        {
            result += p.getSubResult();
        }

        this.result = result/t.getPageNumber();

        FirebaseFirestore.getInstance().collection("results").document(getId())
                .update("result", this.result);
    }
}
