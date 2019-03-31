package utobe.learn2code;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Language {
    private int id;
    private String name;
    private Bitmap icon;
    private ArrayList<Element> tableElements;

    public class Element {
        private String name;

        public Element(String _name) {
            name = _name;
        }

        public String getName() {
            return name;
        }

        public Boolean isUnlocked() {
            return true;
        }

    }

    public Language(int _id, Context context)
    {
        id = _id;

        // get others from DB by ID
        name = "C++";
        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cpp);
    }

    public Element getElementAtIndex(int index) {
        return tableElements.get(index);
    }

    public ArrayList<Element> getElements() {
        return tableElements;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Bitmap getIcon() {
        return icon;
    }

    public Language() {
        tableElements = new ArrayList<Element>();
        tableElements.add(new Element("A"));
        tableElements.add(new Element("B"));
        tableElements.add(new Element("C"));
        tableElements.add(new Element("D"));
    }

}
