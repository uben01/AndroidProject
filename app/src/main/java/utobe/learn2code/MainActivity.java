package utobe.learn2code;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Activity gThis = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // TODO: átnézni
        int screenWidth = displayMetrics.widthPixels;
        int elementCount = (int) Math.floor(screenWidth / 220.0);
        int margin = screenWidth - (elementCount * 200);
        margin = (int)(margin / (elementCount + 1.0));

        GridLayout layout = findViewById(R.id.langContainer);
        layout.setColumnCount(elementCount);

        // TODO: scan database (one query) and distribute info
        for (int i = 0; i < 10; i++) {
            final Integer item_ID = i;   // TODO: DELETE ME
            LinearLayout outer      = new LinearLayout(this);
            ConstraintLayout inner  = new ConstraintLayout(this);
            ImageView icon          = new ImageView(this);
            TextView title          = new TextView(this);

            // Outer
            LinearLayout.LayoutParams outerParams = new LinearLayout.LayoutParams(200, 300);
            outer.setOrientation(LinearLayout.VERTICAL);
            outerParams.setMargins(margin, 10, 0,10);
            outer.setLayoutParams(outerParams);

            // Inner
            ConstraintLayout.LayoutParams innerParams = new ConstraintLayout.LayoutParams(200, 200);
            inner.setLayoutParams(innerParams);

            // Text
            title.setText("C++");
            title.setTextSize(16);
            title.setHeight(100);
            title.setGravity(Gravity.CENTER | Gravity.END);
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            // Image
            icon.setImageResource(R.drawable.ic_cpp);
            ConstraintLayout.LayoutParams iconParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
            icon.setLayoutParams(iconParams);


            // Structure
            inner.addView(icon);
            outer.addView(inner);
            outer.addView(title);

            layout.addView(outer);

            // Listener
            outer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: provide additional informations, if needed
                    Intent intent = new Intent(gThis, TableOfContents.class);
                    intent.putExtra("ID", item_ID.toString());

                    startActivity(intent);
                }
            });
        }

    }
}
