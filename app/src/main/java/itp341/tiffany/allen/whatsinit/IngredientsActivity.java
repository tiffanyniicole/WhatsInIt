package itp341.tiffany.allen.whatsinit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import itp341.tiffany.allen.whatsinit.model.IngredientsSingleton;

/**
 * Created by tiffanyniicole on 4/30/16.
 */
public class IngredientsActivity extends AppCompatActivity{

    public static final String EXTRA_POSITION = "extra_position";
    public static final String CATEGORY = "itp341.tiffany.allen.whatsinit.category";
    public static final String BRAND = "itp341.tiffany.allen.whatsinit.brand";
    public static final String PRODUCT = "itp341.tiffany.allen.whatsinit.product";

    LinearLayout ingredientsLayout;
    TextView ingredientsTextView;
    ListView ingredientsListView;
    ArrayAdapter<String> mAdapter;
    String category;
    String category2;
    String brand;
    String product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_layout);

        ingredientsLayout = (LinearLayout) findViewById(R.id.ingredientsLayout);
        ingredientsTextView = (TextView) findViewById(R.id.ingredientsTextView);
        ingredientsListView = (ListView) findViewById(R.id.ingredientsListView);

        category = getIntent().getStringExtra(CATEGORY);
        brand = getIntent().getStringExtra(BRAND);
        product = getIntent().getStringExtra(PRODUCT);

        ingredientsTextView.setText(brand + " " + product + " Ingredients");

        switch (category) {
            case "hair":
                ingredientsLayout.setBackgroundResource(R.drawable.hair_background);
                category2 = "Hair";
                break;

            case "cosmetic":
                ingredientsLayout.setBackgroundResource(R.drawable.cosmetic_background);
                category2 = "Cosmetics";
                break;

            case  "food":
                ingredientsLayout.setBackgroundResource(R.drawable.food_background);
                category2 = "Food";
                break;

            case "cleaning":
                ingredientsLayout.setBackgroundResource(R.drawable.cleaning_background);
                category2 = "Cleaning";
                break;
        }

        System.out.println(category2);
        System.out.println(brand);
        System.out.println(product);
        mAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_multiple_choice,
                IngredientsSingleton.get(getApplicationContext()).getIngredient());
        ingredientsListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        ingredientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),
                        IndividualIngredientActivity.class);
                i.putExtra(IndividualIngredientActivity.EXTRA_POSITION, position);
                i.putExtra(IndividualIngredientActivity.CATEGORY, category);
                i.putExtra(IndividualIngredientActivity.PRODUCT, product);
                i.putExtra(IndividualIngredientActivity.BRAND, brand);
                startActivityForResult(i, 2);
            }
        });
    }
}
