package com.chandrapal.manage_college.ui.photos;

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

public class AdapterPhotos extends RecyclerView.Adapter<AdapterPhotos.ViewHolderPhotos> {
    List<Model> dataHolder;
    onProfileClickInterface onProfileClickInterface;

    public AdapterPhotos(List<Model> dataHolder, onProfileClickInterface onProfileClickInterface) {
        this.onProfileClickInterface = onProfileClickInterface;
        this.dataHolder = dataHolder;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderPhotos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_photos, parent, false);
        return new ViewHolderPhotos(view, onProfileClickInterface);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterPhotos.ViewHolderPhotos holder, int position) {
        holder.uploadedTime.setText(dataHolder.get(position).getPhotosUploadedTime());
        String usernameString = dataHolder.get(position).getPhotosUploadedBy();

        holder.username.setText(usernameString);

        SpannableStringBuilder captionString = new SpannableStringBuilder();
        int start = captionString.length();
        captionString.append(usernameString);
        captionString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, captionString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        captionString.append(" ").append(dataHolder.get(position).getPhotosCaption().replace("-", " "));

        holder.caption.setText(captionString);
        Picasso.get().load(dataHolder.get(position).getPhotosImage()).into(holder.image);
        Picasso.get().load(dataHolder.get(position).getPhotosUserProfileImage()).into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class ViewHolderPhotos extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView uploadedTime, username, caption, password;
        ImageView image;
        ShapeableImageView profileImage;
        onProfileClickInterface onProfileClickInterface;

        public ViewHolderPhotos(@NonNull @NotNull View itemView, onProfileClickInterface onProfileClickInterface) {
            super(itemView);
            uploadedTime= itemView.findViewById(R.id.uploadedTime);
            username = itemView.findViewById(R.id.username);
            caption = itemView.findViewById(R.id.caption);
            image = itemView.findViewById(R.id.image);
            profileImage = itemView.findViewById(R.id.profileImage);

            this.onProfileClickInterface = onProfileClickInterface;

            username.setOnClickListener(this);
            profileImage.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onProfileClickInterface.onProfileClickMethod(getAbsoluteAdapterPosition());
        }
    }

    public interface onProfileClickInterface{
        void onProfileClickMethod(int position);
    }

}
