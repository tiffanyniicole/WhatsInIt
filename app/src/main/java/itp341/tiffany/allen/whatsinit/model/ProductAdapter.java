package itp341.tiffany.allen.whatsinit.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import itp341.tiffany.allen.whatsinit.R;

/**
 * Created by tiffanyniicole on 5/6/16.
 */
public class ProductAdapter extends ArrayAdapter<Product> {
    Context c;

    public ProductAdapter(Context context, ArrayList<Product> product) {
        super(context, 0, product);
       c = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.productTextView);
        ImageView imageImageView = (ImageView) convertView.findViewById(R.id.productsImageView);

        titleTextView.setText(product.getBrand() + " " + product.getName());

        Glide.with(c)
                .load(product.getUrl())
                .placeholder(R.drawable.icon)
                .into(imageImageView);



        return convertView;
    }
}
