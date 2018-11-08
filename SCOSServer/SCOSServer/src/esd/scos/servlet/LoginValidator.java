package esd.scos.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import esd.scos.model.LoginParam;
import esd.scos.model.ResultBody;
import esd.scos.utils.DataToResponseUtil;
import esd.scos.utils.RegexUtil;

public class LoginValidator extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		StringBuilder stringBuilder=new StringBuilder();
		String line;
		LoginParam loginParam;
		
		//��ȡ��������StringBuilder��
		BufferedReader reader=request.getReader();
		while((line=reader.readLine())!=null) {
			stringBuilder.append(line);
		}

		Gson gson=new Gson();
		loginParam=gson.fromJson(stringBuilder.toString(), LoginParam.class);
		if(loginParam==null) {
			System.out.println("loginParamΪ��");
		}
	
		ResultBody resultBody=new ResultBody(0,"��¼ʧ��","��½��Ϣ");
		if(loginParam!=null && RegexUtil.checkUsernamePassword(loginParam.getUserName(), loginParam.getPassWord())) {
			resultBody.setRESULT_CODE(1);
			resultBody.setMsg("��½�ɹ�");
		}
		
		String responseString=new Gson().toJson(resultBody);
		DataToResponseUtil.putDataToResponse(response, responseString);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
