package com.zxz.common.poi.excel.read;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public class ReadResult<T> {
    private List<T> dtos;
    private List<String> msg;
    private Workbook workbook;

    public ReadResult() {
        msg = new ArrayList<>();
    }

    public List<T> getDtos() {
        return dtos;
    }

    public ReadResult<T> setDtos(List<T> dtos) {
        if (this.dtos != null) {
            this.dtos.addAll(dtos);
        } else {
            this.dtos = dtos;
        }
        return this;
    }

    public List<String> getMsg() {
        if (this.msg == null) {
            this.msg = new ArrayList<>();
        }
        return msg;
    }

    public ReadResult<T> setMsg(List<String> msg) {
        this.msg = msg;
        return this;
    }

    public void setMsg(String msg) {
        getMsg().add(msg);
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public ReadResult<T> setWorkbook(Workbook workbook) {
        this.workbook = workbook;
        return this;
    }
}
