package com.mcmacker4.cryptnote.notes;


import java.io.Serializable;

public class Note implements Serializable {

    private String title;
    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTitlePreview() {
        if(title.length() <= 25)
            return title;
        return title.substring(0, 25).trim().concat("…");
    }

    String getContentPreview() {
        if(content.length() <= 100)
            return content;
        return content.substring(0, 100).trim().concat("…");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public void apply(Note note) {
        setTitle(note.getTitle());
        setContent(note.getContent());
    }

}
