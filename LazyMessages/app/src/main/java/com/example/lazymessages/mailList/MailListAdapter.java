package com.example.lazymessages.mailList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lazymessages.createMail.MailEntity;
import com.example.lazymessages.databinding.ItemMailListBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView de la liste de message
 */
public class MailListAdapter extends RecyclerView.Adapter {
    List<MailEntity> mailList = new ArrayList<>() ;
    private final OnMailClickListener mCallBack ;

    /**
     * @param mailList liste de message
     */
    public void FillArray(List<MailEntity> mailList){
        this.mailList.clear();
        this.mailList.addAll(mailList);
        notifyDataSetChanged();
    }

    public MailListAdapter(OnMailClickListener mCallBack)
    {
        this.mCallBack = mCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        ItemMailListBinding binding = ItemMailListBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new MessageListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessageListViewHolder)holder).bindTo(mailList.get(position));
    }

    @Override
    public int getItemCount() {
        return mailList.size();
    }

    public class MessageListViewHolder extends RecyclerView.ViewHolder {
        private final ItemMailListBinding binding ;

        public MessageListViewHolder(ItemMailListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(MailEntity mails){
            binding.tvItemMessageList.setText("Objet: "+mails.objet);
            binding.destinataire.setText("Destinataire: "+mails.destinataire);
            binding.date.setText("Programmé pour le: "+mails.date);
            binding.detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onMailClick(mails);
                }
            });
            binding.suppr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.deleteMailClicked(mails);
                }
            });
        }
    }
}