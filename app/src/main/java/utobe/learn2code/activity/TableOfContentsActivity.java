package utobe.learn2code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.adapter.TableOfContentAdapter;
import utobe.learn2code.enititymanager.EntityManager;
import utobe.learn2code.model.Language;
import utobe.learn2code.model.Topic;

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
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Topic> topics = Topic.buildTopics(queryDocumentSnapshots);

                        final TableOfContentAdapter adapter;
                        mAdapter = new TableOfContentAdapter(gThis, topics);

                        mAdapter.setClickListener(new TableOfContentAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                        });

                        view.setAdapter(mAdapter);
                        view.setLayoutManager(new LinearLayoutManager(gThis));

                    }
                });

    }
}
