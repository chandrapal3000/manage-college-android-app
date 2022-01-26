package com.chandrapal.manage_college.ui.notes_user_profile;

import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chandrapal.manage_college.Model;
import com.chandrapal.manage_college.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class AdapterNotesUserProfile extends RecyclerView.Adapter<AdapterNotesUserProfile.ViewHolderHome> implements Filterable {
    private List<Model> dataHolder;
    private List<Model> backupList ;
    private OnShowMoreListener onShowMoreListener;
    private OnProfileClickListener onProfileClickListener;
    private View viewSingleRowHome;
    int linearChildCount = 1;
    boolean topBar = true;
    boolean secondBar = true;
    boolean showMore = true;

    public AdapterNotesUserProfile(List<Model> dataHolder, OnShowMoreListener onShowMoreListener, OnProfileClickListener onProfileClickListener) {
        this.dataHolder = dataHolder;
        this.backupList = new ArrayList<>(dataHolder);
        this.onShowMoreListener = onShowMoreListener;
        this.onProfileClickListener = onProfileClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderHome onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_home, parent,false);
        viewSingleRowHome = view;
        return new ViewHolderHome(view, onShowMoreListener, onProfileClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderHome holder, int position) {

        if(!topBar){
            holder.profileImage.setVisibility(View.GONE);
            holder.username.setVisibility(View.GONE);
            holder.uploadedTime.setVisibility(View.GONE);
        }
        else {
            holder.profileImage.setVisibility(View.VISIBLE);
            holder.username.setVisibility(View.VISIBLE);
            holder.uploadedTime.setVisibility(View.VISIBLE);
        }

        if(!secondBar){
            holder.branch.setVisibility(View.GONE);
            holder.subjectAndChapter.setVisibility(View.GONE);
        } else {
            holder.branch.setVisibility(View.VISIBLE);
            holder.subjectAndChapter.setVisibility(View.VISIBLE);
        }

        if(!showMore){
            holder.showMore.setVisibility(View.GONE);
        } else {
            holder.showMore.setVisibility(View.VISIBLE);
        }

        holder.username.setText(dataHolder.get(position).getNotesUploadedBy());
        holder.uploadedTime.setText(dataHolder.get(position).getNotesUploadedTime());
        String branchString = "For "+dataHolder.get(position).getNotesBranch();
        holder.branch.setText(branchString);
        holder.title.setText(dataHolder.get(position).getNotesTitle());
        String subjectAndChapterString = "Subject "+dataHolder.get(position).getNotesSubject()+" Chapter "+dataHolder.get(position).getNotesChapter();
        holder.subjectAndChapter.setText(subjectAndChapterString);
//        holder.news.setText(Html.fromHtml(dataHolder.get(position).getNewsNews()));
        Picasso.get().load(dataHolder.get(position).getNotesUserProfileImage()).into(holder.profileImage);


        ArrayList<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(dataHolder.get(position).getNotesNotesText());
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            lines.add(line);
        }
        scanner.close();
        int textViewCount=1 , imageViewCount=1;
        if(holder.notesLinearLayout.getChildCount()>0){
            holder.notesLinearLayout.removeAllViews();
        }
        for(int j = 0; j <linearChildCount; j++){
            Document jsoupDoc = Jsoup.parse(lines.get(j));
            Elements imgs = jsoupDoc.select("img");
//            Element specialDiv = jsoupDoc.select("div").first();
            if (imgs.size() == 0 ) {
                TextView textView = new TextView(holder.itemView.getContext());
                String textViewString = Html.fromHtml(lines.get(j)).toString();
                try {
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
//                        textView.setBackgroundColor(0xFF018786);
                    textView.setText(textViewString);
                    textView.setTextSize(20);
                    textView.setLetterSpacing((float) 0.053);
                    textView.setTextColor(Color.parseColor("#000000"));
                    holder.notesLinearLayout.addView(textView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textViewCount = textViewCount + 1;
            } else{
                for (Element element : imgs) {
                    Element img = element.select("img").first();
                    String image = img.absUrl("src");
                    if (img.absUrl("src").equals("")) {
                        image = "https://manage-college.000webhostapp.com/upload_image/images/image_not_found.png";
//                        image = "";
                    }
                    ImageView imageView = new ImageView(holder.itemView.getContext());
                    imageView.setPadding(0, 10, 0, 10);
                    imageView.setAdjustViewBounds(true);
                    try {
                        Picasso.get().load(image).into(imageView);
                        holder.notesLinearLayout.addView(imageView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imageViewCount = imageViewCount + 1;
                }

            }

        }

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    @Override
    public Filter getFilter() {
        return backupListFilter;
    }

    private Filter backupListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0 ){
                filteredList.addAll(backupList);
            } else{
                String filterPattern = new Model().cleanString01(constraint.toString());
                for (Model modelItem : backupList){

                    String newNotesTitleString = modelItem.cleanString01(modelItem.getNotesTitle());
                    String newNotesBranchString = modelItem.cleanString01(modelItem.getNotesBranch());
                    String newNotesUploadedByString = modelItem.cleanString01(modelItem.getNotesUploadedBy());
                    String newNotesUploadedTimeString = modelItem.cleanString01(modelItem.getNotesUploadedTime());

                    if (newNotesTitleString.contains(filterPattern) || newNotesBranchString.contains(filterPattern) || newNotesUploadedByString.contains(filterPattern) || newNotesUploadedTimeString.contains(filterPattern) ){
                        filteredList.add(modelItem);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataHolder.clear();
            dataHolder.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolderHome extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView username, uploadedTime, title, branch, subjectAndChapter, showMore;
        ShapeableImageView profileImage;
        LinearLayout notesLinearLayout;
        ConstraintLayout constraintLayout1, constraintLayout2;
        OnShowMoreListener onShowMoreListener;
        OnProfileClickListener onProfileClickListener;
        public ViewHolderHome(@NonNull @NotNull View itemView, OnShowMoreListener onShowMoreListener, OnProfileClickListener onProfileClickListener) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            uploadedTime = itemView.findViewById(R.id.uploadedTime);
            branch = itemView.findViewById(R.id.branch);
            title = itemView.findViewById(R.id.title);
            notesLinearLayout = itemView.findViewById(R.id.notesLinearLayout);
            profileImage = itemView.findViewById(R.id.profileImage);
            subjectAndChapter = itemView.findViewById(R.id.subjectAndChapter);
            showMore = itemView.findViewById(R.id.showMore);
            this.onShowMoreListener = onShowMoreListener;
            this.onProfileClickListener = onProfileClickListener;

            showMore.setOnClickListener(this);
            title.setOnClickListener(this);
            notesLinearLayout.setOnClickListener(this);

            profileImage.setOnClickListener(v->{
                onProfileClickListener.onProfileClick(getBindingAdapterPosition());
            });
            username.setOnClickListener(v->{
                onProfileClickListener.onProfileClick(getBindingAdapterPosition());
            });
        }

        @Override
        public void onClick(View v) {
            onShowMoreListener.onShowMoreClick(getBindingAdapterPosition());
        }
    }
    public interface OnShowMoreListener{
        void onShowMoreClick(int position);
    }

    public interface OnProfileClickListener{
        void onProfileClick(int position);
    }

    public View getItemView(){
        return viewSingleRowHome;
    }

    public void notifyRecyclerview(int linearChildCount, boolean topBar, boolean secondBar, boolean showMore){
        this.linearChildCount = linearChildCount;
        this.topBar = topBar;
        this.secondBar = secondBar;
        this.showMore = showMore;

        notifyDataSetChanged();
    }

}
