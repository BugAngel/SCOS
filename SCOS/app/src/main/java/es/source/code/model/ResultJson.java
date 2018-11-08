package es.source.code.model;

public class ResultJson {
    // 结果码
    private int RESULT_CODE;
    // 携带信息
    private String msg;
    // 对象jsonString
    private String dataString;

    public ResultJson(int RESULT_CODE, String msg, String dataString) {
        this.RESULT_CODE=RESULT_CODE;
        this.msg=msg;
        this.dataString = dataString;
    }

    public int getRESULT_CODE() {
        return RESULT_CODE;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }
}
