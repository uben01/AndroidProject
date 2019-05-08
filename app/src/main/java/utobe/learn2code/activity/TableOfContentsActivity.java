package utobe.learn2code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.adapter.TableOfContentAdapter;
import utobe.learn2code.model.Language;
import utobe.learn2code.model.Result;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;
import utobe.learn2code.util.EntityManager;

import static utobe.learn2code.model.Result.buildResult;

public class TableOfContentsActivity extends AppCompatActivity {

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

        final Language l = (Language) EntityManager.getInstance().getEntity(extras.getString(Constants.ABSTRACT_ENTITY_ID.dbName));
        view = findViewById(R.id.rv_topics);

        FirebaseFirestore.getInstance().collection(Constants.TOPIC_ENTITY_SET_NAME.dbName)
                .whereEqualTo(Constants.TOPIC_FIELD_PARENT.dbName, l.getId())
                .orderBy(Constants.TOPIC_FIELD_SERIAL_NUMBER.dbName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Topic> topics = Topic.buildTopics(queryDocumentSnapshots);
                    ArrayList<String> topicIds = new ArrayList<>();

                    mAdapter = new TableOfContentAdapter(gThis, topicIds);

                    mAdapter.setClickListener((view, position) -> {
                        Topic selected = mAdapter.getItem(position);

                        Intent intent1 = new Intent(gThis, TopicActivity.class)
                                .putExtra(Constants.LANGUAGE_ENTITY_NAME.dbName, selected.getParent())
                                .putExtra(Constants.TOPIC_ENTITY_NAME.dbName, selected.getId());

                        startActivity(intent1);
                    });

                    view.setAdapter(mAdapter);
                    view.setLayoutManager(new LinearLayoutManager(gThis));

                    for (Topic t : topics) {
                        topicIds.add(t.getId());

                        if (!t.getTest())
                            continue;

                        FirebaseFirestore.getInstance().collection(Constants.RESULT_ENTITY_SET_NAME.dbName)
                                .whereEqualTo(Constants.RESULT_FIELD_TOPIC.dbName, t.getId())
                                .whereEqualTo(Constants.RESULT_FIELD_USER.dbName, EntityManager.getInstance().getLoggedInUser().getUid())
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots1 -> {

                                    if (!queryDocumentSnapshots1.isEmpty()) {
                                        // TEST WITH RESULT
                                        t.setResult(buildResult(queryDocumentSnapshots1.getDocuments().get(0)).getId());
                                    } else {
                                        final Result result = buildResult(EntityManager.getInstance().getLoggedInUser().getUid(), t.getId());

                                        // TEST WITHOUT RESULT -- HAVE TO ADD A NEW
                                        FirebaseFirestore.getInstance().collection(Constants.RESULT_ENTITY_SET_NAME.dbName)
                                                .add(result)
                                                .addOnSuccessListener(documentReference -> {
                                                    // presist element
                                                    try {
                                                        result.setId(documentReference.getId());
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    t.setResult(result.getId());
                                                });
                                    }
                                    mAdapter.notifyDataSetChanged();
                                });
                    }

                });
    }
}
