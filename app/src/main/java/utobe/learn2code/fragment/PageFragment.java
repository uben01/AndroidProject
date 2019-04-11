package utobe.learn2code.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
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
        pageId = getArguments().getString("PageId");
        isTest = getArguments().getBoolean("IsTest");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_topic, container, false);
        TextView textView = rootView.findViewById(R.id.page_text);

        String text = ((Page) EntityManager.getInstance().getEntity(pageId)).getText();
        textView.setText(HtmlCompat.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));

        if (isTest) {
            TestPage page = (TestPage) EntityManager.getInstance().getEntity(pageId);
            View block = rootView.findViewById(R.id.testBlock);
            block.setVisibility(View.VISIBLE);

            ((RadioButton) block.findViewById(R.id.answer_A)).setText(page.getA());
            ((RadioButton) block.findViewById(R.id.answer_B)).setText(page.getB());
            ((RadioButton) block.findViewById(R.id.answer_C)).setText(page.getC());
            ((RadioButton) block.findViewById(R.id.answer_D)).setText(page.getD());
        }

        return rootView;
    }
}
