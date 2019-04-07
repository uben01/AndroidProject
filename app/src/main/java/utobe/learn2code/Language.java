package utobe.learn2code;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Language {
    private String id;
    private String name;
    private String iconRef;
    private volatile ArrayList<Topic> topics = new ArrayList<>();

    public class Topic {
        private String id;
        private String title;
        private Boolean isTest;
        private ArrayList<Page> pages;
        private String parent;

        public Topic(QueryDocumentSnapshot document)
        {
            id = document.getId();
            title = document.getString("name");
            isTest = document.getBoolean("isTest");
            parent = document.getString("parent");

            pages = new ArrayList<>();
        }

        public String getTitle() {
            return title;
        }

        public Boolean isUnlocked() {
            return true;
        }

        public Page getPageById(Integer id){
            return pages.get(id);
        }
        public class Page{
            private String title;
            private String text;

            public Page(String _title, String _text)
            {
                title = _title;
                text = _text;
            }

            public String getTitle() {
                return title;
            }

            public String getText() {
                return text;
            }
        }


    }

    public Topic getElementAtIndex(int index) {
        return topics.get(index);
    }

    public ArrayList<Topic> getElements() {
        return topics;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getIconRef() {
        return iconRef;
    }

    public Language(QueryDocumentSnapshot document, Boolean b_buildTopicks) {
        id = document.getId();
        name = document.getString("name");
        iconRef = document.getDocumentReference("icon").getPath();

        if (b_buildTopicks)
            buildTopicks();
    }

    public void buildTopicks()
    {
        ServiceFactory.getFirebaseFirestore().collection("topics")
                .whereEqualTo("parent", this.id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Topic topic = new Topic(document);
                                topics.add(topic);
                                Log.i("ASDA", topics.size() + "");
                            }
                        } else {
                            Log.w("ASD", "hiba");
                        }
                    }
                });
    }

    public static ArrayList<Language> buildLanguages(QuerySnapshot documents) {
        ArrayList<Language> languages = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            languages.add(new Language(document, false));
        }

        return languages;
    }

}
