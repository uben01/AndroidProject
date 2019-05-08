package utobe.learn2code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import utobe.learn2code.R;

public class AddLanguageActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_language);

        ImageButton button_upload = findViewById(R.id.button_upload);
        button_upload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("image/svg+xml");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Choose SVG to Upload..."), PICK_FILE_REQUEST);

        });
    }
}
