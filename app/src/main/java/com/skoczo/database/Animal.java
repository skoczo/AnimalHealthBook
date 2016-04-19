package com.skoczo.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by skoczo on 01.04.16.
 */
@DatabaseTable(tableName = "ANIMALS")
public class Animal {

    @DatabaseField(index = true, generatedId = true)
    private Integer _ID;

    @DatabaseField
    private String NAME;

    @DatabaseField
    private Date BIRTH;

    @DatabaseField
    private Integer WEIGHT;

    @DatabaseField
    private Integer TYPE;

    @DatabaseField
    private String BREED;

    public Integer get_ID() {
        return _ID;
    }

    public void set_ID(Integer _ID) {
        this._ID = _ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Date getBIRTH() {
        return BIRTH;
    }

    public void setBIRTH(Date BIRTH) {
        this.BIRTH = BIRTH;
    }

    public Integer getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(Integer WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public Integer getTYPE() {
        return TYPE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }

    public String getBREED() {
        return BREED;
    }

    public void setBREED(String BREED) {
        this.BREED = BREED;
    }
}
