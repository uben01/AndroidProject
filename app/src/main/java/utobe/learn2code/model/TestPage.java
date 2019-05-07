package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;

import utobe.learn2code.exception.PersistenceException;

public class TestPage extends Page {
    private final String A, B, C, D;
    private final char[] correct;

    private TestPage(QueryDocumentSnapshot document) throws PersistenceException {
        super(document);

        A = document.getString("A");
        B = document.getString("B");
        C = document.getString("C");
        D = document.getString("D");
        correct = document.getString("correct").toCharArray();

        if (A == null || B == null || C == null || D == null || correct == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {}", getId()));
    }

    public static ArrayList<TestPage> buildTestPages(QuerySnapshot documents) throws PersistenceException {
        ArrayList<TestPage> testPages = new ArrayList<>();
        try {
            for (QueryDocumentSnapshot document : documents) {
                TestPage page = new TestPage(document);
                testPages.add(page);
            }
        } catch (PersistenceException e) {
            throw new PersistenceException(MessageFormat.format("Error while persisting elements: {}", e.getLocalizedMessage()));
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

    public char[] getCorrectAnswers() {
        return correct;
    }
}
