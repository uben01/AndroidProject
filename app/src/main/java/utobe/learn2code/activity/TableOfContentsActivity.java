package utobe.learn2code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;

import utobe.learn2code.R;
import utobe.learn2code.adapter.TableOfContentAdapter;
import utobe.learn2code.enititymanager.EntityManager;
import utobe.learn2code.model.Language;
import utobe.learn2code.model.Result;
import utobe.learn2code.model.Topic;

import static utobe.learn2code.model.Result.buildResult;

public class TableOfContentsActivity extends AppCompatActivity {

    private final Activity gThis = this;

    private RecyclerView view;
    private TableOfContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_of_contents);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        final Language l = (Language) EntityManager.getInstance().getEntity(extras.getString("id"));
        view = findViewById(R.id.topicsTable);

        FirebaseFirestore.getInstance().collection("topics")
                .whereEqualTo("parent", l.getId())
                .orderBy("serialNumber")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Topic> topics = Topic.buildTopics(queryDocumentSnapshots);
                        ArrayList<String> topicIds = new ArrayList<>();

                        Iterator<Topic> iterator = topics.iterator();
                        while (iterator.hasNext()) {
                            final Topic t = iterator.next();

                            topicIds.add(t.getId());

                            if (!t.getTest())
                                continue;

                            FirebaseFirestore.getInstance().collection("results")
                            .whereEqualTo("topic", t.getId())
                                    .whereEqualTo("user", EntityManager.getInstance().getLoggedInUser().getUid())
                            .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                            if (!queryDocumentSnapshots.isEmpty()) {
                                                // TEST WITH RESULT
                                                t.setResult(buildResult(queryDocumentSnapshots.getDocuments().get(0)).getId());
                                                mAdapter.notifyDataSetChanged();
                                            } else {
                                                final Result result = Result.buildResult(EntityManager.getInstance().getLoggedInUser().getUid(), t.getId());

                                                // TEST WITHOUT RESULT -- HAVE TO ADD A NEW
                                                FirebaseFirestore.getInstance().collection("results")
                                                        .add(result)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                // presist element
                                                                try {
                                                                    result.setId(documentReference.getId());
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }
                                                                t.setResult(result.getId());
                                                                mAdapter.notifyDataSetChanged();
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }

                        mAdapter = new TableOfContentAdapter(gThis, topicIds);
                        mAdapter.setClickListener(new TableOfContentAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Topic selected = mAdapter.getItem(position);

                                Intent intent = new Intent(gThis, TopicActivity.class);
                                intent.putExtra("language", selected.getParent());
                                intent.putExtra("topic", selected.getId());

                                startActivity(intent);
                            }
                        });

                        view.setAdapter(mAdapter);
                        view.setLayoutManager(new LinearLayoutManager(gThis));

                    }
                });

    }
}
