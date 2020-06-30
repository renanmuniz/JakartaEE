import com.sun.rowset.CachedRowSetImpl;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/listar3")
public class ServletExer5 extends HttpServlet {

    @Resource(name="jdbc/sistema")
    private DataSource ds;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("<html><body>");
        out.println("<h1> Listagem de clientes com Escalabilidade</h1>");
        try {
            CachedRowSet cache = getDados();
            while(cache.next()) {
                out.println("<p>"+ cache.getString("nome") + cache.getString("email") +"</p>");
            }

            cache.close();

        } catch (Exception e) {
            out.println("<p>"+e.getMessage()+"</p");
        }
        out.println("</body></html>");
    }

    private CachedRowSet getDados() throws Exception{
        try {
            Connection con = ds.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from cliente");
            CachedRowSet cache = new CachedRowSetImpl();
            cache.populate(rs);
            rs.close();
            con.close();
            return cache;

        } catch (SQLException e) {
            if (e.getMessage().contains("Cannot get a connection")){
                throw new Exception("Sistema Sobrecarregado");
            } else {
                e.printStackTrace();
                throw new Exception("Erro não tratado");
            }
        }
    }
}
