package com.example.projetmobile.activity.PlanDeFormation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetmobile.R;
import com.example.projetmobile.model.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_product, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.textViewName.setText(product.getSemestre());
        holder.textViewBrand.setText("Code: " +product.getCode());
        holder.textViewDesc.setText( "Intitulé: "+product.getIntitule());
        holder.textViewQty.setText("Volume :" + product.getVolume()+"Heures " );
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewBrand, textViewDesc, textViewPrice, textViewQty;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);
            textViewDesc = itemView.findViewById(R.id.textview_desc);
            textViewQty = itemView.findViewById(R.id.textview_quantity);



        }


    }
}
