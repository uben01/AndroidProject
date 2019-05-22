package utobe.learn2code.activity.adder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import utobe.learn2code.R;
import utobe.learn2code.activity.IAbstractActivity;
import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.model.Language;
import utobe.learn2code.util.AfterTextChangedWatcher;
import utobe.learn2code.util.Constants;

public class AddLanguageActivity extends AppCompatActivity implements IAbstractActivity {
    private static final int PICK_FILE_REQUEST = 1;

    private Button button_next;
    private Boolean term_text = false;
    private Boolean term_icon = false;
    private Uri uploadIconUri;
    private final Activity gThis = this;

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

        et_languageName.addTextChangedListener(new AfterTextChangedWatcher() {
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
            String iconName = entry_name.toLowerCase() + ".svg";
            storageRef
                    .child(iconName)
                    .putFile(uploadIconUri, metadata)
                    .addOnSuccessListener(taskSnapshot -> {
                        Language language = Language.buildLanguage(entityManager.getLoggedInUser().getUid(), iconName, entry_name);

                        FirebaseFirestore.getInstance()
                                .collection(Constants.LANGUAGE_ENTITY_SET_NAME)
                                .add(language.toMap())
                                .addOnSuccessListener(documentReference -> {
                                    try {
                                        language.setId(documentReference.getId());

                                        Intent addTopicIntent = new Intent(gThis, AddTopicActivity.class);
                                        addTopicIntent.putExtra(Constants.ABSTRACT_ENTITY_ID, documentReference.getId());

                                        startActivity(addTopicIntent);
                                    } catch (PersistenceException e) {
                                        e.printStackTrace();
                                    }
                                });
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
            } else {
                Snackbar.make(findViewById(R.id.lo_language), "Something went wrong during the file upload", Snackbar.LENGTH_LONG)
                        .show();
            }
        }
    }

}
