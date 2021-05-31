package com.zxz.common.excel.write;

import com.zxz.common.excel.model.AnnotationMeta;

import java.util.Map;

public class ReadHeadResult {
    //写完头部后的下一列索引
    private int nextEndHeadRowIndex;
    //映射关系
    private Map<Integer, AnnotationMeta> metaMap;

    public int getNextEndHeadRowIndex() {
        return nextEndHeadRowIndex;
    }

    public void setNextEndHeadRowIndex(int nextEndHeadRowIndex) {
        this.nextEndHeadRowIndex = nextEndHeadRowIndex;
    }

    public Map<Integer, AnnotationMeta> getMetaMap() {
        return metaMap;
    }

    public void setMetaMap(Map<Integer, AnnotationMeta> metaMap) {
        this.metaMap = metaMap;
    }

    public ReadHeadResult(int nextEndHeadRowIndex, Map<Integer, AnnotationMeta> metaMap) {
        this.nextEndHeadRowIndex = nextEndHeadRowIndex;
        this.metaMap = metaMap;
    }
}
