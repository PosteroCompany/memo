package br.com.posterocompany.memo.models;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Francisco on 12/03/2016.
 */
public class Note extends SugarRecord implements Serializable{

    public String text;
    public Date dateCreate;
    public Date dateSave;

    public Note() {

    }

    public List<NoteUpdate> getUpdates() {
        return NoteUpdate.find(NoteUpdate.class, "note = ?", this.getId().toString());
    }

    public static List<Note> listAllOrdered(){
        return Note.find(Note.class, "", null, "", "id desc", "");
    }
}
