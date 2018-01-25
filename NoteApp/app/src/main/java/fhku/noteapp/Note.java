package fhku.noteapp;

public class Note {

    protected int id;
    protected String title;
    protected String note;
    protected int lastEdited;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(int lastEdited) {
        this.lastEdited = lastEdited;
    }

    public void setLastEditedToNow() {
        int timestamp = (int) (System.currentTimeMillis() / 1000L);
        this.lastEdited = timestamp;
    }

}
