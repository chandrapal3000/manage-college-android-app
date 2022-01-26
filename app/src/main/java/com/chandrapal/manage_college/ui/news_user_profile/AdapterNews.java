package com.chandrapal.manage_college.ui.news_user_profile;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolderNews> {
    List<Model> dataHolder;

    public AdapterNews(List<Model> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderNews onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_news, parent, false);
        return new ViewHolderNews(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderNews holder, int position) {
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
        Picasso.get().load(dataHolder.get(position).getNotesUserProfileImage()).into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class ViewHolderNews extends RecyclerView.ViewHolder{
        TextView uploadedTime, username, title, branch, relatedDocument;
        ShapeableImageView profileImage;

        public ViewHolderNews(@NonNull @NotNull View itemView) {
            super(itemView);
            uploadedTime= itemView.findViewById(R.id.uploadedTime_single_row_news);
            username = itemView.findViewById(R.id.username_single_row_news);
            title = itemView.findViewById(R.id.title_single_row_news);
            branch = itemView.findViewById(R.id.branch_single_row_news);
            relatedDocument = itemView.findViewById(R.id.relatedDocument_single_row_news);
            profileImage = itemView.findViewById(R.id.profileImage_single_row_news);
        }
    }
}
