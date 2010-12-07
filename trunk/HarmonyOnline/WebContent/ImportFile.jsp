<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.ArrayList"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%
		StringBuffer in = new StringBuffer();
    	
  		try {
	    	// Retrieve content boundary for use in reading in file
	    	String contentType = request.getContentType();
	    	int ind = contentType.indexOf("boundary="); 
			String boundary = contentType.substring(ind+9);
		 
			// Open up a stream to the passed in content
			BufferedReader br=new BufferedReader(new InputStreamReader(request.getInputStream()));
		
			// Parse all file information as a set of query-watchlist match scores
			for (String line; (line=br.readLine())!=null; )       
			{             
				// If a boundary marker doesn't exist, parse as part of the passed in file
				if (line.indexOf(boundary) == -1)
					in.append(line+"\n");
				else
				{
					System.out.println("A:"+br.readLine());
					System.out.println("B:"+br.readLine());
					System.out.println("C:"+br.readLine());
				}
			}
			
			System.out.println(in.toString());
    	} catch(Exception e) {}
	%>
  </head>
</html>