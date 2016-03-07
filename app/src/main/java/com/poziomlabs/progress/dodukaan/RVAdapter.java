package com.poziomlabs.progress.dodukaan;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import android.support.v7.widget.RecyclerView;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.StoreViewHolder> {


    public static class StoreViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView storeName;
        TextView storeCategory;
        ImageView storePhoto;
        ImageView storeDelivery;

        StoreViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            storeName = (TextView)itemView.findViewById(R.id.store_name);
            storeCategory = (TextView)itemView.findViewById(R.id.store_category);
            storePhoto = (ImageView)itemView.findViewById(R.id.store_photo);
            storeDelivery = (ImageView)itemView.findViewById(R.id.delivery);





        }
    }

    List<Store> stores;
    Context context;


    RVAdapter(List<Store> stores,Context context){
        this.stores = stores;
        this.context = context;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        StoreViewHolder svh = new StoreViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(StoreViewHolder storeViewHolder, final int i) {
        storeViewHolder.storeName.setText(stores.get(i).name);
        storeViewHolder.storeCategory.setText(stores.get(i).category);
        storeViewHolder.storePhoto.setImageResource(stores.get(i).photoId);
        storeViewHolder.storeDelivery.setImageResource(R.drawable.truck);


        storeViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            //    Intent opnactvty = new Intent("com.example.progress.json_reader.READERACTIVITY");
                Intent opnactvty = new Intent(context, MainActivity.class);
                opnactvty.putExtra("StoreID",String.valueOf(stores.get(i).storeID));
                opnactvty.putExtra("StoreCategory",stores.get(i).category);
                opnactvty.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              //  Log.d("Intent", opnactvty.getAction());
                context.startActivity(opnactvty);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stores.size();
    }
}