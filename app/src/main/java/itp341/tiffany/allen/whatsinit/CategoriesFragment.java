package itp341.tiffany.allen.whatsinit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by tiffanyniicole on 4/30/16.
 */
public class CategoriesFragment extends Fragment implements View.OnClickListener{
    public static final String CATEGORY = "itp341.tiffany.allen.whatsinit.category";

    Button hairButton;
    Button cosmeticsButton;
    Button foodButton;
    Button cleaningButton;

    public CategoriesFragment() {
    }

    public static CategoriesFragment newInstance() {
        Bundle args = new Bundle();

        CategoriesFragment f = new CategoriesFragment();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categories_fragment, container, false);

        hairButton = (Button) v.findViewById(R.id.hairButton);
        cosmeticsButton = (Button) v.findViewById(R.id.cosmeticsButton);
        foodButton = (Button) v.findViewById(R.id.foodButton);
        cleaningButton = (Button) v.findViewById(R.id.cleaningButton);

        hairButton.setOnClickListener(this);
        cosmeticsButton.setOnClickListener(this);
        foodButton.setOnClickListener(this);
        cleaningButton.setOnClickListener(this);


        return  v;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.hairButton:
               i = new Intent(getActivity(),
                        SearchActivity.class);
                i.putExtra(CATEGORY, "hair");
                startActivityForResult(i, 0);
                break;

            case R.id.cosmeticsButton:
                i = new Intent(getActivity(),
                        SearchActivity.class);
                i.putExtra(CATEGORY, "cosmetic");
                startActivityForResult(i, 0);
                break;

            case R.id.foodButton:
                i = new Intent(getActivity(),
                        SearchActivity.class);
                i.putExtra(CATEGORY, "food");
                startActivityForResult(i, 0);
                break;

            case R.id.cleaningButton:
                i = new Intent(getActivity(),
                        SearchActivity.class);
                i.putExtra(CATEGORY, "cleaning");
                startActivityForResult(i, 0);
                break;
        }

    }
}
