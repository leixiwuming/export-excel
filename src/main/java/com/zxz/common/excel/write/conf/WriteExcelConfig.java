package com.zxz.common.excel.write.conf;

import com.zxz.common.excel.convert.BaseConvert;
import com.zxz.common.excel.convert.set.WriteConverts;
import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.excel.reflect.jdk.JDKReflect;
import com.zxz.common.excel.util.GreaterMap;
import com.zxz.common.excel.util.InitThreadLocal;
import com.zxz.common.excel.write.function.SpecialCell;
import com.zxz.common.excel.write.function.SpecialRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class WriteExcelConfig {

    public static String DEFAULT_SHEET_NAME = "sheet";

    //反射策略
    private static ThreadLocal<ReflectStrategy> reflectStrategyThreadLocal = new InitThreadLocal<>(new JDKReflect());

    public static void setReflectStrategy(ReflectStrategy reflectStrategy) {
        reflectStrategyThreadLocal.set(reflectStrategy);
    }

    public static ReflectStrategy getReflectStrategy() {
        return reflectStrategyThreadLocal.get();
    }


    //转换器
    private static ThreadLocal<BaseConvert> convertThreadLocal = new InitThreadLocal<>(new WriteConverts());

    public static void setConvertThreadLocal(BaseConvert baseConvert) {
        convertThreadLocal.set(baseConvert);
    }

    public static BaseConvert getConvertThreadLocal() {
        return convertThreadLocal.get();
    }


    //一些默认样式
    private static ThreadLocal<CellStyleConfig> cellStyleConfigThreadLocal = new InitThreadLocal<>(new DefaultCellStyleConfig());

    public static CellStyleConfig getCellStyleConfigThreadLocal() {
        return cellStyleConfigThreadLocal.get();
    }

    public static void setCellStyleConfigThreadLocal(CellStyleConfig cellStyleConfig) {
        cellStyleConfigThreadLocal.set(cellStyleConfig);
    }


    //宽度计算方式
    private static ThreadLocal<Function<Integer, Integer>> calculationWidthThreadLocal = new InitThreadLocal<>(width -> width * 256 + 196);

    public static void setCalculationWidthThreadLocal(Function<Integer, Integer> function) {
        calculationWidthThreadLocal.set(function);
    }

    public static Function<Integer, Integer> getCalculationWidthThreadLocal() {
        return calculationWidthThreadLocal.get();
    }

    //存储每一列的宽度，为当列最长字符的宽度 扩展时千万记得保存 GreaterMap put只会在比原值大的时候添加
    private static ThreadLocal<Map<Integer, Integer>> cellMaxWidthThreadLocal = new InitThreadLocal<>(new GreaterMap<>());

    public static void putCellWidth(int cellIndex, int width) {
        getCellWidth().put(cellIndex, width);
    }

    public static Map<Integer, Integer> getCellWidth() {
        return cellMaxWidthThreadLocal.get();
    }

    //特殊行处理,每一行都会先执行集合内的函数
    private static ThreadLocal<List<SpecialRow>> specialRowThreadLocal = new InitThreadLocal<>(new ArrayList<>());

    public static List<SpecialRow> getSpecialRowThreadLocal() {
        return specialRowThreadLocal.get();
    }

    public static void addSpecialRowThreadLocal(SpecialRow specialRow) {
        specialRowThreadLocal.get().add(specialRow);
    }

    //特殊列的处理
    private static ThreadLocal<List<SpecialCell>> specialCellThreadLocal = new InitThreadLocal<>(new ArrayList<>());

    public static List<SpecialCell> getSpecialCellThreadLocal() {
        return specialCellThreadLocal.get();
    }

    public static void addSpecialCellThreadLocal(SpecialCell specialCell) {
        specialCellThreadLocal.get().add(specialCell);
    }


    public static void remove() {
        specialCellThreadLocal.remove();
        specialRowThreadLocal.remove();
        reflectStrategyThreadLocal.remove();
        calculationWidthThreadLocal.remove();
        cellMaxWidthThreadLocal.remove();
        convertThreadLocal.remove();
        cellStyleConfigThreadLocal.remove();
    }
    public static void removeCellWidth() {
        cellMaxWidthThreadLocal.remove();
    }

}
