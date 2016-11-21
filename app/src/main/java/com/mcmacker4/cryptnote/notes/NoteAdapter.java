package com.mcmacker4.cryptnote.notes;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mcmacker4.cryptnote.R;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, List<Note> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        if(note == null)
            throw new NullPointerException("Note is null.");

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_list_item, parent, false);

        TextView titleView = (TextView) convertView.findViewById(R.id.title);
        TextView contentView = (TextView) convertView.findViewById(R.id.content);

        titleView.setText(note.getTitlePreview());
        contentView.setText(note.getContentPreview());

        return convertView;
    }
}
