package com.justep.bmap.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 
 * @author 007slm
 * @email 007slm@163.com
 *
 */
public class BMapApiServlet  extends HttpServlet{
	
	private static final long serialVersionUID = -5539311031616806855L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String action = req.getParameter("action");
			if ("convertLocation".equals(action)) {
				convertLocation(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
	private void convertLocation(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String longitude = req.getParameter("longitude");
		String latitude = req.getParameter("latitude");
		String url = "http://api.map.baidu.com/geoconv/v1/?coords="+longitude+ ","+ latitude +"&from=1&to=5&ak=xifH76TpyIL1cvnTzuEP0bpq";
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(httpGet);
		String resultContent = new BasicResponseHandler().handleResponse(response);
		resp.getWriter().write(resultContent);
	}
}
