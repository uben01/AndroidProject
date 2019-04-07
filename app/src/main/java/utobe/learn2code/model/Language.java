package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import utobe.learn2code.enititymanager.EntityManager;

public class Language extends AbstractEntity {
    private String name;
    private String iconRef;
    private volatile ArrayList<Topic> topics = new ArrayList<>();

    public Topic getElementAtIndex(int index) {
        return topics.get(index);
    }

    public ArrayList<Topic> getElements() {
        return topics;
    }

    public String getName() {
        return name;
    }

    public String getIconRef() {
        return iconRef;
    }

    public Language(QueryDocumentSnapshot document) {
        super(document.getId());
        name = document.getString("name");
        iconRef = document.getDocumentReference("icon").getPath();

    }

    public static ArrayList<Language> buildLanguages(QuerySnapshot documents) {
        ArrayList<Language> languages = new ArrayList<>();
        EntityManager em = EntityManager.getInstance();
        for (QueryDocumentSnapshot document : documents) {
            Language language = new Language(document);
            languages.add(language);
            em.addEntity(language);
        }

        return languages;
    }

}
