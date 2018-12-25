<%--
  Created by IntelliJ IDEA.
  User: 林木
  Date: 2018/12/24
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>大功告成</title>
  </head>
  <body>
  <form action="<%=request.getContextPath()%>/testServlet" ethod="POST">
    <input type="submit"value="提交">
  </form>
  </body>
</html>
