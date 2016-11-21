package com.mcmacker4.cryptnote;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mcmacker4.cryptnote.notes.Note;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Note note = (Note) getIntent().getSerializableExtra("note");
        if(note != null) {
            setTitle("Edit Note");
            EditText titleField = (EditText) findViewById(R.id.titleField);
            EditText contentField = (EditText) findViewById(R.id.contentField);
            titleField.setText(note.getTitle());
            contentField.setText(note.getContent());
        } else {
            setTitle("New Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.saveBtn) {
            EditText titleField = (EditText) findViewById(R.id.titleField);
            EditText contentField = (EditText) findViewById(R.id.contentField);

            if(titleField.getText().toString().length() <= 0 || contentField.getText().toString().length() <= 0) {
                Snackbar.make(findViewById(R.id.activity_edit_note), "Fields cannot be empty.", Snackbar.LENGTH_SHORT).show();
                return false;
            }

            Note note = new Note(titleField.getText().toString(), contentField.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("note", note);
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
