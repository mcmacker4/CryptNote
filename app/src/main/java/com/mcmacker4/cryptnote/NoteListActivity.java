package com.mcmacker4.cryptnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mcmacker4.cryptnote.notes.Note;
import com.mcmacker4.cryptnote.notes.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    public static final int
            ADD_NEW_NOTE_REQUEST = 1,
            EDIT_NOTE_REQUEST = 2;

    ArrayAdapter<Note> adapter;
    List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView noteListView = (ListView) findViewById(R.id.noteListView);
        noteList = new ArrayList<>();
        adapter = new NoteAdapter(this, noteList);
        noteListView.setAdapter(adapter);

        adapter.add(new Note("Hello World", "This is a test note."));

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editNoteInPosition(position);
            }
        });

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmAndDeleteNote(position);
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addNoteBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });
    }

    private void addNewNote() {
        Intent intent = new Intent(this, EditNoteActivity.class);
        startActivityForResult(intent, ADD_NEW_NOTE_REQUEST);
    }

    private void editNoteInPosition(int position) {
        Note note = adapter.getItem(position);
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("note", note);
        intent.putExtra("position", position);
        startActivityForResult(intent, EDIT_NOTE_REQUEST);
    }

    private void confirmAndDeleteNote(int position) {
        final Note note = adapter.getItem(position);
        if (note != null) {
            new AlertDialog.Builder(this).setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete \"" + note.getTitlePreview() + "\"?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.remove(note);
                            Snackbar.make(findViewById(R.id.noteListView), "Note deleted.", Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Result", resultCode + " (OK=" + RESULT_OK + ", CANCEL=" + RESULT_CANCELED + ", FIRST_USER=" + RESULT_FIRST_USER + ")");
        if(resultCode == RESULT_OK) {
            if(requestCode == ADD_NEW_NOTE_REQUEST) {
                Log.d("NoteList", "NEW NOTE request completed.");
                Note note = (Note) data.getSerializableExtra("note");
                adapter.add(note);
            } else if(requestCode == EDIT_NOTE_REQUEST) {
                Log.d("NoteList", "EDIT NOTE request completed.");
                final Note note = (Note) data.getSerializableExtra("note");
                int position = data.getIntExtra("position", -1);
                Note old = adapter.getItem(position);
                if(old != null) old.apply(note);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
