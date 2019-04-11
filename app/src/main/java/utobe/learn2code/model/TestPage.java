package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TestPage extends Page {
    private final String A, B, C, D;
    private final String correct;

    protected TestPage(QueryDocumentSnapshot document) {
        super(document);

        A = document.getString("A");
        B = document.getString("B");
        C = document.getString("C");
        D = document.getString("D");
        correct = document.getString("correct");
    }

    public static ArrayList<TestPage> buildTestPages(QuerySnapshot documents) {
        ArrayList<TestPage> testPages = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            TestPage page = new TestPage(document);
            testPages.add(page);
        }

        return testPages;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    public String getD() {
        return D;
    }

    public String getCorrect() {
        return correct;
    }
}
