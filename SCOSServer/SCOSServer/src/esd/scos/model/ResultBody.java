package esd.scos.model;

public class ResultBody {
	//״ֵ̬
	private int RESULT_CODE;
	//��Ϣ
	private String msg;
	//Я�������ַ���
	private String dataString;
	
	public ResultBody(int rESULT_CODE, String msg, String dataString) {
		super();
		RESULT_CODE = rESULT_CODE;
		this.msg = msg;
		this.dataString = dataString;
	}
	
	public int getRESULT_CODE() {
		return RESULT_CODE;
	}
	public void setRESULT_CODE(int rESULT_CODE) {
		RESULT_CODE = rESULT_CODE;
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
