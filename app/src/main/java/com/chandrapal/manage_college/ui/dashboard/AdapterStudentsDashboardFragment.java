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

public class AdapterStudentsDashboardFragment extends RecyclerView.Adapter<AdapterStudentsDashboardFragment.ViewHolderStudentsDashboardFragment> {
    List<Model> dataHolder;
    onImageClickInterfaceForStudents onImageClickInterface;

    public AdapterStudentsDashboardFragment(List<Model> dataHolder, onImageClickInterfaceForStudents onImageClickInterface ) {
        this.dataHolder = dataHolder;
        this.onImageClickInterface = onImageClickInterface;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolderStudentsDashboardFragment onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_users_fragment_dashboard, parent, false);
        return new AdapterStudentsDashboardFragment.ViewHolderStudentsDashboardFragment(view, onImageClickInterface);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterStudentsDashboardFragment.ViewHolderStudentsDashboardFragment holder, int position) {
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
        Picasso.get().load(dataHolder.get(position).getUsersProfileImage()).into(holder.profile_image);
        holder.fullname.setText(dataHolder.get(position).getUsersFullname());
//        Picasso.get().load(dataHolder.get(position).getPhotosImage()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class ViewHolderStudentsDashboardFragment extends RecyclerView.ViewHolder{
        TextView uploadedTime, username, caption, password, fullname;
        ShapeableImageView profile_image;
        ShapeableImageView profileImage;
        onImageClickInterfaceForStudents onImageClickInterfaceViewHolder;

        public ViewHolderStudentsDashboardFragment(@NonNull @NotNull View itemView, onImageClickInterfaceForStudents onImageClickInterface) {
            super(itemView);
//            uploadedTime= itemView.findViewById(R.id.uploadedTime);
//            username = itemView.findViewById(R.id.username);
//            caption = itemView.findViewById(R.id.caption);
            profile_image = itemView.findViewById(R.id.image_single_row_users_fragment_dashboard);
            fullname = itemView.findViewById(R.id.fullname_single_row_users_fragment_dashboard);
//            profileImage = itemView.findViewById(R.id.profileImage);
            onImageClickInterfaceViewHolder = onImageClickInterface;

            profile_image.setOnClickListener(v->onImageClickInterfaceViewHolder.onImageClickMethodForStudents(getAbsoluteAdapterPosition()));
            fullname.setOnClickListener(v->onImageClickInterfaceViewHolder.onImageClickMethodForStudents(getAbsoluteAdapterPosition()));

        }
    }

    public interface onImageClickInterfaceForStudents {
        void onImageClickMethodForStudents(int position);
    }

}
