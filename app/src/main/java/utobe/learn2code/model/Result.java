package utobe.learn2code.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import utobe.learn2code.util.Constants;

public class Result extends AbstractEntity {
    private String user;
    private String topic;
    private Double result;

    private Result(DocumentSnapshot document) {
        super(document.getId());

        user = document.getString(Constants.RESULT_FIELD_USER);
        topic = document.getString(Constants.RESULT_FIELD_TOPIC);
        result = document.getDouble(Constants.RESULT_FIELD_RESULT);
    }

    private Result(String user, String topic) {
        this.user = user;
        this.topic = topic;
        this.result = 0.0;
    }

    public Result() {
    }

    // have to persist later
    public static Result buildResult(String user, String topic) {
        return new Result(user, topic);
    }

    public static ArrayList<Result> buildResultsFromDB(QuerySnapshot querySnapshot) {
        ArrayList<Result> results = new ArrayList<>();
        for (DocumentSnapshot documentSnapshot : querySnapshot) {
            results.add(new Result(documentSnapshot));
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
}
