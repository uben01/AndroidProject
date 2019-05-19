package utobe.learn2code.activity.adder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

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

        FrameLayout frameLayout = findViewById(R.id.fr_add_page);
        if (topic.getTest()) {
            Fragment fragment = new TestAdderFragment();
            //FragmentTransaction ft = ;

        }
    }
}

