package utobe.learn2code.model;

import com.google.firebase.firestore.DocumentSnapshot;

public class Result extends AbstractEntity {
    private String user;
    private String page;
    private Double result;

    protected Result(DocumentSnapshot document) {
        super(document.getId());

        user = document.getString("user");
        page = document.getString("topic");
        result = document.getDouble("result");
    }

    protected Result(String user, String page) {
        this.user = user;
        this.page = page;
        this.result = 0.0;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
