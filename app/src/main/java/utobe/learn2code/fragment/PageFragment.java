package utobe.learn2code.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import utobe.learn2code.R;
import utobe.learn2code.enititymanager.EntityManager;
import utobe.learn2code.model.Page;

public class PageFragment extends Fragment {
    private int sectionNumber;
    private String pageId;

    public PageFragment() {
    }

    public static PageFragment newInstance(int sectionNumber, String pageId) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("Page", sectionNumber);
        args.putString("PageId", pageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sectionNumber = getArguments().getInt("Page", 0);
        pageId = getArguments().getString("PageId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_topic, container, false);
        TextView textView = rootView.findViewById(R.id.page_text);
        textView.setText(((Page) EntityManager.getInstance().getEntity(pageId)).getText());
        return rootView;
    }
}
