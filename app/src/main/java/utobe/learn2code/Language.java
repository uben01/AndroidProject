package utobe.learn2code;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Language {
    private String id;
    private String name;
    private Bitmap icon;
    private ArrayList<Topic> topics;

    public class Topic {
        private String id;
        private String name;
        private Boolean isTest;
        private ArrayList<Page> pages;

        public Topic(String _name)
        {
            name = _name;
            pages = new ArrayList<>();
        }

        public String getName() {
            return name;
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

    public Language(String _id, Context context)
    {
        id = _id;

        // get others from DB by ID
        name = "C++";
        icon = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_cpp);

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
    public Bitmap getIcon() {
        return icon;
    }

    public Language() {
        topics = new ArrayList<Topic>();
        topics.add(new Topic("A"));
        topics.add(new Topic("B"));
        topics.add(new Topic("C"));
        topics.add(new Topic("D"));
    }

    // TODO: Innen lehessen példányt kérni, minden más constructor private
    public Language getLanguageFromDatabase(String id)
    {
        Language lan = new Language();

        return lan;
    }

}
