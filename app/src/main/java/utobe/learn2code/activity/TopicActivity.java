package utobe.learn2code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.adapter.TopicAdapter;
import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.model.Page;
import utobe.learn2code.model.TestPage;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;

public class TopicActivity extends AppCompatActivity implements IAbstractActivity {
    private TopicAdapter mTopicAdapter;
    private ViewPager mViewPager;

    private Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_topic);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        topic = (Topic) entityManager.getEntity(extras.getString(Constants.TOPIC_ENTITY_NAME));

        FirebaseFirestore.getInstance().collection(Constants.PAGE_ENTITY_SET_NAME)
                .whereEqualTo(Constants.PAGE_FIELD_PARENT, topic.getId())
                .orderBy(Constants.PAGE_FIELD_SERIAL_NUMBER, Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Page> pages = new ArrayList<>();
                    try {
                        if (!topic.getTest()) {
                            pages.addAll(Page.buildPagesFromDB(queryDocumentSnapshots));
                        } else {
                            pages.addAll(TestPage.buildTestPagesFromDB(queryDocumentSnapshots));
                        }
                        topic.setPages(pages);

                        // Set up the ViewPager with the sections adapter.
                        mViewPager = findViewById(R.id.vp_topic);

                        mTopicAdapter = new TopicAdapter(getSupportFragmentManager(), pages, topic.getTest(), mViewPager);
                        mViewPager.setAdapter(mTopicAdapter);

                        final TabLayout tabLayout = findViewById(R.id.vp_tab_topic);
                        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
                    } catch (PersistenceException e) {
                        //TODO: SnackBar
                    }
                });


    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public int getItemCount() {
        return mTopicAdapter.getCount();
    }
}

