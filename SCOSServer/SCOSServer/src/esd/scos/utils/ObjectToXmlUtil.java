package esd.scos.utils;

import java.util.ArrayList;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Document;

import esd.scos.model.FoodItem;

public class ObjectToXmlUtil {
	public static Element foodItemToXml(ArrayList<FoodItem> foodArrayList) {
		Document foodDoc=DocumentHelper.createDocument();
		Element root=foodDoc.addElement("root");//���ڵ�
		for(FoodItem foodItem:foodArrayList) {
			//��Ʒ�м�ڵ�
			Element foodItemElement=root.addElement("food");
			//��Ʒ���ӽڵ㣬Ҳ���Ǹ�������
			Element name=foodItemElement.addElement("name");
			name.addText(foodItem.getName());
			Element price=foodItemElement.addElement("price");
			price.addText(String.valueOf(foodItem.getPrice()));
			Element sort=foodItemElement.addElement("sort");
			sort.addText(String.valueOf(foodItem.getSort()));
			Element store=foodItemElement.addElement("store");
			store.addText(String.valueOf(foodItem.getStore()));
		}
		return root;
	}
}
