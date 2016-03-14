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
        List<NoteUpdate> updates = NoteUpdate.find(NoteUpdate.class, "note = ?", this.getId().toString());
        NoteUpdate update = new NoteUpdate();
        update.oldText = "";
        update.dateCreate = this.dateCreate;

        if(updates.size() > 0){
            NoteUpdate firstUpdate = updates.get(0);
            update.newText = firstUpdate.oldText;
            update.dateUpdate = firstUpdate.dateCreate;
        }
        else {
            update.newText = this.text;
            update.dateUpdate = this.dateSave;
        }

        updates.add(0,update);

        return updates;
    }

    public static List<Note> listAllOrdered(){
        return Note.find(Note.class, "", null, "", "id desc", "");
    }
}
