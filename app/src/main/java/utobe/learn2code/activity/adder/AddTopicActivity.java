package utobe.learn2code.activity.adder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.firestore.FirebaseFirestore;

import utobe.learn2code.R;
import utobe.learn2code.activity.IAbstractActivity;
import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.model.Language;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;

public class AddTopicActivity extends AppCompatActivity implements IAbstractActivity {

    private Language language;
    private EditText name;
    private Switch isTest;

    private Activity gThis = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);

        Bundle extras = getIntent().getExtras();
        language = (Language) entityManager.getEntity(extras.getString(Constants.ABSTRACT_ENTITY_ID));

        name = findViewById(R.id.et_topic_name);
        isTest = findViewById(R.id.sw_is_test);
        Button button_next = findViewById(R.id.button_add_topic_add_pages);
        Button button_back = findViewById(R.id.button_add_topic_back);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    button_next.setEnabled(true);
                } else {
                    button_next.setEnabled(false);
                }
            }
        });

        button_back.setOnClickListener(v -> {
            finish();
        });

        button_next.setOnClickListener(v -> {
            try {
                Topic topic = Topic.buildTopic(name.getText().toString(), isTest.isChecked(), language.getId());
                FirebaseFirestore.getInstance()
                        .collection(Constants.TOPIC_ENTITY_SET_NAME)
                        .add(topic.toMap())
                        .addOnSuccessListener(documentReference -> {
                            try {
                                topic.setId(documentReference.getId());
                                Intent addPageIntent = new Intent(gThis, AddPageActivity.class);
                                addPageIntent.putExtra(Constants.ABSTRACT_ENTITY_ID, topic.getId());

                                startActivity(addPageIntent);
                            } catch (PersistenceException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (PersistenceException e) {
                Snackbar.make(findViewById(R.id.lo_add_topic), "Something went wrong during the topic setting", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}
