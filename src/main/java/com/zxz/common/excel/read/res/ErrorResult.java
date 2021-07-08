package com.zxz.common.excel.read.res;

import com.zxz.common.excel.util.StringUtils;

import java.util.List;


public class ErrorResult<T> {
    private T data;
    private List<entiy> errorMsg;

    public String getMsg() {
        if (errorMsg == null || errorMsg.isEmpty()) {
            return StringUtils.EMPTY;
        }
        StringBuilder message = new StringBuilder();
        for (entiy entiy : errorMsg) {
            message.append("第").append(entiy.sheetNum).append("页").append("第").append(entiy.rowNum).append("行")
                    .append("第").append(entiy.cellNum).append("列").append(entiy.errorMsg);
        }
        return message.toString();
    }

    public ErrorResult() {
    }

    public ErrorResult(T data) {
        this.data = data;
    }

    class entiy {
        private int rowNum;
        private int cellNum;
        private int sheetNum;
        private String errorMsg;

        public entiy(int rowNum, int cellNum, int sheetNum, String errorMsg) {
            this.rowNum = rowNum;
            this.cellNum = cellNum;
            this.sheetNum = sheetNum;
            this.errorMsg = errorMsg;
        }
    }

}
