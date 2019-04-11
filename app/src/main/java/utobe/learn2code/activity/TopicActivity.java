package utobe.learn2code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.adapter.TopicAdapter;
import utobe.learn2code.enititymanager.EntityManager;
import utobe.learn2code.model.Language;
import utobe.learn2code.model.Page;
import utobe.learn2code.model.Topic;

public class TopicActivity extends AppCompatActivity {
    private TopicAdapter mTopicAdapter;
    private ViewPager mViewPager;

    private Language language;
    private Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        language = (Language)EntityManager.getInstance().getEntity(extras.getString("language"));
        topic = (Topic)EntityManager.getInstance().getEntity(extras.getString("topic"));

        Log.i("ASD", topic.getId());
        FirebaseFirestore.getInstance().collection("pages")
                .whereEqualTo("parent", topic.getId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Page> pages = Page.buildPages(queryDocumentSnapshots);

                        // Set up the ViewPager with the sections adapter.
                        mViewPager = findViewById(R.id.container);

                        mTopicAdapter = new TopicAdapter(getSupportFragmentManager(), pages);
                        mViewPager.setAdapter(mTopicAdapter);

                        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int i, float v, int i1) {

                            }

                            @Override
                            public void onPageSelected(int i) {
                                Toast.makeText(TopicActivity.this,
                                        "Selected page position: " + i, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPageScrollStateChanged(int i) {

                            }
                        });
                        final TabLayout tabLayout = findViewById(R.id.view_pager_tab);
                        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
                    }
                });


    }


}

