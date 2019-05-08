package utobe.learn2code.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.text.HtmlCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import utobe.learn2code.R;
import utobe.learn2code.activity.TopicActivity;
import utobe.learn2code.model.Page;
import utobe.learn2code.model.Result;
import utobe.learn2code.model.TestPage;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;
import utobe.learn2code.util.EntityManager;

public class PageFragment extends Fragment {
    private String pageId;
    private boolean isTest;

    public PageFragment() {
    }


    public static PageFragment newInstance(String pageId, boolean isTest) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ABSTRACT_ENTITY_ID.dbName, pageId);
        args.putBoolean(Constants.TOPIC_FIELD_IS_TEST.dbName, isTest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageId = getArguments().getString(Constants.ABSTRACT_ENTITY_ID.dbName);
        isTest = getArguments().getBoolean(Constants.TOPIC_FIELD_IS_TEST.dbName);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_topic, container, false);
        TextView textView = rootView.findViewById(R.id.txt_page);

        String text = ((Page) EntityManager.getInstance().getEntity(pageId)).getText();
        textView.setText(HtmlCompat.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));

        FloatingActionButton fab = rootView.findViewById(R.id.fab_next_topic);
        fab.setOnClickListener(v -> {
            TopicActivity activity = (TopicActivity) getActivity();
            ViewPager viewPager = ((TopicActivity) getActivity()).getViewPager();
            if (activity.getItemCount() != viewPager.getCurrentItem() + 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            } else {
                activity.finish();
            }
        });

        if (isTest) {
            final TestPage page = (TestPage) EntityManager.getInstance().getEntity(pageId);
            final View block = rootView.findViewById(R.id.sv_test_block);
            block.setVisibility(View.VISIBLE);

            final CheckBox A = block.findViewById(R.id.txt_answer_A);
            A.setText(page.getA());
            final CheckBox B = block.findViewById(R.id.txt_answer_B);
            B.setText(page.getB());
            final CheckBox C = block.findViewById(R.id.txt_answer_C);
            C.setText(page.getC());
            final CheckBox D = block.findViewById(R.id.txt_answer_D);
            D.setText(page.getD());

            final CheckBox[] buttons = {A, B, C, D};

            final String[] chars = {Constants.PAGE_FIELD_A.dbName, Constants.PAGE_FIELD_B.dbName, Constants.PAGE_FIELD_C.dbName, Constants.PAGE_FIELD_D.dbName};
            Button runTest = rootView.findViewById(R.id.button_check);
            runTest.setOnClickListener(v -> {
                int errCounter = 0;
                for (int i = 0; i < buttons.length; i++) {
                    if (new String(page.getCorrectAnswers()).contains(chars[i]) != buttons[i].isChecked()) {
                        if (new String(page.getCorrectAnswers()).contains(chars[i])) {
                            buttons[i].setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
                            // Ha jó lett volna
                        } else {
                            buttons[i].setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                        buttons[i].setError(getString(R.string.checxbox_incorrect));
                        ++errCounter;
                    } else {
                        buttons[i].setError(null);
                    }
                    buttons[i].setEnabled(false);
                }
                page.setSubResult(1 - (errCounter / 4.0));
                ((Result) EntityManager.getInstance().getEntity(((Topic) EntityManager.getInstance().getEntity(page.getParent())).getResult())).updateResult();
            });
        }

        return rootView;
    }
}
