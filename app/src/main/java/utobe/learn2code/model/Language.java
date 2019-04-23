package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;

import utobe.learn2code.exception.PersistenceException;

public class Language extends AbstractEntity {
    private final String name;
    private final String icon;

    private Language(QueryDocumentSnapshot document) throws PersistenceException {
        super(document.getId());

        name = document.getString("name");
        icon = document.getString("icon");

        if (name == null || icon == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {}", getId()));
    }

    public static ArrayList<Language> buildLanguages(QuerySnapshot documents) throws PersistenceException {
        ArrayList<Language> languages = new ArrayList<>();
        try {
            for (QueryDocumentSnapshot document : documents) {
                Language language = new Language(document);
                languages.add(language);
            }

        } catch (PersistenceException e) {
            throw new PersistenceException(MessageFormat.format("Error while persisting elements: {}", e.getLocalizedMessage()));
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
