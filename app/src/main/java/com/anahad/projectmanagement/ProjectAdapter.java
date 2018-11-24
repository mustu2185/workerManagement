package com.anahad.projectmanagement;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProjectAdapter extends FirestoreRecyclerAdapter<Project,ProjectAdapter.ProjectHolder> {

    private static final String TAG = ProjectAdapter.class.getSimpleName();
    OnItemClickListner listner;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProjectAdapter(@NonNull FirestoreRecyclerOptions<Project> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProjectHolder holder, int position, @NonNull Project model) {
        Log.d(TAG, "onBindViewHolder: Mustafa: creating card for " + model.getName());
        holder.nameTextView.setText(model.getName());
        int households = model.getHouseholds().size();
        int users = model.getMembers().size() + model.getNonMembers().size();
        String household_string = "Household";
        if (households>1) household_string =household_string+"s";
        String user_string = "User";
        if (users>1) user_string = user_string + "s";
        holder.text1_quant.setText(households + "");
        holder.text1_qual.setText(household_string);
        holder.text2_quant.setText(users+"");
        holder.text2_qual.setText(user_string);
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_general_item,viewGroup,false);
        Log.d(TAG, "onCreateViewHolder: Mustafa: called");
        return new ProjectHolder(v);
    }

    class ProjectHolder extends RecyclerView.ViewHolder
    {
        TextView nameTextView;
        TextView text1_quant;
        TextView text2_quant;
        TextView text1_qual;
        TextView text2_qual;

        public ProjectHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextView = (TextView)itemView.findViewById(R.id.text_view_name);
            this.text1_quant = (TextView)itemView.findViewById(R.id.text_quant_1);
            this.text2_quant = (TextView)itemView.findViewById(R.id.text_quant_2);
            this.text1_qual = (TextView)itemView.findViewById(R.id.text_qual_1);
            this.text2_qual = (TextView)itemView.findViewById(R.id.text_qual_2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listner != null)
                    {
                        listner.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public void deleteItem(int adapterPosition) {
        getSnapshots().getSnapshot(adapterPosition).getReference().delete();
    }

    public interface OnItemClickListner
    {
        void onItemClick(DocumentSnapshot document, int position);
    }

    void setOnItemClickListner(OnItemClickListner listner)
    {
        this.listner = listner;
    }

}
