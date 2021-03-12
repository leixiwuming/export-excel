package com.zxz.common.poi.excel.function.abs;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;
import java.util.function.Predicate;

public abstract class BaseSpecialCell<T> {
    private Map<Predicate<Cell>, CellStyle> cellPredicates;
    private List<SpecialCell<T>> specialCells;
    private List<Predicate<Cell>> cellKeyPredicate;

    public abstract void configFunction(Workbook workbook);

    public abstract void configSpecialCell();

    public BaseSpecialCell() {
        this.cellPredicates = new LinkedHashMap<>();
        this.specialCells = new ArrayList<>();
        configSpecialCell();
    }

    public Map<Predicate<Cell>, CellStyle> getCellPredicates() {
        return cellPredicates;
    }

    public List<SpecialCell<T>> getSpecialCells() {
        return specialCells;
    }

    public void initCellKeyPredicate() {
        if (getCellPredicates() != null) {
            cellKeyPredicate = Arrays.asList(
                    getCellPredicates().keySet().
                            toArray(new Predicate[getCellPredicates().size()]));
        }
    }

    public List<Predicate<Cell>> getCellKeyPredicate() {
        return cellKeyPredicate;
    }
}
