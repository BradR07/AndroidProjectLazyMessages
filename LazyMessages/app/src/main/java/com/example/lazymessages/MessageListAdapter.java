package com.example.lazymessages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lazymessages.databinding.ItemMessageListBinding;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    List<Messages> messageList = new ArrayList<>() ;
    private OnSmsClickLstener mCallBack ;

    public void FillArray(List<Messages> messageList){
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    public MessageListAdapter(OnSmsClickLstener mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        ItemMessageListBinding binding = ItemMessageListBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new MessageListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessageListViewHolder)holder).bindTo(messageList.get(position));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
    public class MessageListViewHolder extends RecyclerView.ViewHolder {
        private ItemMessageListBinding binding ;

        public MessageListViewHolder(ItemMessageListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindTo(Messages messages){
            binding.tvItemMessageList.setText(messages.titre);
            binding.tvItemMessageList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onSmsClick(messages);
                }
            });
        }

    }

}
