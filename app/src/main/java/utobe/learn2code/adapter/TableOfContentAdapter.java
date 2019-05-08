package utobe.learn2code.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import utobe.learn2code.R;
import utobe.learn2code.model.Result;
import utobe.learn2code.model.Topic;

public class TableOfContentAdapter extends
        RecyclerView.Adapter<TableOfContentAdapter.ViewHolder> implements IAbstractAdapter {

    private final ArrayList<String> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public TableOfContentAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_table_of_contents, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // TODO: megszépíteni
        Topic topic = (Topic) entityManager.getEntity(mData.get(position));

        SpannableStringBuilder title = new SpannableStringBuilder(topic.getTitle());

        if (topic.getTest()) {

            if (topic.getResult() != null) {
                title
                        .append(" ")
                        .append(String.valueOf(Math.round(((Result) entityManager.getEntity(topic.getResult())).getResult() * 100)));
            } else {
                title.append(" 0");
            }
            title.append("%");

            int startIndex = topic.getTitle().length();
            int endIndex = topic.getTitle().length();

            title.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, 0);
            title.setSpan(new ForegroundColorSpan(Color.rgb(70, 130, 180)), startIndex, endIndex, 0);
        }


        holder.myTextView.setText(title);
        holder.myButton.setText(R.string.table_of_contents_start);

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public Topic getItem(int id) {
        return (Topic) entityManager.getEntity(mData.get(id));
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView myTextView;
        final Button myButton;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.topic_name);
            myButton = itemView.findViewById(R.id.topic_button);

            myButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
