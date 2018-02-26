package com.example.popovov.newyorktimes.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popovov.newyorktimes.R;
import com.example.popovov.newyorktimes.model.MediaMetadatum;
import com.example.popovov.newyorktimes.model.Medium;
import com.example.popovov.newyorktimes.model.Result;
import com.example.popovov.newyorktimes.utils.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Result> results;
    private Context context;
    private Result res;
    private Medium med;
    private MediaMetadatum media;
    public MyAdapter(List<Result> results,Context context){
        this.results=results;
        this.context=context;
    }
    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        res=results.get(position);
        med=res.getMedia().get(0);
        media=med.getMediaMetadata().get(0);
        holder.tittle.setText(res.getTitle());
        Picasso.with(holder.image.getContext())
                .load(media.getUrl())
                .placeholder(R.drawable.ic_rotate_right_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(holder.image);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick){
                    Toast.makeText(context,results.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }else{

                   Toast.makeText(context,results.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private ItemClickListener itemClickListener;
        private CardView card;
        private TextView tittle;
        private ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            card=(CardView)itemView.findViewById(R.id.card);
            tittle=(TextView) itemView.findViewById(R.id.tittle);
            image=(ImageView)itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),true);
            return true;
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }
    }

}
