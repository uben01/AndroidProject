package utobe.learn2code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.activity.adder.AddTopicActivity;
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
    private final String logedInUserId = entityManager.getLoggedInUser().getUid();

    // Have to (re)load all time, this activity is active
    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_table_of_contents);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        final Language parentLanguage = (Language) entityManager.getEntity(extras.getString(Constants.ABSTRACT_ENTITY_ID));
        view = findViewById(R.id.rv_topics);

        FloatingActionButton fab = findViewById(R.id.fab_add_topic);
        if (parentLanguage.getCreatedBy().equals(entityManager.getLoggedInUser().getUid()) && !parentLanguage.getPublished()) {
            fab.show();
            fab.setOnClickListener(v -> {
                Intent addTopicIntent = new Intent(gThis, AddTopicActivity.class);
                addTopicIntent.putExtra(Constants.ABSTRACT_ENTITY_ID, parentLanguage.getId());

                startActivity(addTopicIntent);
            });
        } else {
            fab.hide();
        }
        Switch publishedSwitch = findViewById(R.id.sw_published);
        if (parentLanguage.getCreatedBy().equals(entityManager.getLoggedInUser().getUid())) {
            publishedSwitch.setVisibility(View.VISIBLE);
            publishedSwitch.setChecked(parentLanguage.getPublished());

            publishedSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                    FirebaseFirestore.getInstance().collection(Constants.LANGUAGE_ENTITY_SET_NAME)
                            .document(parentLanguage.getId())
                            .update(Constants.LANGUAGE_FIELD_PUBLISHED, isChecked));
        }

        FirebaseFirestore.getInstance().collection(Constants.TOPIC_ENTITY_SET_NAME)
                .whereEqualTo(Constants.TOPIC_FIELD_PARENT, parentLanguage.getId())
                .orderBy(Constants.TOPIC_FIELD_SERIAL_NUMBER)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    try {
                        ArrayList<Topic> topics = Topic.buildTopicsFromDB(queryDocumentSnapshots);
                        ArrayList<String> topicIds = new ArrayList<>();

                        parentLanguage.addTopics(topics);

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

                        FirebaseFirestore.getInstance().collection(Constants.RESULT_ENTITY_SET_NAME)
                                .whereEqualTo(Constants.RESULT_FIELD_USER, logedInUserId)
                                .get()
                                .addOnSuccessListener(resultQuerySnapshot -> {
                                    try {
                                        for (Topic tempTopic : topics)
                                            topicIds.add(tempTopic.getId());

                                        ArrayList<Result> results = Result.buildResultsFromDBforTopics(resultQuerySnapshot, topicIds);

                                        /*
                                         * Set all possible results
                                         * it's usually: all is set or none
                                         * but there is a possibility for adding new topics to existing language
                                         * */
                                        for (Result result : results) {
                                            Topic parent = ((Topic) entityManager.getEntity(result.getTopic()));
                                            parent.setResult(result.getId());
                                        }
                                        for (Topic tempTopic : topics) {
                                            Topic topic = (Topic) entityManager.getEntity(tempTopic.getId());

                                            if (topic.getResult() != null || !topic.getTest())
                                                continue;

                                            Result resultForSelectedTopic = Result.buildResult(logedInUserId, topic.getId());
                                            FirebaseFirestore.getInstance()
                                                    .collection(Constants.RESULT_ENTITY_SET_NAME)
                                                    .add(resultForSelectedTopic.toMap())
                                                    .addOnSuccessListener(documentReference -> {
                                                        //presist element
                                                        try {
                                                            resultForSelectedTopic.setId(documentReference.getId());
                                                            topic.setResult(resultForSelectedTopic.getId());
                                                        } catch (PersistenceException e) {
                                                            Snackbar.make(gThis.findViewById(R.id.lo_language), "Something went wrong during a result setting", Snackbar.LENGTH_LONG)
                                                                    .show();
                                                            e.printStackTrace();
                                                        }
                                                    });
                                        }
                                        mAdapter.notifyDataSetChanged();
                                    } catch (PersistenceException e) {
                                        Snackbar.make(gThis.findViewById(R.id.lo_language), "Something went wrong during a result setting", Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                });
                    } catch (PersistenceException e) {
                        Snackbar.make(gThis.findViewById(R.id.lo_language), "Something went wrong during a topic setting", Snackbar.LENGTH_LONG)
                                .setAction("Retry", v -> recreate())
                                .show();
                    }
                });
    }
}
