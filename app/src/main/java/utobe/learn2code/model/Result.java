package utobe.learn2code.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.util.Constants;
import utobe.learn2code.util.EntityManager;

public class Result extends AbstractEntity {
    private String user;
    private String topic;
    private Double result;

    private Result(DocumentSnapshot document) throws PersistenceException {
        super(document.getId());

        user = document.getString(Constants.RESULT_FIELD_USER);
        topic = document.getString(Constants.RESULT_FIELD_TOPIC);
        result = document.getDouble(Constants.RESULT_FIELD_RESULT);

        if (user == null || topic == null || result == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {0}", getId()));

        ((Topic) entityManager.getEntity(topic)).setResult(this.getId());
    }

    private Result(String user, String topic) throws PersistenceException {
        this.user = user;
        this.topic = topic;
        this.result = 0.0;

        if (user == null || topic == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {0}", getId()));
    }

    // have to persist later
    public static Result buildResult(String user, String topic) throws PersistenceException {
        return new Result(user, topic);
    }

    public static ArrayList<Result> buildResultsFromDB(QuerySnapshot querySnapshot) throws PersistenceException {
        ArrayList<Result> results = new ArrayList<>();
        for (DocumentSnapshot documentSnapshot : querySnapshot) {
            Result res = (Result) EntityManager.getInstance().getEntity(documentSnapshot.getId());
            if (res == null)
                results.add(new Result(documentSnapshot));
            else
                results.add(res);
        }

        return results;
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

    public void setResult(Double result) {
        this.result = result;
    }

    public void updateResult() {
        Topic t = (Topic) entityManager.getEntity(topic);
        Double result = 0.0;
        for( Page p : t.getPages())
        {
            result += p.getSubResult();
        }

        this.result = result/t.getPageNumber();

        FirebaseFirestore.getInstance().collection("results").document(getId())
                .update("result", this.result);
    }

    // Freamwork serialization doesn't work properly with this class
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.RESULT_FIELD_USER, user);
        map.put(Constants.RESULT_FIELD_RESULT, result);
        map.put(Constants.RESULT_FIELD_TOPIC, topic);

        return map;
    }
}
