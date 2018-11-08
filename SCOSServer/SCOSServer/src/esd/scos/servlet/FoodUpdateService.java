package esd.scos.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.gson.Gson;

import esd.scos.model.FoodItem;
import esd.scos.model.ResultBody;
import esd.scos.utils.DataToResponseUtil;
import esd.scos.utils.FoodInformationUtil;
import esd.scos.utils.Global;
import esd.scos.utils.ObjectToXmlUtil;

public class FoodUpdateService extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int Type=0;
	private static final int JSON_TYPE=1;
	private static final int XML_TYPE=2;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//�жϷ�����������
		Type=request.getContentType().equals("application/json")?JSON_TYPE:XML_TYPE;
		request.setCharacterEncoding("utf-8");
		
		ArrayList<FoodItem> foodArrayList=FoodInformationUtil.getFoodArrayList(Global.AMOUNT);
		
		if(Type==JSON_TYPE) {
//			System.out.println("��ʼ����JSON");
			//����JSON
			Date startDate = new Date(System.currentTimeMillis());
			String foodListJson=new Gson().toJson(foodArrayList);
			Date endDate = new Date(System.currentTimeMillis());
            long duration = endDate.getTime() - startDate.getTime();
            System.out.println("��Ʒ����Ϊ"+String.valueOf(Global.AMOUNT)+"ʱ������JSONʱ��"+String.valueOf(duration)+"ms");
            //����JSON
            response.setContentType("application/json;charset=utf-8");
			ResultBody resultBody=new ResultBody(1,"����ɹ�",foodListJson);
			String resultJson=new Gson().toJson(resultBody);
			DataToResponseUtil.putDataToResponse(response, resultJson);
			Type=0;
		}else if(Type==XML_TYPE) {
			//����XML
			Date startDate = new Date(System.currentTimeMillis());
			Element foodArrayListString =ObjectToXmlUtil.foodItemToXml(foodArrayList);
			Date endDate = new Date(System.currentTimeMillis());
            long duration = endDate.getTime() - startDate.getTime();
            System.out.println("��Ʒ����Ϊ"+String.valueOf(Global.AMOUNT)+"ʱ������XMLʱ��"+String.valueOf(duration)+"ms");
            //����XML
			response.setContentType("text/xml;charset=utf-8");
			Document foodDoc=DocumentHelper.createDocument();
			Element result=foodDoc.addElement("Result");//���ڵ�
			Element resultCode=result.addElement("RESULTCODE");
			resultCode.addText("1");
			Element message=result.addElement("message");
			message.addText("����ɹ�");
			result.add(foodArrayListString);
			DataToResponseUtil.putDataToResponse(response, foodDoc.asXML());
			Type=0;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
