package utobe.learn2code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.adapter.LanguageSelectAdapter;
import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.model.Language;
import utobe.learn2code.util.Constants;

public class LanguageActivity extends AppCompatActivity implements IAbstractActivity {

    private final Activity gThis = this;
    LanguageSelectAdapter adapter;
    ArrayList<Language> languages;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_language);

        /*
            PUBLISHED
            or
            NOT PUBLISHED but CREATED BY <ME>
         */
        FirebaseFirestore.getInstance().collection(Constants.LANGUAGE_ENTITY_SET_NAME)
                .whereEqualTo(Constants.LANGUAGE_FIELD_PUBLISHED, true)
                .get()
                .addOnSuccessListener(querySnapshots -> {
                    addLanguagesAndNotify(querySnapshots);

                });

        FirebaseFirestore.getInstance().collection(Constants.LANGUAGE_ENTITY_SET_NAME)
                .whereEqualTo(Constants.LANGUAGE_FIELD_PUBLISHED, false)
                .whereEqualTo(Constants.LANGUAGE_FIELD_CREATED_BY, entityManager.getLoggedInUser().getUid())
                .get()
                .addOnSuccessListener(querySnapshots2 -> {
                    addLanguagesAndNotify(querySnapshots2);
                });


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        final float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        final int elementsInRow = (int) Math.floor(screenWidth / 110.0);

        fab = findViewById(R.id.fab_add_language);
        final RecyclerView view = findViewById(R.id.rv_language);

        languages = new ArrayList<>();
        adapter = new LanguageSelectAdapter(gThis, languages);
        view.setLayoutManager(new GridLayoutManager(gThis, elementsInRow));

        view.setAdapter(adapter);
        adapter.setClickListener((view1, position) -> {
            Language selected = adapter.getItem(position);
            Intent intent = new Intent(gThis, TableOfContentsActivity.class);
            intent.putExtra(Constants.ABSTRACT_ENTITY_ID, selected.getId());

            startActivity(intent);
        });

        fab.setOnClickListener(v -> {
            startActivity(new Intent(gThis, AddLanguageActivity.class));
        });

    }

    private synchronized void addLanguagesAndNotify(QuerySnapshot querySnapshots) {
        try {
            int numOfLanguages = languages.size();
            languages.addAll(Language.buildLanguagesFromDB(querySnapshots));
            adapter.notifyItemRangeInserted(numOfLanguages, languages.size());
        } catch (PersistenceException e) {
            //TODO: SnackBar
        }
    }
}

