package itp341.tiffany.allen.whatsinit;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import itp341.tiffany.allen.whatsinit.model.IngredientsSingleton;
import itp341.tiffany.allen.whatsinit.model.Product;
import itp341.tiffany.allen.whatsinit.model.ProductAdapter;

/**
 * Created by tiffanyniicole on 5/6/16.
 */
public class ProductsActivity extends AppCompatActivity{
    public static final String CATEGORY = "itp341.tiffany.allen.whatsinit.category";
    public static final String BRAND = "itp341.tiffany.allen.whatsinit.brand";
    ListView productListView;
    LinearLayout productLayout;
    ArrayList<Product> products;
    ArrayList<Object> listImages;
    ArrayList<String> ingredientsList;
    ProductAdapter adapter;

    String category;
    String category2;
    String brand;
    String p;
    String search = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_layout);
        Firebase.setAndroidContext(this);

        category = getIntent().getStringExtra(CATEGORY);
        brand = getIntent().getStringExtra(BRAND);

        productLayout = (LinearLayout) findViewById(R.id.productLayout);
        productListView = (ListView) findViewById(R.id.ingredientsListView);
        ingredientsList = new ArrayList<String>();
        products = new ArrayList<Product>();

        //Setting appropriate background
        switch (category) {
            case "hair":
                productLayout.setBackgroundResource(R.drawable.hair_background);
                category2 = "Hair";
                break;

            case "cosmetic":
                productLayout.setBackgroundResource(R.drawable.cosmetic_background);
                category2 = "Cosmetics";
                break;

            case  "food":
                productLayout.setBackgroundResource(R.drawable.food_background);
                category2 = "Food";
                break;

            case "cleaning":
                productLayout.setBackgroundResource(R.drawable.cleaning_background);
                category2 = "Cleaning";
                break;
        }

        doFirebase();

        //Set on click listener for listview of products
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When item in listview is clicked go to ingredients activity and display ingredients
                IngredientsSingleton.get(getApplicationContext()).clear();
                Intent i = new Intent(getApplicationContext(),
                        IngredientsActivity.class
                );
                final Firebase ref = new Firebase(getResources().getString(R.string.Firebase_url));
                ref.child(category2).child(brand).child(products.get(position).getName())
                        .child("Ingredients")
                        .addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                String ingredient = dataSnapshot.getKey();
                                System.out.println("Ingredient =>" + ingredient);
                                IngredientsSingleton.get(getApplicationContext()).addIngredient(ingredient);
                                System.out.println("IngredientsList size =" + ingredientsList.size());
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                //Send necessary info to get  ingredients info from firebase
                i.putExtra(IngredientsActivity.EXTRA_POSITION, position);
                i.putExtra(IngredientsActivity.CATEGORY, category);
                i.putExtra(IngredientsActivity.PRODUCT, products.get(position).getName());
                i.putExtra(IngredientsActivity.BRAND, products.get(position).getBrand());
                System.out.println("IngredientsList size =" + ingredientsList.size());
                startActivityForResult(i, 0);
            }
        });

    }

    //Async task for pulling from Bing Image Search API
    public class getImagesTask extends AsyncTask<Void, Void, Void>
    {
        JSONObject json;
        private String mSearchStr;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            URL url = null;

            try {
                for (int i = 0; i < products.size(); i++) {
                    p = products.get(i).getName();

                    System.out.println("name => " + p);
                    //Save the brand and name of product to search
                    search = brand + " " + p;
                    search = Uri.encode(search);
                    System.out.println("Search string => " + search);
                    //Set search URL
                    String bingUrl = "https://api.datamarket.azure.com/Bing/Search/v1/"
                            + "Image?Query=%27" + search + "%27&$format=JSON&$top=1";
                    String accountKey = "o1CY7g0ttGUZMiWc05ObeqzKnkvtIx30VG98oGHiPpg";
                    //Encode Bing authentication key
                    String encodedAuth = Base64.encodeToString((accountKey + ":" + accountKey).getBytes(), Base64.NO_WRAP);
                    System.out.println("Account Key " + encodedAuth);

                    //Create URL and open connection
                    url = new URL(bingUrl);
                    final URLConnection connection = url.openConnection();
                    connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

                    //Use buffered reader to get reference to JSON results
                    String temp;
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    while ((temp = reader.readLine()) != null) {
                        builder.append(temp);
                    }

                    System.out.println("Builder string = " + builder.toString());
                    json = new JSONObject(builder.toString());
                    //Grab results and place into JSON Array
                    JSONObject responseObject = json.getJSONObject("d");
                    JSONArray responseArray = responseObject.getJSONArray("results");

                    listImages = getImageList(responseArray, i);
                    System.out.println(products.get(i).getUrl());
                }
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
            SetListViewAdapter();
        }

    }


    public ArrayList<Object> getImageList(JSONArray resultArray, int index)
    {
        ArrayList<Object> listImages = new ArrayList<Object>();

        try
        {
            for(int i=0; i<resultArray.length(); i++)
            {
                JSONObject obj;
                obj = resultArray.getJSONObject(i);

                //Get specific URL to image
                String url = obj.getString("MediaUrl");
                System.out.println("Thumb URL => " + url);
                products.get(index).setUrl(url);

            }
            return listImages;
        }
        catch (JSONException e)
        {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void SetListViewAdapter()
    {
        adapter = new ProductAdapter(this, products);
        productListView.setAdapter(adapter);
    }

    public void doFirebase() {
        //Get each child from specif brand in Firebase and store in product array
        final Firebase ref = new Firebase(getResources().getString(R.string.Firebase_url));
        ref.child(category2).child(brand).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Product product = new Product();
                product.setName(dataSnapshot.getKey());
                product.setBrand(brand);
                products.add(product);
                Log.d("TAG", product.getName());
                System.out.println("SIZE " + products.size());

                //Tell Async task to start and call API for specific product
                new getImagesTask().execute();
                    System.out.println("done!");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("TAG", "HERE");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("TAG", firebaseError.getMessage());
            }
        });


    }
}
