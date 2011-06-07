<%@page import="utils.UBCUtils"%>
<%@page import="org.eclipse.swt.browser.ProgressListener"%>
<%@page import="org.eclipse.swt.widgets.Shell"%>
<%@page import="org.eclipse.swt.widgets.Display"%>
<%@page import="org.eclipse.swt.SWT"%>
<%@page import="org.eclipse.swt.browser.Browser"%>
<%
  String ubc = request.getParameter("ubc");
  out.println(UBCUtils.getDescription(ubc));
%>
