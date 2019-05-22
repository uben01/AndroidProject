package utobe.learn2code.activity.adder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import utobe.learn2code.R;
import utobe.learn2code.activity.IAbstractActivity;
import utobe.learn2code.activity.fragment.PageAdderFragment;
import utobe.learn2code.activity.fragment.TestPageAdderFragment;
import utobe.learn2code.model.Topic;
import utobe.learn2code.util.Constants;

public class AddPageActivity extends AppCompatActivity implements IAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        Bundle extras = getIntent().getExtras();
        Topic topic = (Topic) entityManager.getEntity(extras.getString(Constants.ABSTRACT_ENTITY_ID));

        Fragment fragment;
        if (topic.getTest()) {
            fragment = TestPageAdderFragment.newInstance(topic);
        } else {
            fragment = PageAdderFragment.newInstance(topic);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fr_add_page, fragment).commit();
    }
}

