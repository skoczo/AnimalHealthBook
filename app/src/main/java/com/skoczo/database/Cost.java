package com.skoczo.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by skoczo on 19.04.16.
 */
@DatabaseTable(tableName = "COSTS")
public class Cost {

    @DatabaseField(index = true, generatedId = true)
    private Integer _ID;

    @DatabaseField
    private Integer ANIMAL_ID;

    @DatabaseField
    private Date DATE;

    @DatabaseField
    private String TYPE;

    @DatabaseField
    private String COMMENT;

    @DatabaseField
    private float PRICE;

    public Integer getANIMAL_ID() {
        return ANIMAL_ID;
    }

    public void setANIMAL_ID(Integer ANIMAL_ID) {
        this.ANIMAL_ID = ANIMAL_ID;
    }

    public String getCOMMENT() {
        return COMMENT;
    }

    public void setCOMMENT(String COMMENT) {
        this.COMMENT = COMMENT;
    }

    public Date getDATE() {
        return DATE;
    }

    public void setDATE(Date DATE) {
        this.DATE = DATE;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public float getPRICE() {
        return PRICE;
    }

    public void setPRICE(float PRICE) {
        this.PRICE = PRICE;
    }

    public Integer get_ID() {
        return _ID;
    }

    public void set_ID(Integer _ID) {
        this._ID = _ID;
    }
}
