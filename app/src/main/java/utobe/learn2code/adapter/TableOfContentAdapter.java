package utobe.learn2code.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import utobe.learn2code.R;
import utobe.learn2code.activity.TopicActivity;
import utobe.learn2code.model.Topic;

public class TableOfContentAdapter extends
        RecyclerView.Adapter<TableOfContentAdapter.ViewHolder> {

    private final List<Topic> langTopics;
    private Context context;

    public TableOfContentAdapter(List<Topic> topics) {
        langTopics = topics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_table_of_contents, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        // Get the data model based on position
        Topic topic = langTopics.get(i);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(topic.getTitle());
        Button button = viewHolder.messageButton;
        button.setText(topic.isUnlocked() ? "Start!" : "Not yet unlocked");
        button.setEnabled(topic.isUnlocked());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TopicActivity.class);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return langTopics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameTextView;
        final Button messageButton;

        ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }
}
