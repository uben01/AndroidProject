package utobe.learn2code.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVGParseException;
import com.google.firebase.storage.FirebaseStorage;

import java.nio.charset.StandardCharsets;
import java.util.List;

import utobe.learn2code.R;
import utobe.learn2code.model.Language;

public class LanguageSelectAdapter extends
        RecyclerView.Adapter<LanguageSelectAdapter.ViewHolder> implements IAbstractAdapter {

    private final List<Language> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public LanguageSelectAdapter(Context context, List<Language> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_language_select, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (!mData.get(position).getPublished()) {
            holder.notPublishedView.setVisibility(View.VISIBLE);
        }
        holder.titleView.setText(mData.get(position).getName());
        FirebaseStorage.getInstance().getReference(mData.get(position).getIcon())
                .getBytes(1024 * 5)
                .addOnSuccessListener(bytes -> {
                    try {
                        SVG svg = SVG.getFromString(new String(bytes, StandardCharsets.UTF_8));
                        holder.myImageView.setSVG(svg);
                    } catch (SVGParseException e) {
                        e.printStackTrace();
                    }
                });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView titleView;
        final TextView notPublishedView;
        final SVGImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.txt_item_language);
            notPublishedView = itemView.findViewById(R.id.txt_item_language_not_published);
            myImageView = itemView.findViewById(R.id.img_item_language);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Language getItem(int id) {
        return mData.get(id);
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