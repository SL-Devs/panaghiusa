package com.sldevs.panaghiusa;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Cart_Item_List_Adaptor extends BaseAdapter {

    Context context;
    ArrayList<String> plasticImage = new ArrayList<String>();
    String[] plasticType;
    LayoutInflater inflater;
    public Cart_Item_List_Adaptor(Context ctx, String[] typePlastic, ArrayList<String> imagePlastic){
        this.context = ctx;
        this.plasticType = typePlastic;
        this.plasticImage = imagePlastic;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return plasticType.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.cart_item_layout, null);
        TextView plasticTypee = (TextView) view.findViewById(R.id.tvPlasticType);
        ImageView plasticImagee = (ImageView) view.findViewById(R.id.ivCartItems);

        plasticTypee.setText(plasticType[i]);
//        plasticImagee.setImageBitmap(plasticImage.get(i));
        return null;
    }
}
