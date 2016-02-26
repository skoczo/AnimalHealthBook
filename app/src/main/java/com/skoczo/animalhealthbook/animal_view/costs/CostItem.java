package com.skoczo.animalhealthbook.animal_view.costs;

import android.util.Log;

import com.skoczo.animalhealthbook.animal_view.costs.type.CostType;
import com.skoczo.animalhealthbook.animal_view.costs.type.CostTypes;

/**
 * Created by skoczo on 13.02.16.
 */
public class CostItem {
    CostTypes types = CostTypes.getIntance();

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public CostType getType() {
        return type;
    }

    public void setType(CostType type) {
        this.type = type;
    }

    public void setType(Integer id) {
        if(types != null) {
            this.type = types.getCostType(id);
        } else {
            Log.e(this.getClass().getName(), "types is null");
        }
    }

    Integer price;
    CostType type;
}
