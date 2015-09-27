<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.IOException"%>
<%!
	public Properties loadProperty() {
	InputStream inputStream = null;
	Properties prop = new Properties();
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties");
			prop.load(inputStream);
		} catch(Exception e) {
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