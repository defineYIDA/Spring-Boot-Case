import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "myServlet")
public class myServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("***********************************************************************");
        System.out.println("do get");
        System.out.println("***********************************************************************");
        response.setCharacterEncoding("UTF-8");    //设置响应的字符集格式为UTF-8
        response.setContentType("text/html");  //设置响应正文的MIME类型
        PrintWriter out = response.getWriter();    //返回一个PrintWriter对象，Servlet使用它来输出字符串形式的正文数据
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>过年好</TITLE></HEAD>");
        out.println("  <BODY>");
        out.println("    <table border='0' align='center'>");
        out.println("            <tr><td bgcolor='skyblue'colspan=2>过年好</td></tr>");
        out.println("     </table>");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("***********************************************************************");
        System.out.println("do post");
        System.out.println("***********************************************************************");

        response.setCharacterEncoding("UTF-8");    //设置响应的字符集格式为UTF-8
        response.setContentType("text/html");  //设置响应正文的MIME类型
        PrintWriter out = response.getWriter();    //返回一个PrintWriter对象，Servlet使用它来输出字符串形式的正文数据
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>过年好</TITLE></HEAD>");
        out.println("  <BODY>");
        out.println("    <table border='0' align='center'>");
        out.println("            <tr><td bgcolor='skyblue'colspan=2>过年好</td></tr>");
        out.println("     </table>");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("***********************************************************************");
        System.out.println("service");
        System.out.println("***********************************************************************");
        super.service(req, resp);
    }

    @Override
    public void init() throws ServletException {
        System.out.println("***********************************************************************");
        System.out.println("Init");
        System.out.println("***********************************************************************");
        super.init();
    }

    @Override
    public void destroy() {
        System.out.println("***********************************************************************");
        System.out.println("destroy");
        System.out.println("***********************************************************************");
        super.destroy();
    }
}
