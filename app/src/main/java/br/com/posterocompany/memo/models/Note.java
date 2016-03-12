package br.com.posterocompany.memo.models;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Francisco on 12/03/2016.
 */
public class Note extends SugarRecord {

    public String text;
    public Date dateCreate;
    public Date dateSave;

    public Note(){

    }
}
