package br.com.posterocompany.memo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.posterocompany.memo.R;
import br.com.posterocompany.memo.models.Note;

/**
 * Created by Francisco on 12/03/2016.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> notes;

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_note, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = notes.get(position);

        holder.lblText.setText(note.text);
        holder.lblDateCreate.setText(note.dateCreate.toString());
        holder.lblDateSave.setText(note.dateSave.toString());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lblText;
        private TextView lblDateCreate;
        private TextView lblDateSave;

        public ViewHolder(View itemView) {
            super(itemView);

            lblText = (TextView) itemView.findViewById(R.id.lblText);
            lblDateCreate = (TextView) itemView.findViewById(R.id.lblDateCreate);
            lblDateSave = (TextView) itemView.findViewById(R.id.lblDateUpdate);

        }
    }
}
