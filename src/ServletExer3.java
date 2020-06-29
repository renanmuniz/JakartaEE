import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/listar1")
public class ServletExer3 extends HttpServlet {

    @Resource(name="jdbc/sistema")
    private DataSource ds;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection con = null;

        try {
            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            out.println("<html><body>");
            out.println("<h1> Listagem de clientes </h1>");
            con = ds.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from cliente");
            while(rs.next()) {
                out.println("<p>"+ rs.getString("nome") + rs.getString("email") +"</p>");
            }
            out.println("</body></html>");
            rs.close();

        } catch (Exception e) {
            if(con!=null) {
                try {
                    con.close();
                } catch(Exception e2) {
                    e2.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }
}
