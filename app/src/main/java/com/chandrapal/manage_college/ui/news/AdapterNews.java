package com.chandrapal.manage_college.ui.news;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chandrapal.manage_college.Model;
import com.chandrapal.manage_college.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<com.chandrapal.manage_college.ui.news.AdapterNews.ViewHolderNews> {
    List<Model> dataHolder;
    OnProfileClickInterface onProfileClickInterface;

    public AdapterNews(List<Model> dataHolder, OnProfileClickInterface onProfileClickInterface) {
        this.onProfileClickInterface = onProfileClickInterface;
        this.dataHolder = dataHolder;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderNews onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_news, parent, false);
        return new ViewHolderNews(view, onProfileClickInterface);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull com.chandrapal.manage_college.ui.news.AdapterNews.ViewHolderNews holder, int position) {
        holder.uploadedTime.setText(dataHolder.get(position).getNewsUploadedTime());
        String usernameString = dataHolder.get(position).getNewsUploadedBy();
        holder.username.setText(usernameString);
        holder.title.setText(dataHolder.get(position).getNewsTitle());
        holder.branch.setText(dataHolder.get(position).getNewsBranch());

        String relatedDocumentString = dataHolder.get(position).getNewsRelatedDocument();
        if(relatedDocumentString.equals("")){
            relatedDocumentString = "No related document";
        }

        holder.relatedDocument.setText(relatedDocumentString);
        Picasso.get().load("https://manage-college.000webhostapp.com/upload_image/images/1406992643.jpg").into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class ViewHolderNews extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView uploadedTime, username, title, branch, relatedDocument;
        ShapeableImageView profileImage;
        OnProfileClickInterface onProfileClickInterface;

        public ViewHolderNews(@NonNull @NotNull View itemView, OnProfileClickInterface onProfileClickInterface) {
            super(itemView);
            uploadedTime= itemView.findViewById(R.id.uploadedTime_single_row_news);
            username = itemView.findViewById(R.id.username_single_row_news);
            title = itemView.findViewById(R.id.title_single_row_news);
            branch = itemView.findViewById(R.id.branch_single_row_news);
            relatedDocument = itemView.findViewById(R.id.relatedDocument_single_row_news);
            profileImage = itemView.findViewById(R.id.profileImage_single_row_news);

            this.onProfileClickInterface = onProfileClickInterface;

            profileImage.setOnClickListener(this);
            username.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onProfileClickInterface.onProfileClickMethod(getAbsoluteAdapterPosition());
        }
    }
    public interface OnProfileClickInterface{
        void onProfileClickMethod(int position);
    }
}
