package utobe.learn2code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class TableOfContents extends AppCompatActivity {

    RecyclerView list;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_of_contents);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // Language l = new Language();
        list = findViewById(R.id.table);

        //  mAdapter = new TableOfContentAdapter(l.getElements());
        //   list.setAdapter(mAdapter);

        list.setLayoutManager(new LinearLayoutManager(this));

        // TODO: get table of content for the selected language
        Log.i("ASD", "" + mAdapter.getItemCount());

    }
}
