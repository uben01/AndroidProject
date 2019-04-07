package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Language extends AbstractEntity {
    private final String name;
    private final String iconRef;

    public String getName() {
        return name;
    }

    public String getIconRef() {
        return iconRef;
    }


    private Language(QueryDocumentSnapshot document) {
        super(document.getId());
        name = document.getString("name");
        iconRef = document.getDocumentReference("icon").getPath();
    }

    public static ArrayList<Language> buildLanguages(QuerySnapshot documents) {
        ArrayList<Language> languages = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Language language = new Language(document);
            languages.add(language);
        }

        return languages;
    }

}
