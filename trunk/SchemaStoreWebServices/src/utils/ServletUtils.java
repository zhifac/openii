package utils;

import javax.servlet.http.HttpServletRequest;

/** Runs servlet utilities */
public class ServletUtils
{
	/** Retrieves the web service URL */
	static public String getWebServiceURL(HttpServletRequest request)
	{
		String url = request.getRequestURL().toString();
		String contextPath = request.getContextPath();
		return url.replaceAll(contextPath+".*",contextPath);
	}

	/** Retrieve the main page URL for the specified service */
	static public String getMainPageURL(HttpServletRequest request)
		{ return getWebServiceURL(request) + "/index.html"; }
	
	/** Retrieve the URL for displaying a supplier and what they are supplying */
	static public String getSupplierURL(HttpServletRequest request, int id)
		{ return getWebServiceURL(request) + "/displaySupplier.jsp?id=" + id; }
}