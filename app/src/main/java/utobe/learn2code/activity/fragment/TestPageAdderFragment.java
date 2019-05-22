package utobe.learn2code.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import utobe.learn2code.R;
import utobe.learn2code.activity.TableOfContentsActivity;
import utobe.learn2code.activity.adder.AddPageActivity;
import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.model.TestPage;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;

public class TestPageAdderFragment extends Fragment {

    private Topic parent;

    private EditText title;
    private EditText question;
    private CheckBox[] corrects;
    private EditText[] answers;

    private View rootView;

    public TestPageAdderFragment() {
    }

    public static TestPageAdderFragment newInstance(Topic parent) {
        TestPageAdderFragment fragment = new TestPageAdderFragment();
        fragment.parent = parent;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_test_adder, container, false);

        title = rootView.findViewById(R.id.et_test_adder_title);
        question = rootView.findViewById(R.id.et_test_adder_question);
        corrects = new CheckBox[]{
                rootView.findViewById(R.id.cb_test_adder_0),
                rootView.findViewById(R.id.cb_test_adder_1),
                rootView.findViewById(R.id.cb_test_adder_2),
                rootView.findViewById(R.id.cb_test_adder_3)
        };
        answers = new EditText[]{
                rootView.findViewById(R.id.et_test_adder_0),
                rootView.findViewById(R.id.et_test_adder_1),
                rootView.findViewById(R.id.et_test_adder_2),
                rootView.findViewById(R.id.et_test_adder_3)
        };

        FloatingActionButton fabNext = rootView.findViewById(R.id.fab_add_another_test_page);
        FloatingActionButton fabFinish = rootView.findViewById(R.id.fab_finish_test_pages);

        fabNext.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddPageActivity.class);
            intent.putExtra(Constants.ABSTRACT_ENTITY_ID, parent.getId());
            registerElementandStartNewIntent(intent);
        });
        fabFinish.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TableOfContentsActivity.class);
            intent.putExtra(Constants.ABSTRACT_ENTITY_ID, parent.getParent());
            registerElementandStartNewIntent(intent);
        });

        return rootView;
    }

    private boolean validateFileds() {
        if (title.getText().toString().length() == 0)
            return false;

        if (question.getText().toString().length() == 0)
            return false;

        boolean validCheckbox = false;
        for (CheckBox correct : corrects)
            if (correct.isChecked()) {
                validCheckbox = true;
                break;
            }

        if (!validCheckbox)
            return false;

        for (EditText answer : answers)
            if (answer.getText().toString().length() == 0)
                return false;

        return true;
    }

    private void registerElementandStartNewIntent(Intent intent) {
        if (validateFileds()) {
            StringBuilder correctString = new StringBuilder();

            for (int i = 0; i < 4; i++)
                if (corrects[i].isChecked())
                    correctString.append("ABCD".charAt(i));

            Long serialNumber;
            if (parent.getPageNumber() == 0)
                serialNumber = Long.valueOf(0);
            else
                serialNumber = parent.getPages().get(parent.getPageNumber() - 1).getSerialNumber();
            TestPage testPage = TestPage.buildTestPage(
                    parent.getId(),
                    title.getText().toString(),
                    question.getText().toString(),
                    answers[0].getText().toString(),
                    answers[1].getText().toString(),
                    answers[2].getText().toString(),
                    answers[3].getText().toString(),
                    correctString.toString(),
                    serialNumber
            );
            FirebaseFirestore.getInstance()
                    .collection(Constants.PAGE_ENTITY_SET_NAME)
                    .add(testPage.toMap())
                    .addOnSuccessListener(documentReference -> {
                        try {
                            testPage.setId(documentReference.getId());

                            startActivity(intent);

                        } catch (PersistenceException e) {
                            e.printStackTrace();
                        }

                    });

        } else {
            Snackbar.make(rootView, "Empty question, answer or there is no selected correct answer", Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}

