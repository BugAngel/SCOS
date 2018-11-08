package es.source.code.model;

import java.util.ArrayList;

public class ResultXml{
    // 结果码
    private int RESULT_CODE;
    // 携带信息
    private String msg;
    // Food列表
    private ArrayList<FoodItem> dataList;

    public ResultXml(){

    }

    public ResultXml(int RESULT_CODE, String msg, ArrayList<FoodItem> dataList) {
        this.RESULT_CODE=RESULT_CODE;
        this.msg=msg;
        this.dataList = dataList;
    }

    public int getRESULT_CODE() {
        return RESULT_CODE;
    }

    public void setRESULT_CODE(int RESULT_CODE) {
        this.RESULT_CODE = RESULT_CODE;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<FoodItem> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<FoodItem> dataList) {
        this.dataList = dataList;
    }
}
