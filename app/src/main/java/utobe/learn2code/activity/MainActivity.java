package utobe.learn2code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.adapter.LanguageSelectAdapter;
import utobe.learn2code.model.Language;

public class MainActivity extends AppCompatActivity {

    private final Activity gThis = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        final int elementCount = (int) Math.floor(screenWidth / 110.0);


        FirebaseFirestore.getInstance().collection("languages")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Language> languages = Language.buildLanguages(queryDocumentSnapshots);

                        final LanguageSelectAdapter adapter;
                        RecyclerView view = findViewById(R.id.langContainer);
                        view.setLayoutManager(new GridLayoutManager(gThis, elementCount));

                        adapter = new LanguageSelectAdapter(gThis, languages);
                        adapter.setClickListener(new LanguageSelectAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(gThis, TableOfContentsActivity.class);
                                intent.putExtra("id", adapter.getItem(position).getId());
                                intent.putExtra("name", adapter.getItem(position).getName());

                                startActivity(intent);
                            }
                        });
                        view.setAdapter(adapter);
                    }
                });
    }
}

