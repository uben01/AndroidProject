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
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import utobe.learn2code.R;
import utobe.learn2code.activity.TableOfContentsActivity;
import utobe.learn2code.activity.adder.AddPageActivity;
import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.model.Page;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;

public class PageAdderFragment extends Fragment {

    private Topic parent;

    private View rootView;
    private EditText title;
    private EditText text;

    public PageAdderFragment() {
    }

    public static PageAdderFragment newInstance(Topic parent) {
        PageAdderFragment fragment = new PageAdderFragment();
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
        rootView = inflater.inflate(R.layout.fragment_page_adder, container, false);

        title = rootView.findViewById(R.id.et_page_adder_title);
        text = rootView.findViewById(R.id.et_page_adder_text);

        FloatingActionButton fabNext = rootView.findViewById(R.id.fab_add_another_page);
        FloatingActionButton fabFinish = rootView.findViewById(R.id.fab_finish_pages);

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

    private boolean validateFileds(EditText title, EditText text) {
        if (title.getText().toString().length() == 0)
            return false;

        return text.getText().toString().length() >= 30;
    }

    private void registerElementandStartNewIntent(Intent intent) {
        if (validateFileds(title, text)) {

            Long serialNumber;
            if (parent.getPageNumber() == 0)
                serialNumber = Long.valueOf(0);
            else
                serialNumber = parent.getPages().get(parent.getPageNumber() - 1).getSerialNumber();

            Page page = Page.buildPage(
                    parent.getId(),
                    title.getText().toString(),
                    text.getText().toString(),
                    serialNumber
            );
            FirebaseFirestore.getInstance()
                    .collection(Constants.PAGE_ENTITY_SET_NAME)
                    .add(page.toMap())
                    .addOnSuccessListener(documentReference -> {
                        try {
                            page.setId(documentReference.getId());

                            startActivity(intent);
                        } catch (PersistenceException e) {
                            e.printStackTrace();
                        }
                    });

        } else {
            Snackbar.make(rootView, "Empty title, or the description of the topic is shorter then 30 characters", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

}
