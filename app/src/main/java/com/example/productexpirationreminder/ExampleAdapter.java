package com.example.productexpirationreminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {
    private ArrayList<AddItem> mExampleList;
    private ArrayList<AddItem> examplelistfull;
    private OnItemClickListener mListener;

    @Override
    public Filter getFilter() {
        return examplefilter;
    }
    private Filter examplefilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<AddItem> filteredList=new ArrayList<>();
           if (constraint==null||constraint.length()==0){
               filteredList.addAll(examplelistfull);

           }
           else {
               String filterpattern=constraint.toString().toLowerCase().trim();

               for (AddItem item:examplelistfull){
                   if(item.getTextView().toLowerCase().contains(filterpattern)){
                       filteredList.add(item);
                   }
               }
           }
            FilterResults results=new FilterResults();
           results.values=filteredList;
        return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mExampleList.clear();
            mExampleList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    public interface OnItemClickListener {

        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class ExampleViewHolder extends RecyclerView.ViewHolder  {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mDeleteImage;


        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {//labar away statica kawata abe aw parametera da bneyn
            super(itemView);
            mImageView = itemView.findViewById(R.id.IV);
            mTextView1 = itemView.findViewById(R.id.t1);
            mTextView2 = itemView.findViewById(R.id.t2);
            mDeleteImage = itemView.findViewById(R.id.deletebutton);

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }


    }



    public ExampleAdapter(ArrayList<AddItem> exampleList) {
        mExampleList = exampleList;
        examplelistfull=new ArrayList<>(exampleList);
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        AddItem currentItem = mExampleList.get(position);

        holder.mImageView.setImageURI(currentItem.getImageView());
        holder.mTextView1.setText(currentItem.getTextView());
        holder.mTextView2.setText(currentItem.getTextView1());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}