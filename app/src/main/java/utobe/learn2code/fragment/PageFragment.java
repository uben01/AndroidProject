package utobe.learn2code.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import utobe.learn2code.R;
import utobe.learn2code.enititymanager.EntityManager;
import utobe.learn2code.model.Page;
import utobe.learn2code.model.TestPage;

public class PageFragment extends Fragment {
    private String pageId;
    private boolean isTest;

    public PageFragment() {
    }


    public static PageFragment newInstance(String pageId, boolean isTest) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString("PageId", pageId);
        args.putBoolean("IsTest", isTest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        pageId = getArguments().getString("PageId");
        isTest = getArguments().getBoolean("IsTest");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_topic, container, false);
        TextView textView = rootView.findViewById(R.id.page_text);

        String text = ((Page) EntityManager.getInstance().getEntity(pageId)).getText();
        textView.setText(HtmlCompat.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));

        if (isTest) {
            final TestPage page = (TestPage) EntityManager.getInstance().getEntity(pageId);
            final View block = rootView.findViewById(R.id.testBlock);
            block.setVisibility(View.VISIBLE);

            final CheckBox A = block.findViewById(R.id.answer_A);
            A.setText(page.getA());
            final CheckBox B = block.findViewById(R.id.answer_B);
            B.setText(page.getB());
            final CheckBox C = block.findViewById(R.id.answer_C);
            C.setText(page.getC());
            final CheckBox D = block.findViewById(R.id.answer_D);
            D.setText(page.getD());

            final CheckBox[] checkBoxes = {A, B, C, D};

            final String[] optionChars = {"A", "B", "C", "D"};
            Button runTest = rootView.findViewById(R.id.button_check);
            runTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int errorCounter = 0;
                    for (int i = 0; i < checkBoxes.length; i++) {
                        if (page.getCorrectAnswers().contains(optionChars[i]) != checkBoxes[i].isChecked()) {
                            if (page.getCorrectAnswers().contains(optionChars[i])) {
                                checkBoxes[i].setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
                            } else {
                                checkBoxes[i].setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            }
                            checkBoxes[i].setError(getString(R.string.checxbox_incorrect));
                            ++errorCounter;
                        } else {
                            checkBoxes[i].setError(null);
                        }
                        checkBoxes[i].setEnabled(false);
                    }

                    // Set result
                    Page page = (Page) EntityManager.getInstance().getEntity(pageId);
                    page.setResult(errorCounter / 4.0);

                }
            });
        }

        return rootView;
    }
}
