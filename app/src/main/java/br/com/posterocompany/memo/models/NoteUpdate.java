package br.com.posterocompany.memo.models;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Francisco on 12/03/2016.
 */
public class NoteUpdate extends SugarRecord implements Serializable{

    public String oldText;
    public String newText;
    public Date dateCreate;
    public Date dateUpdate;

    public Note note;

    public NoteUpdate() {

    }

}
