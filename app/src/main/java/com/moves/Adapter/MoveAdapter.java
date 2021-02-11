package com.moves.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moves.Activity.DetailActivity;
import com.moves.Model.Move;
import com.moves.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w500";
    private Context mContext;
    private ArrayList<Move> list;
    private int a;

    public MoveAdapter(ArrayList<Move> list, Context mContext, int a) {
        this.list = list;
        this.a = a;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == VIEW_TYPE_NORMAL) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_move_design, parent, false);
            return new ProductViewHolder(v);
        } else if (viewType == VIEW_TYPE_LOADING) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new ProgressHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            final Move lists = list.get(position);
            productViewHolder.bind(lists);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("videoId", lists.getId());
                    intent.putExtra("a", a);
                    intent.putExtra("name", lists.getTitle() + "");
                    intent.putExtra("img", lists.getPosterPath());
                    intent.putExtra("date", lists.getReleaseDate());
                    intent.putExtra("langueg", lists.getOriginalLanguage());
                    intent.putExtra("overview", lists.getOverview());
                    intent.putExtra("OriginalTitle", lists.getOriginalTitle());
                    intent.putExtra("ratingBar", lists.getVoteAverage());
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == list.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void addItems(List<Move> postItems) {
        list.addAll(postItems);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    Move getItem(int position) {
        return list.get(position);
    }

    public void addLoading() {
        isLoaderVisible = true;
        list.add(new Move());
        notifyItemInserted(list.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = list.size() - 1;
        Move item = getItem(position);
        if (item != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private RatingBar ratingBar;
        private ImageView imageView;
        private TextView title, language;

        private ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.move_img);
            title = itemView.findViewById(R.id.move_tv_title);
            language = itemView.findViewById(R.id.move_tv_language);
            ratingBar = itemView.findViewById(R.id.moves_ratingBar);
        }

        @SuppressLint("SetTextI18n")
        private void bind(Move lists) {
            title.setText(lists.getTitle());
            language.setText(lists.getOriginalLanguage());
            ratingBar.setRating((float) lists.getVoteAverage());
            Picasso.get().load(BASE_URL_IMG + lists.getPosterPath()).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }
    }

    public class ProgressHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        ProgressHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}