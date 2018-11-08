package esd.scos.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class DataToResponseUtil {
	public static void putDataToResponse(HttpServletResponse response,String jsonValue) {
		PrintWriter out=null;
		try {
			out=response.getWriter();
			out.write(jsonValue);
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(out!=null) {
				out.close();
			}
		}
	}
}
