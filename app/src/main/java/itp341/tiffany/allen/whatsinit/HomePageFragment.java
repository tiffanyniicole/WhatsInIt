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
public class HomePageFragment extends Fragment {

    Button searchButton;

    public HomePageFragment() {
    }

    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();

        HomePageFragment f = new HomePageFragment();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_screen_fragment, container, false);

        searchButton = (Button) v.findViewById(R.id.beginButton);

        //Listener for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoriesActivity.class);
                startActivity(i);
            }
        });

        return  v;
    }
}
