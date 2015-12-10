<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.IOException" %>
<%
   /*
	   功能：读取配置
	   版本：1.0
	   日期：2015-11-21
	   说明： 从类路径里读取配置文件的各个属性
	   以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	   该代码仅供学习和研究使用，只是提供一个参考。
	
	//***********页面功能说明***********
		 该页面可以在本机电脑测试。
	//********************************
	*/
%>
<%!
    public Properties loadProperty() {
        InputStream inputStream = null;
        Properties prop = new Properties();
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties");
            prop.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
        return prop;
    }
%>