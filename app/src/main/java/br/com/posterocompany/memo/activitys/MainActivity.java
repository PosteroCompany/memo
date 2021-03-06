package br.com.posterocompany.memo.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import br.com.posterocompany.memo.R;
import br.com.posterocompany.memo.adapters.NotesAdapter;
import br.com.posterocompany.memo.models.Note;
import br.com.posterocompany.memo.utils.DividerItemDecoration;
import br.com.posterocompany.memo.utils.ItemClickSupport;
import br.com.posterocompany.memo.utils.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PopupMenu.OnMenuItemClickListener {

    public static int REQUEST_NOTE_ADD = 1;
    public static int REQUEST_NOTE_EDIT = 2;

    private List<Note> notes;
    private Note selectedNote;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initLayout();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_repository:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/PosteroCompany/memo")));
                break;
            case R.id.nav_contact:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://web-postero.rhcloud.com/")));
                break;
            case R.id.nav_export:
                this.exportNotes();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initLayout() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, NoteAddActivity.class), REQUEST_NOTE_ADD);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        notes = Note.listAllOrdered();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotesAdapter(notes);
        recyclerView.setAdapter(adapter);

        setRecyclerViewItemClicks();

    }

    public void setRecyclerViewItemClicks() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(MainActivity.this, NoteEditActivity.class);
                intent.putExtra("noteId", notes.get(position).getId());
                startActivityForResult(intent, REQUEST_NOTE_EDIT);
            }
        });
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                selectedNote = notes.get(position);

                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v, Gravity.END);
                popupMenu.setOnMenuItemClickListener(MainActivity.this);
                popupMenu.inflate(R.menu.popup_note_menu);
                popupMenu.show();
                return true;
            }
        });
    }

    public void deleteNote() {
        notes.remove(selectedNote);
        selectedNote.delete();
        adapter.notifyDataSetChanged();
        Snackbar.make(recyclerView, this.getString(R.string.note_deleted), Snackbar.LENGTH_SHORT).show();

    }

    public void openHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("noteId", selectedNote.getId());
        startActivity(intent);
    }

    public void exportNotes() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filename = "memo_notes.txt";
            String outputString = "";
            File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/PosteroCompany/Memo");

            myDir.mkdirs();

            for (Note note : Note.listAll(Note.class)) {

                outputString += "Note ID: " + note.getId() + "\n";
                outputString += "Note: " + note.text + "\n";
                outputString += "Create Date: " + Text.toDateTime(note.dateCreate) + "\n";
                outputString += "Save Date: " + Text.toDateTime(note.dateSave) + "\n";
                outputString += "-----------------------\n";
            }

            try {
                File file = new File(myDir, filename);
                if (file.exists()) {
                    file.delete();
                }

                FileOutputStream fos = new FileOutputStream(file);

                fos.write(outputString.getBytes());
                fos.flush();
                fos.close();
                //}
            } catch (Exception e) {
                e.printStackTrace();
            }
            Snackbar.make(recyclerView, this.getString(R.string.note_exported), Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(recyclerView, this.getString(R.string.sd_card_error), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_NOTE_ADD) {
            if (resultCode == RESULT_OK) {
                Long noteId = data.getLongExtra("noteId", 0);
                if (noteId != 0) {
                    Note note = Note.findById(Note.class, noteId);
                    notes.add(0, note);
                    adapter.notifyDataSetChanged();
                }
            }
        } else if (requestCode == REQUEST_NOTE_EDIT) {
            if (resultCode == RESULT_OK) {
                Long noteId = data.getLongExtra("noteId", 0);
                if (noteId != 0) {
                    notes = Note.listAllOrdered();
                    adapter = new NotesAdapter(notes);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.act_delete:
                this.deleteNote();
                return true;
            case R.id.act_note_history:
                this.openHistory();
                return true;
        }
        return false;
    }
}
