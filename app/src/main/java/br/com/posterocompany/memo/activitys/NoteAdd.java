package br.com.posterocompany.memo.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.posterocompany.memo.R;
import br.com.posterocompany.memo.models.Note;

public class NoteAdd extends AppCompatActivity {

    private Note note;

    private EditText txtText;
    private Date dateCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        txtText = (EditText) findViewById(R.id.txtText);
        dateCreate = new Date();
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
                this.saveNote();
                getIntent().putExtra("noteId",note.getId());
                this.setResult(RESULT_OK, getIntent());
                this.finish();
                return true;
        }
        return false;
    }

    public void saveNote() {
        String text = txtText.getText().toString();
        note = new Note();

        note.text = text;
        note.dateCreate = dateCreate;
        note.dateSave = new Date();

        note.save();

    }
}
