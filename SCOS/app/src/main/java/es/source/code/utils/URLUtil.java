package es.source.code.utils;

import android.util.Log;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import es.source.code.model.FoodItem;
import es.source.code.model.ResultXml;

public class URLUtil {

    public static String streamToString(InputStream is) {
        try {
            if(is==null){
                Log.d(Global.TAG.SIX_TAG,"InputStream为空");
                return null;
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;

            while (true) {
                len = is.read(buffer);
                if(len!=-1){
                    outputStream.write(buffer, 0, len);
                }else{
                    break;
                }
            }
            Log.d(Global.TAG.JSON_XML_TAG, "Json流的长度为：" + outputStream.size());
            byte[] byteArray = outputStream.toByteArray();
            String str=new String(byteArray);
            Log.d(Global.TAG.SIX_TAG,str);
            outputStream.close();
            is.close();
            return str;
        } catch (IOException e) {
            Log.d(Global.TAG.SIX_TAG,"流转字符串过程出错");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            Log.d(Global.TAG.SIX_TAG,exception);
            return null;
        }
    }

    public static void resultFromStreamTOString(InputStream is) {
        String xml=null;
        ArrayList<FoodItem> foodList = new ArrayList<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len;

            while (true) {
                len = is.read(buffer);
                if (len != -1) {
                    outputStream.write(buffer, 0, len);
                } else {
                    break;
                }
            }
            Log.d(Global.TAG.JSON_XML_TAG, "XML流的长度为：" + outputStream.size());
            xml=new String(outputStream.toByteArray(), "utf-8");
            is.close();
            outputStream.close();
        } catch (IOException e) {
            Log.d(Global.TAG.SIX_TAG, "流转字符串过程出错");
        }

        try {
            // 读取并解析XML文档
            // SAXReader就是一个管道，用一个流的方式，把xml文件读出来
            //
            // SAXReader reader = new SAXReader(); //User.hbm.xml表示你要解析的xml文档
            // Document document = reader.read(new File("User.hbm.xml"));

            if(xml==null){
                Log.d(Global.TAG.SIX_TAG, "xml为空");
                return;
            }

            Date startDate = new Date(System.currentTimeMillis());
            // 下面的是通过解析xml字符串的
            Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            Log.d(Global.TAG.SIX_TAG, "根节点：" + rootElt.getName()); // 拿到根节点的名称
            Log.d(Global.TAG.SIX_TAG, "根节点子节点个数：" + rootElt.nodeCount()); // 拿到根节点的名称
            int num=0;
            for (Iterator iter = rootElt.elementIterator(Global.ELEMENT_ID.ROOT); iter.hasNext(); ) //对所有food子节点进行遍历
            {
                Element elm = (Element) iter.next();
                List nodes = elm.elements(Global.ELEMENT_ID.FOOD);
                for (Iterator it = nodes.iterator(); it.hasNext(); ) {
                    FoodItem foodItem = new FoodItem();
                    Element recordEle = (Element) it.next();
                    String name = recordEle.elementTextTrim(Global.ELEMENT_ID.NAME);
                    String price = recordEle.elementTextTrim(Global.ELEMENT_ID.PRICE);
                    String sort = recordEle.elementTextTrim(Global.ELEMENT_ID.SORT);
                    String store = recordEle.elementTextTrim(Global.ELEMENT_ID.STORE);
                    num++;
                }
            }
            Date endDate = new Date(System.currentTimeMillis());
            long duration = endDate.getTime() - startDate.getTime();
            Global.URL_FOOD_AMOUNT = num;
                Log.d(Global.TAG.JSON_XML_TAG, "菜品数量为"+String.valueOf(num)+"时，解析XML时间"+String.valueOf(duration)+"ms");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
