package com.itcraftsolution.todoplanner.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_Notes")
public class Notes {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Title")
    private String notesTitle;

    @ColumnInfo(name = "Desc")
    private String notes;

    @ColumnInfo(name = "Date")
    private String notesDate;

    @ColumnInfo(name = "Pin")
    private boolean pin;

    @ColumnInfo(name = "Colors")
    private String color;

//    @ColumnInfo(name = "imagePaths")
//    private String image_path;

    public Notes(String notesTitle, String notes, String notesDate, boolean pin, String color) {
        this.notesTitle = notesTitle;
        this.notes = notes;
        this.notesDate = notesDate;
        this.pin = pin;
        this.color = color;
    }

    public Notes(int id, String notesTitle, String notes, String notesDate, boolean pin, String color) {
        this.id = id;
        this.notesTitle = notesTitle;
        this.notes = notes;
        this.notesDate = notesDate;
        this.pin = pin;
        this.color = color;
    }

    public Notes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotesDate() {
        return notesDate;
    }

    public void setNotesDate(String notesDate) {
        this.notesDate = notesDate;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
