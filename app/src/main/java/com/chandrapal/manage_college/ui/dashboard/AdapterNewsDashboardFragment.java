package com.chandrapal.manage_college.ui.dashboard;

import android.content.Intent;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chandrapal.manage_college.MainActivity;
import com.chandrapal.manage_college.Model;
import com.chandrapal.manage_college.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;

public class AdapterNewsDashboardFragment extends RecyclerView.Adapter<AdapterNewsDashboardFragment.ViewHolderNewsDashboardFragment> {
    List<Model> dataHolder;
    onClickInterfaceForNews onClickInterfaceForNews;

    public AdapterNewsDashboardFragment(List<Model> dataHolder, onClickInterfaceForNews onClickInterfaceForNews ) {
        this.dataHolder = dataHolder;
        this.onClickInterfaceForNews = onClickInterfaceForNews;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderNewsDashboardFragment onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_news_fragment_dashboard, parent, false);
        return new AdapterNewsDashboardFragment.ViewHolderNewsDashboardFragment(view, onClickInterfaceForNews);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterNewsDashboardFragment.ViewHolderNewsDashboardFragment holder, int position) {

            holder.news.setText(dataHolder.get(position).getNewsTitle());

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class ViewHolderNewsDashboardFragment extends RecyclerView.ViewHolder{
        TextView news;
        onClickInterfaceForNews onClickInterfaceForNewsViewHolder;

        public ViewHolderNewsDashboardFragment(@NonNull @NotNull View itemView, onClickInterfaceForNews onClickInterfaceForNews) {
            super(itemView);

            news = itemView.findViewById(R.id.textview_news_single_row_news_fragment_dashboard);

            onClickInterfaceForNewsViewHolder = onClickInterfaceForNews;
            itemView.setOnClickListener(v->onClickInterfaceForNewsViewHolder.onClickMethodForNews(getAbsoluteAdapterPosition()));
        }
    }

    public interface onClickInterfaceForNews{
        void onClickMethodForNews(int position);
    }

}

