package utobe.learn2code.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import utobe.learn2code.R;
import utobe.learn2code.util.Constants;

public class AddLanguageActivity extends AppCompatActivity implements IAbstractActivity {
    private static final int PICK_FILE_REQUEST = 1;

    Button button_next;
    Boolean term_text = false;
    Boolean term_icon = false;
    private Uri uploadIconUri;

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

        EditText et_languageName = findViewById(R.id.et_lang_name);
        button_next = findViewById(R.id.button_add_language_next);

        et_languageName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    term_text = true;
                    if (term_icon) {
                        button_next.setEnabled(true);
                    }
                } else {
                    term_text = false;
                    button_next.setEnabled(false);
                }
            }
        });


        button_next.setOnClickListener(v -> {
            if (!term_icon || !term_text)
                return;

            StorageReference storageRef = FirebaseStorage.getInstance().getReference("icons/");

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/svg+xml")
                    .build();

            String entry_name = et_languageName.getText().toString();
            String entry_iconName = entry_name.toLowerCase() + ".svg";
            storageRef
                    .child(entry_iconName)
                    .putFile(uploadIconUri, metadata)
                    .addOnSuccessListener(taskSnapshot -> {
                        Map<String, Object> language = new HashMap<>();
                        language.put(Constants.LANGUAGE_FIELD_CREATED_BY, entityManager.getLoggedInUser().getUid());
                        language.put(Constants.LANGUAGE_FIELD_ICON, "/icons/" + entry_iconName);
                        language.put(Constants.LANGUAGE_FIELD_NAME, entry_name);
                        language.put(Constants.LANGUAGE_FIELD_PUBLISHED, false);

                        FirebaseFirestore.getInstance()
                                .collection(Constants.LANGUAGE_ENTITY_SET_NAME)
                                .document()
                                .set(language);

                    });
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FILE_REQUEST) {
            if (resultCode == RESULT_OK) {
                uploadIconUri = data.getData();
                ((TextView) findViewById(R.id.txt_upload_svg)).setText(uploadIconUri.getPath().substring(uploadIconUri.getPath().lastIndexOf('/') + 1));
                term_icon = true;
                if (term_text) {
                    button_next.setEnabled(true);
                }
            }
        }
    }

}
