package com.example.knowledge_enhancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class VocabMenuAdapter extends PagerAdapter {
    private List<VocabMenuItem> items;
    private LayoutInflater layoutInflater;
    private Context context;

    public VocabMenuAdapter(List<VocabMenuItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.vocab_menu_item, container, false);

        ImageView itemImage = (ImageView) view.findViewById(R.id.vocab_menu_item_image);
        TextView itemText = (TextView) view.findViewById(R.id.vocab_menu_item_text);

        itemImage.setImageResource(items.get(position).getItemImage());
        itemText.setText(items.get(position).getItemTitle());

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
