package utobe.learn2code;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
public class Language {
    int id;
    String name;
    Bitmap icon;

    public Language(int _id, Context context)
    {
        id = _id;

        // get others from DB by ID
        name = "C++";
        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cpp);
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

}
