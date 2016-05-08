package itp341.tiffany.allen.whatsinit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by tiffanyniicole on 4/30/16.
 */
public class SearchActivity extends AppCompatActivity {
    public static final String CATEGORY = "itp341.tiffany.allen.whatsinit.category";
    public static final String BRAND = "itp341.tiffany.allen.whatsinit.brand";
    EditText searchEditText;
    TextView searchTextView;
    Button searchButton;
    LinearLayout searchLayout;
    String category;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        searchEditText = (EditText) findViewById(R.id.searchEditText);
        searchTextView = (TextView) findViewById(R.id.searchTextView);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);

        category = getIntent().getStringExtra(CATEGORY);
        searchTextView.setText("For " + category + " products");

        //Setting correct background for layout
        switch (category) {
            case "hair":
                searchLayout.setBackgroundResource(R.drawable.hair_background);
                break;

            case "cosmetic":
                searchLayout.setBackgroundResource(R.drawable.cosmetic_background);
                break;

            case  "food":
                searchLayout.setBackgroundResource(R.drawable.food_background);
                break;

            case "cleaning":
                searchLayout.setBackgroundResource(R.drawable.cleaning_background);
                break;
        }

        //Listener for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent to go to product activity
                Intent i = new Intent(
                        getApplicationContext(),
                        ProductsActivity.class
                );

                //Sending category and brand name to product activity
                i.putExtra(CATEGORY, category);
                i.putExtra(BRAND, searchEditText.getText().toString());

                startActivityForResult(i, 1);

            }
        });
    }
}
