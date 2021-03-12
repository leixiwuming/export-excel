package com.zxz.common.poi.excel.function.abs;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSpecialRow<T> {
    private Map<Predicate<Integer>, CellStyle> rowPredicates;
    private List<SpecialRow<T>> specialRows;


    public abstract void configSpecialRow();

    public BaseSpecialRow() {
        rowPredicates = new HashMap<>();
        specialRows = new ArrayList<>();
        configSpecialRow();
    }

    public Map<Predicate<Integer>, CellStyle> getRowPredicates() {
        return rowPredicates;
    }

    public List<SpecialRow<T>> getSpecialRows() {
        return specialRows;
    }


}
