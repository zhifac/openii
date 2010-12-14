<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.BufferedWriter"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.ArrayList"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%
    	// Holds the temporary file
    	File tempFile = null;
    
  		try {
  			// Prepare to retrieve data from the input stream
 			DataInputStream in = new DataInputStream (request.getInputStream());
  			int formDataLength = request.getContentLength();
  			byte dataBytes[] = new byte[formDataLength];
 
  			// Retrieve data from the input stream
  			int byteRead = 0, totalBytesRead = 0;
  			while (totalBytesRead < formDataLength)
  			{
  				byteRead = in.read (dataBytes, totalBytesRead, formDataLength);
  				totalBytesRead += byteRead;
  			}
 			String file = new String(dataBytes);

  			// Generate the temporary file
   			String filename = file.substring(file.indexOf("filename=\"")+10);
   			filename = filename.substring (0, filename.indexOf ("\n"));
   			filename = filename.substring (filename.lastIndexOf ("\\") + 1,filename.indexOf ("\""));
  			tempFile = File.createTempFile("ImportedFile", filename);
  			FileOutputStream fileOut = new FileOutputStream(tempFile);

			// Find the start position of the file block
  			int pos;
  			pos = file.indexOf("filename=\"");
  			pos = file.indexOf("\n", pos) + 1;
  			pos = file.indexOf("\n", pos) + 1;
  			pos = file.indexOf("\n", pos) + 1;
  			int startPos = ((file.substring(0, pos)).getBytes()).length;

			// Find the end position of the file block
  			String contentType = request.getContentType();
 			int lastIndex = contentType.lastIndexOf("=");
  			String boundary = contentType.substring(lastIndex + 1,contentType.length());
   			int boundaryLocation = file.indexOf(boundary, pos) - 4;
  			int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
  			
  			// Upload the file
  			fileOut.write (dataBytes, startPos, (endPos - startPos));
  			fileOut.flush ();
  			fileOut.close ();
	  	}
  		catch(Exception e) { System.out.println("(E)ImportFile: Failed to upload file - " + e.getMessage()); }	
   	%>
  
    <script>
      var filename = parent.document.getElementById('filename').value;
      parent.closeDialog();
      parent.document.getElementById('harmonyApplet').importFile(filename,"<%= tempFile.getName() %>");
    </script>

  </head>
</html>