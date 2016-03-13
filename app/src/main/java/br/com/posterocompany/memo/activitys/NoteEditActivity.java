package br.com.posterocompany.memo.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

import br.com.posterocompany.memo.R;
import br.com.posterocompany.memo.models.Note;
import br.com.posterocompany.memo.models.NoteUpdate;

public class NoteEditActivity extends AppCompatActivity {
    private Note note;
    private NoteUpdate update;

    private EditText txtText;
    private Date dateCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        txtText = (EditText) findViewById(R.id.txtText);
        dateCreate = new Date();
        Long id = getIntent().getLongExtra("noteId", 0);
        if (id != 0) {
            note = Note.findById(Note.class,id);
            txtText.setText(note.text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_note_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.act_save:
                if (!txtText.getText().toString().trim().isEmpty()) {
                    this.updateNote();
                    getIntent().putExtra("noteId", note.getId());
                    this.setResult(RESULT_OK, getIntent());
                    this.finish();
                    return true;
                }
        }
        return false;
    }

    public void updateNote() {
        String text = txtText.getText().toString().trim();
        update = new NoteUpdate();

        update.oldText = note.text.trim();
        update.newText = text;
        update.dateCreate = dateCreate;
        update.dateUpdate = new Date();
        update.note = note;
        update.save();

        note.text = update.newText;
        note.dateSave = update.dateUpdate;
        note.save();

    }
}
