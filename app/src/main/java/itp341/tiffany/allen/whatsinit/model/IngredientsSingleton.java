package itp341.tiffany.allen.whatsinit.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by tiffanyniicole on 5/7/16.
 */
public class IngredientsSingleton {
    private ArrayList<String> mIngredients;

    private Context mAppContext;
    private static IngredientsSingleton sIngredients;

    public IngredientsSingleton(Context c) {
        mAppContext = c;
        mIngredients = new ArrayList<>();
    }

    public static IngredientsSingleton get (Context c) {
        if(sIngredients == null) {
            sIngredients = new IngredientsSingleton(c.getApplicationContext());
        }

        return sIngredients;
    }

    public ArrayList<String> getIngredient () { return mIngredients; }

    public String getIngredient (int index) { return mIngredients.get(index); }

    public void addIngredient (String n) {
        mIngredients.add(n);
    }

    public void removeIngredient (int position) {
        if(position >=0 && position < mIngredients.size())
            mIngredients.remove(position);
    }

    public void updateIngredient (int position, String n) {
        if (position >= 0 && position < mIngredients.size()){
            mIngredients.set(position, n);
        }
    }

    public int getSize() {return mIngredients.size();}

    public void clear() {
        mIngredients.clear();
    }

}
