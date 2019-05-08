package utobe.learn2code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.adapter.LanguageSelectAdapter;
import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.model.Language;

public class LanguageActivity extends AppCompatActivity {

    private final Activity gThis = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_language);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        final int elementCount = (int) Math.floor(screenWidth / 110.0);

        final RecyclerView view = findViewById(R.id.langContainer);

        FirebaseFirestore.getInstance().collection("languages")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    try {
                        ArrayList<Language> languages = new ArrayList<>(Language.buildLanguages(queryDocumentSnapshots));

                        final LanguageSelectAdapter adapter;
                        view.setLayoutManager(new GridLayoutManager(gThis, elementCount));

                        adapter = new LanguageSelectAdapter(gThis, languages);
                        adapter.setClickListener((view1, position) -> {
                            Language selected = adapter.getItem(position);
                            Intent intent = new Intent(gThis, TableOfContentsActivity.class);
                            intent.putExtra("id", selected.getId());

                            startActivity(intent);
                        });
                        view.setAdapter(adapter);
                    } catch (PersistenceException e) {
                        // TODO
                    }
                });
    }
}

