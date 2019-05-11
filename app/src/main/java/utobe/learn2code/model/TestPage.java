package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;

import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.util.Constants;

public class TestPage extends Page {
    private final String A, B, C, D;
    private final char[] correct;

    private TestPage(QueryDocumentSnapshot document) throws PersistenceException {
        super(document);

        A = document.getString(Constants.PAGE_FIELD_A);
        B = document.getString(Constants.PAGE_FIELD_B);
        C = document.getString(Constants.PAGE_FIELD_C);
        D = document.getString(Constants.PAGE_FIELD_D);
        correct = document.getString(Constants.PAGE_FIELD_CORRECT).toCharArray();

        if (A == null || B == null || C == null || D == null || correct == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {}", getId()));
    }

    public static ArrayList<TestPage> buildTestPagesFromDB(QuerySnapshot documents) throws PersistenceException {
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

    public char[] getCorrectAnswers() {
        return correct;
    }
}
