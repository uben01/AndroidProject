package utobe.learn2code.activity.adder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import utobe.learn2code.R;
import utobe.learn2code.activity.IAbstractActivity;
import utobe.learn2code.activity.fragment.TestAdderFragment;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;

public class AddPageActivity extends AppCompatActivity implements IAbstractActivity {
    private Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        Bundle extras = getIntent().getExtras();
        topic = (Topic) entityManager.getEntity(extras.getString(Constants.ABSTRACT_ENTITY_ID));

        if (topic.getTest()) {
            TestAdderFragment fragment = TestAdderFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.fr_add_page, fragment).commit();
        }
    }
}

