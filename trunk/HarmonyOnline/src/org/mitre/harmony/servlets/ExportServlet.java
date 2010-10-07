package org.mitre.harmony.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet for exporting a mapping */
public class ExportServlet extends HttpServlet
{	
	/** Return the exported mapping */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{ doPost(request,response); }
	
	/** Return the exported mapping */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Access the temporary file
		String tempFilename = (String)request.getParameter("filename");
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File file = new File(tempDir, tempFilename);

		// Generate a name for the file to export
		String filename = tempFilename.replaceAll(".*__", "");

		// Prepare the header to be outputted
		response.setContentType("text;charset=UTF-8");
//		response.setContentType(new MimetypesFileTypeMap().getContentType(file));
		response.setHeader("Content-disposition","attachment; filename="+filename);
		
		// Traverse through all lines stored in the results file and output this information to file
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		byte data[] = new byte[1000];		
		while(in.read(data,0,1000)>=0)
			response.getOutputStream().write(data);
	}
}