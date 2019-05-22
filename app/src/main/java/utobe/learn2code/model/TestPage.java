package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.util.Constants;
import utobe.learn2code.util.EntityManager;

public class TestPage extends Page {
    private final String A, B, C, D;
    private final String correct;

    private TestPage(QueryDocumentSnapshot document) throws PersistenceException {
        super(document);

        A = document.getString(Constants.PAGE_FIELD_A);
        B = document.getString(Constants.PAGE_FIELD_B);
        C = document.getString(Constants.PAGE_FIELD_C);
        D = document.getString(Constants.PAGE_FIELD_D);
        correct = document.getString(Constants.PAGE_FIELD_CORRECT);

        if (A == null || B == null || C == null || D == null || correct == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {0}", getId()));
    }

    private TestPage(String parent, String title, String question, String a, String b, String c, String d, String correct, Long serialNumber) {
        super(parent, title, question, serialNumber);

        A = a;
        B = b;
        C = c;
        D = d;
        this.correct = correct;
        this.serialNumber = serialNumber;
    }

    public static ArrayList<TestPage> buildTestPagesFromDB(QuerySnapshot documents) throws PersistenceException {
        ArrayList<TestPage> testPages = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            TestPage tpage = (TestPage) EntityManager.getInstance().getEntity(document.getId());
            if (tpage == null)
                testPages.add(new TestPage(document));
            else
                testPages.add(tpage);

        }

        return testPages;
    }

    public static TestPage buildTestPage(String parent, String title, String question, String A, String B, String C, String D, String correct, Long serialNumber) {
        return new TestPage(parent, title, question, A, B, C, D, correct, serialNumber);
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

    public String getCorrectAnswers() {
        return correct;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.PAGE_FIELD_A, A);
        map.put(Constants.PAGE_FIELD_B, B);
        map.put(Constants.PAGE_FIELD_C, C);
        map.put(Constants.PAGE_FIELD_D, D);
        map.put(Constants.PAGE_FIELD_CORRECT, correct);
        map.put(Constants.PAGE_FIELD_PARENT, parent);
        map.put(Constants.PAGE_FIELD_TEXT, text);
        map.put(Constants.PAGE_FIELD_TITLE, title);
        map.put(Constants.PAGE_FIELD_SERIAL_NUMBER, serialNumber);

        return map;
    }
}
