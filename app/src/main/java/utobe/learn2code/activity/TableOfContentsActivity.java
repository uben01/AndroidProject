package utobe.learn2code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.adapter.TableOfContentAdapter;
import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.model.Language;
import utobe.learn2code.model.Result;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;

public class TableOfContentsActivity extends AppCompatActivity implements IAbstractActivity {

    private final Activity gThis = this;

    private RecyclerView view;
    private TableOfContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadDataAndSetView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadDataAndSetView();

    }

    private void loadDataAndSetView() {
        setContentView(R.layout.activity_table_of_contents);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        final Language l = (Language) entityManager.getEntity(extras.getString(Constants.ABSTRACT_ENTITY_ID));
        view = findViewById(R.id.rv_topics);

        FloatingActionButton fab = findViewById(R.id.fab_add_topic);
        if (l.getCreatedBy().equals(entityManager.getLoggedInUser().getUid())) {
            fab.show();
            fab.setOnClickListener(v -> {

            });
        } else {
            fab.hide();
        }

        FirebaseFirestore.getInstance().collection(Constants.TOPIC_ENTITY_SET_NAME)
                .whereEqualTo(Constants.TOPIC_FIELD_PARENT, l.getId())
                .orderBy(Constants.TOPIC_FIELD_SERIAL_NUMBER)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Topic> topics = Topic.buildTopicsFromDB(queryDocumentSnapshots);
                    ArrayList<String> topicIds = new ArrayList<>();

                    mAdapter = new TableOfContentAdapter(gThis, topicIds);

                    mAdapter.setClickListener((view, position) -> {
                        Topic selected = mAdapter.getItem(position);

                        Intent selectTopicIntent = new Intent(gThis, TopicActivity.class)
                                .putExtra(Constants.LANGUAGE_ENTITY_NAME, selected.getParent())
                                .putExtra(Constants.TOPIC_ENTITY_NAME, selected.getId());

                        startActivity(selectTopicIntent);
                    });

                    view.setAdapter(mAdapter);
                    view.setLayoutManager(new LinearLayoutManager(gThis));

                    final String logedInUserId = entityManager.getLoggedInUser().getUid();
                    FirebaseFirestore.getInstance().collection(Constants.RESULT_ENTITY_SET_NAME)
                            .whereEqualTo(Constants.RESULT_FIELD_USER, logedInUserId)
                            .get()
                            .addOnSuccessListener(resultQuerySnapshot -> {
                                ArrayList<Result> results = Result.buildResultsFromDB(resultQuerySnapshot);

                                for (Result result : results) {
                                    Topic parent = ((Topic) entityManager.getEntity(result.getTopic()));
                                    parent.setResult(result.getId());
                                }
                                for (Topic tempTopic : topics) {
                                    topicIds.add(tempTopic.getId());
                                    Topic topic = (Topic) entityManager.getEntity(tempTopic.getId());

                                    if (topic.getResult() != null || !topic.getTest())
                                        continue;

                                    Result resultForSelectedTopic = Result.buildResult(logedInUserId, topic.getId());
                                    FirebaseFirestore.getInstance().collection(Constants.RESULT_ENTITY_SET_NAME)
                                            .add(resultForSelectedTopic)
                                            .addOnSuccessListener(documentReference -> {
                                                // presist element
                                                try {
                                                    resultForSelectedTopic.setId(documentReference.getId());
                                                    topic.setResult(resultForSelectedTopic.getId());
                                                } catch (PersistenceException e) {
                                                    //TODO: SnackBar
                                                    e.printStackTrace();
                                                }
                                            });

                                }
                                mAdapter.notifyDataSetChanged();

                            });
                });
    }
}
