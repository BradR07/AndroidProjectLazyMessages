package com.example.lazymessages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lazymessages.databinding.ItemMailListBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView de la liste de message
 */
public class MailListAdapter extends RecyclerView.Adapter {

    List<Mails> mailList = new ArrayList<>() ;
    private OnMailClickListener mCallBack ;

    /**
     * @param mailList liste de message
     */
    public void FillArray(List<Mails> mailList){
        this.mailList.clear();
        this.mailList.addAll(mailList);
        notifyDataSetChanged();
    }

    public MailListAdapter(OnMailClickListener mCallBack) {
        this.mCallBack = mCallBack;
    }

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

        private ItemMailListBinding binding ;

        public MessageListViewHolder(ItemMailListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Mails mails){
            binding.tvItemMessageList.setText(mails.objet.toString());
            binding.destinataire.setText(mails.destinataire.toString());
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
