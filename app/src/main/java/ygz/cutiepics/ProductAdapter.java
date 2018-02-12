package ygz.cutiepics;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Raina on 2018-02-01.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{
    private static final String TAG = ProductAdapter.class.getSimpleName();
    private Context context;
    private List<ProductObject> productList;

    public ProductAdapter(Context context, List<ProductObject> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emoji_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductObject productObject = productList.get(position);
        int imageRes = getResourceId(context, productObject.getName(), "drawable", context.getPackageName());
        holder.emoji.setImageResource(imageRes);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static int getResourceId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }
}
