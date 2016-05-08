package itp341.tiffany.allen.whatsinit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import itp341.tiffany.allen.whatsinit.model.IngredientsSingleton;

/**
 * Created by tiffanyniicole on 4/30/16.
 */
public class IndividualIngredientActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String CATEGORY = "itp341.tiffany.allen.whatsinit.category";
    public static final String BRAND = "itp341.tiffany.allen.whatsinit.brand";
    public static final String PRODUCT = "itp341.tiffany.allen.whatsinit.product";

    LinearLayout ingredientLayout;
    TextView ingredientsTextView;
    TextView ingredientInfoTextView;
    String category;
    String category2;
    String brand;
    String product;
    String ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_ingredient_layout);

        ingredientLayout = (LinearLayout) findViewById(R.id.ingredientLayout);
        ingredientsTextView = (TextView) findViewById(R.id.ingredientTextView);
        ingredientInfoTextView = (TextView) findViewById(R.id.ingredientInfoTextView);

        category = getIntent().getStringExtra(CATEGORY);
        brand = getIntent().getStringExtra(BRAND);
        product = getIntent().getStringExtra(PRODUCT);
        ingredient = IngredientsSingleton.get(getApplicationContext())
                .getIngredient(getIntent().getIntExtra(EXTRA_POSITION, 0));

        ingredientsTextView.setText(ingredient);

        switch (category) {
            case "hair":
                ingredientLayout.setBackgroundResource(R.drawable.hair_background);
                category2 = "Hair";
                break;

            case "cosmetic":
                ingredientLayout.setBackgroundResource(R.drawable.cosmetic_background);
                category2 = "Cosmetics";
                break;

            case  "food":
                ingredientLayout.setBackgroundResource(R.drawable.food_background);
                category2 = "Food";
                break;

            case "cleaning":
                ingredientLayout.setBackgroundResource(R.drawable.cleaning_background);
                category2 = "Cleaning";
                break;
        }

       new getIngredientInfo().execute();
    }

    public class getIngredientInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            JSONObject json;
            try {
                String encodedIngredient = ingredient.toLowerCase();
                encodedIngredient.replace(" ", "_");
                String wikiUrl = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="
                        + encodedIngredient + "&rvprop=content&format=json&rvsection=0&exintro=1";
                URL url = new URL(wikiUrl);

                final URLConnection connection = url.openConnection();

                String temp;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                while ((temp = reader.readLine()) != null) {
                    builder.append(temp);
                }

                System.out.println("Builder string = " + builder.toString());
                json = new JSONObject(builder.toString());

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }catch(JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
