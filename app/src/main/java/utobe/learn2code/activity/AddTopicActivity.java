package utobe.learn2code.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import utobe.learn2code.R;
import utobe.learn2code.model.Language;
import utobe.learn2code.util.Constants;

public class AddTopicActivity extends AppCompatActivity implements IAbstractActivity {

    private Language language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);

        Bundle extras = getIntent().getExtras();
        language = (Language) entityManager.getEntity(extras.getString(Constants.ABSTRACT_ENTITY_ID));


    }
}
