package com.chandrapal.manage_college.ui.dashboard;

import android.content.Intent;
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

import com.chandrapal.manage_college.MainActivity;
import com.chandrapal.manage_college.Model;
import com.chandrapal.manage_college.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;

public class AdapterPhotosDashboardFragment extends RecyclerView.Adapter<AdapterPhotosDashboardFragment.ViewHolderPhotosDashboardFragment> {
    List<Model> dataHolder;
    onImageClickInterfaceForPhotos onImageClickInterfaceForPhotos;

    public AdapterPhotosDashboardFragment(List<Model> dataHolder, onImageClickInterfaceForPhotos onImageClickInterfaceForPhotos ) {
        this.dataHolder = dataHolder;
        this.onImageClickInterfaceForPhotos = onImageClickInterfaceForPhotos;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderPhotosDashboardFragment onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_photos_fragment_dashboard, parent, false);
        return new AdapterPhotosDashboardFragment.ViewHolderPhotosDashboardFragment(view, onImageClickInterfaceForPhotos);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterPhotosDashboardFragment.ViewHolderPhotosDashboardFragment holder, int position) {
//        holder.uploadedTime.setText(dataHolder.get(position).getPhotosUploadedTime());
//        String usernameString = dataHolder.get(position).getPhotosUploadedBy();
//
//        holder.username.setText(usernameString);
//
//        SpannableStringBuilder captionString = new SpannableStringBuilder();
//        int start = captionString.length();
//        captionString.append(usernameString);
//        captionString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, captionString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        captionString.append(" ").append(dataHolder.get(position).getPhotosCaption().replace("-", " "));

//        holder.caption.setText(captionString);
        Picasso.get().load(dataHolder.get(position).getPhotosImage()).into(holder.image);
//        Picasso.get().load(dataHolder.get(position).getPhotosImage()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class ViewHolderPhotosDashboardFragment extends RecyclerView.ViewHolder{
        TextView uploadedTime, username, caption, password;
        ImageView image;
        ShapeableImageView profileImage;
        onImageClickInterfaceForPhotos onImageClickInterfaceForPhotosViewHolder;

        public ViewHolderPhotosDashboardFragment(@NonNull @NotNull View itemView, onImageClickInterfaceForPhotos onImageClickInterfaceForPhotos) {
            super(itemView);
//            uploadedTime= itemView.findViewById(R.id.uploadedTime);
//            username = itemView.findViewById(R.id.username);
//            caption = itemView.findViewById(R.id.caption);
            image = itemView.findViewById(R.id.image_single_row_photos_fragment_dashboard);
//            profileImage = itemView.findViewById(R.id.profileImage);
            onImageClickInterfaceForPhotosViewHolder = onImageClickInterfaceForPhotos;

            image.setOnClickListener(v->onImageClickInterfaceForPhotosViewHolder.onImageClickMethodForPhotos(getAbsoluteAdapterPosition()));
        }
    }

    public interface onImageClickInterfaceForPhotos{
        void onImageClickMethodForPhotos(int position);
    }

}
