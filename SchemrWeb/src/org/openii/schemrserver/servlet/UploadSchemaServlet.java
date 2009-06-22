package org.openii.schemrserver.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadSchemaServlet
 */
public class UploadSchemaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(200);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Got an upload schema request");
		// Create a factory for disk-based file items
		FileItemFactory factory = new DiskFileItemFactory();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// Parse the request
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
			   FileItem item = (FileItem) iter.next();
			   //handling a normal form-field
			   if (item.isFormField()) {
			      System.out.println("Got a form field");
			      String name = item.getFieldName();
			      String value = item.getString();
			      System.out.println("Name:"+name+",Value:"+value);
			   } else {//handling file loads
			      System.out.println("Not form field");
			      String fieldName = item.getFieldName();
			      String fileName = item.getName();
			      String contentType = item.getContentType();
			      boolean isInMemory = item.isInMemory();
			      long sizeInBytes = item.getSize();
			      System.out.println("Field Name:"+fieldName+",File Name:"+fileName);
			      System.out.println("Content Type:"+contentType+",Is In Memory:"+isInMemory+",Size:"+sizeInBytes);

			      byte[] data = item.get();
			      FileOutputStream fileOutSt = new FileOutputStream("temp"+File.pathSeparator+fileName);
			      fileOutSt.write(data);
			      fileOutSt.close();
			   }
			}
			response.setStatus(200);
		} catch (FileUploadException e) {
			e.printStackTrace();
			response.sendError(406);
		}

	}

}
