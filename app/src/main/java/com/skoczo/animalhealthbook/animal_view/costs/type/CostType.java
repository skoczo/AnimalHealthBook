package com.skoczo.animalhealthbook.animal_view.costs.type;

/**
 * Created by skoczo on 13.02.16.
 */
public class CostType {
    private Integer id;
    private String name;

    public CostType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
