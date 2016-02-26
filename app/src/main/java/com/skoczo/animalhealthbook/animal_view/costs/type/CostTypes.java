package com.skoczo.animalhealthbook.animal_view.costs.type;

import android.content.Context;
import android.util.Log;

import com.skoczo.animalhealthbook.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skoczo on 13.02.16.
 */
public class CostTypes {
    private static CostTypes instance;
    public final Map<Integer, CostType> types = new HashMap<Integer, CostType>();

    private CostTypes(Context ctx) {
        types.put(1, new CostType(1, ctx.getString(R.string.vaccine)));
        types.put(2, new CostType(2, ctx.getString(R.string.treatment)));
    }

    public static CostTypes getIntance() {
        return instance;
    }


    public static void initialize(Context ctx) {
        if (instance == null) {
            instance = new CostTypes(ctx);
        }

    }

    public CostType getCostType(Integer id) {
        CostType t = types.get(id);

        if (t == null) {
            Log.e(this.getClass().getName(), "wrong id: " + id);
        }

        return t;
    }
}
