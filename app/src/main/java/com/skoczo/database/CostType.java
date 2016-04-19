package com.skoczo.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by skoczo on 19.04.16.
 */
@DatabaseTable(tableName = "COST_TYPE")
public class CostType {

    @DatabaseField(index = true, generatedId = true)
    private Integer _ID;

    @DatabaseField
    private String TYPE;
}
