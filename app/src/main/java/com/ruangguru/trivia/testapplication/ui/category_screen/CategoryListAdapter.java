package com.ruangguru.trivia.testapplication.ui.category_screen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruangguru.trivia.testapplication.R;
import com.ruangguru.trivia.testapplication.data.repository.CategoryItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lukas Dylan Adisurya on 11/24/2017.
 * If you had any question about this project, you can contact me via E-mail lukas.dylan.adisurya@gmail.com
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private final List<CategoryItem> categoryItemList;
    private final CategoryInterface.Adapter listener;

    CategoryListAdapter(List<CategoryItem> categoryItemList, CategoryInterface.Adapter listener){
        this.categoryItemList = categoryItemList;
        this.listener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryItem categoryItem = categoryItemList.get(position);
        holder.bind(categoryItem);
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.backgroundCategoryIV)
        ImageView backgroundCategoryIV;
        @BindView(R.id.iconCategoryIV)
        ImageView iconCategoryIV;
        @BindView(R.id.categoryNameTV)
        TextView categoryNameTV;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(CategoryItem categoryItem){
            backgroundCategoryIV.setImageResource(categoryItem.getBackgroundImage());
            iconCategoryIV.setImageResource(categoryItem.getCategoryIcon());
            categoryNameTV.setText(categoryItem.getCategoryName());
        }

        @OnClick(R.id.mainCard)
        void selectCategory(){
            listener.onCategorySelect(getAdapterPosition());
        }
    }
}
