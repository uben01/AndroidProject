package utobe.learn2code.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import utobe.learn2code.exception.PersistenceException;
import utobe.learn2code.util.Constants;
import utobe.learn2code.util.EntityManager;

public class Language extends AbstractEntity {
    private final String name;
    private final String icon;
    private final String createdBy;
    private final Boolean published;

    private final ArrayList<Topic> topics = new ArrayList<>();

    private Language(String createdBy, String icon, String name) {
        this.name = name;
        this.createdBy = createdBy;
        this.icon = icon;
        this.published = false;
    }

    private Language(QueryDocumentSnapshot document) throws PersistenceException {
        super(document.getId());

        name = document.getString(Constants.LANGUAGE_FIELD_NAME);
        icon = document.getString(Constants.LANGUAGE_FIELD_ICON);
        createdBy = document.getString(Constants.LANGUAGE_FIELD_CREATED_BY);
        published = document.getBoolean(Constants.LANGUAGE_FIELD_PUBLISHED);

        if (name == null || icon == null || createdBy == null || published == null)
            throw new PersistenceException(MessageFormat.format("Missing mandatory field in object with id {0}", getId()));
    }

    public static ArrayList<Language> buildLanguagesFromDB(QuerySnapshot documents) throws PersistenceException {
        ArrayList<Language> languages = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Language lan = (Language) EntityManager.getInstance().getEntity(document.getId());
            if (lan == null)
                languages.add(new Language(document));
            else
                languages.add(lan);

        }

        return languages;
    }

    public static Language buildLanguageFromDB(QueryDocumentSnapshot document) throws PersistenceException {
        Language lan = (Language) EntityManager.getInstance().getEntity(document.getId());
        if (lan == null)
            return new Language(document);

        return lan;
    }

    public static Language buildLanguage(String createdBy, String icon, String name) {
        return new Language(createdBy, icon, name);
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public Boolean getPublished() {
        return published;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void addTopics(Collection<Topic> topics) {
        this.topics.addAll(topics);
    }

    public int getTopicCount() {
        return topics.size();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.LANGUAGE_FIELD_CREATED_BY, createdBy);
        map.put(Constants.LANGUAGE_FIELD_ICON, icon);
        map.put(Constants.LANGUAGE_FIELD_NAME, name);
        map.put(Constants.LANGUAGE_FIELD_PUBLISHED, published);

        return map;
    }
}
