package com.example.knowledge_enhancer;

import java.util.ArrayList;
import java.util.List;

public class VocabMenuItem {
    private int itemImage;
    private String itemTitle;
    private List<VocabMenuItem> items;

    public VocabMenuItem(int itemImage, String itemTitle) {
        this.itemImage = itemImage;
        this.itemTitle = itemTitle;
        items = new ArrayList<>();
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
}
