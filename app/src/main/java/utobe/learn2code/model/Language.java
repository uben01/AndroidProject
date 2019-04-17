package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Language extends AbstractEntity {
    private String name;
    private String icon;

    private Language(QueryDocumentSnapshot document) {
        super(document.getId());
        name = document.getString("name");
        icon = document.getString("icon");
    }

    public static ArrayList<Language> buildLanguages(QuerySnapshot documents) {
        ArrayList<Language> languages = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Language language = new Language(document);
            languages.add(language);
        }

        return languages;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
