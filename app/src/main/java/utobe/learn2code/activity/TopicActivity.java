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
import utobe.learn2code.model.Language;
import utobe.learn2code.model.Page;
import utobe.learn2code.model.TestPage;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;
import utobe.learn2code.util.EntityManager;

public class TopicActivity extends AppCompatActivity {
    private TopicAdapter mTopicAdapter;
    private ViewPager mViewPager;

    private Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_topic);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        Language language = (Language) EntityManager.getInstance().getEntity(extras.getString(Constants.LANGUAGE_ENTITY_NAME.dbName));
        topic = (Topic) EntityManager.getInstance().getEntity(extras.getString(Constants.TOPIC_ENTITY_NAME.dbName));

        FirebaseFirestore.getInstance().collection(Constants.PAGE_ENTITY_SET_NAME.dbName)
                .whereEqualTo(Constants.PAGE_FIELD_PARENT.dbName, topic.getId())
                .orderBy(Constants.PAGE_FIELD_SERIAL_NUMBER.dbName, Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Page> pages = new ArrayList<>();
                    try {
                        if (!topic.getTest()) {
                            pages.addAll(Page.buildPages(queryDocumentSnapshots));
                        } else {
                            pages.addAll(TestPage.buildTestPages(queryDocumentSnapshots));
                        }
                        topic.setPages(pages);

                        // Set up the ViewPager with the sections adapter.
                        mViewPager = findViewById(R.id.container);

                        mTopicAdapter = new TopicAdapter(getSupportFragmentManager(), pages, topic.getTest(), mViewPager);
                        mViewPager.setAdapter(mTopicAdapter);

                        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int i, float v, int i1) {

                            }

                            @Override
                            public void onPageSelected(int i) {
                            }

                            @Override
                            public void onPageScrollStateChanged(int i) {

                            }
                        });
                        final TabLayout tabLayout = findViewById(R.id.view_pager_tab);
                        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
                    } catch (PersistenceException e) {
                        // TODO
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

