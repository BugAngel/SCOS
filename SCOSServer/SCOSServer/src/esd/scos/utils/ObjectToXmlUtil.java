package esd.scos.utils;

import java.util.ArrayList;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Document;

import esd.scos.model.FoodItem;

public class ObjectToXmlUtil {
	public static Element foodItemToXml(ArrayList<FoodItem> foodArrayList) {
		Document foodDoc=DocumentHelper.createDocument();
		Element root=foodDoc.addElement("root");//根节点
		for(FoodItem foodItem:foodArrayList) {
			//菜品中间节点
			Element foodItemElement=root.addElement("food");
			//菜品的子节点，也就是各项属性
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
