<!DOCTYPE html PUBLIC >
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Listagem 1</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1>Lista 1</h1>
    <table class="table table-bordered table-condensed table-hover">
        <thead >
        <tr>
            <th class="info">ID</th>
            <th class="info">NOME</th>
            <th class="info">E-MAIL</th>
        </tr>
        </thead>
        <tbody>
        <%
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/sistema");
            Connection conn = ds.getConnection(); // reservando a conexão!
            ResultSet res = conn.createStatement().executeQuery("select * from cliente");
            while (res.next()) {
        %>
        <tr>
            <td><%=res.getString("id")%></td>
            <td><%=res.getString("nome")%></td>
            <td><%=res.getString("email")%></td>
        </tr>
        <%
            }
            res.close();
            conn.close(); // devolvendo a conexão.
        %>
        </tbody>
    </table>
</div>
</body>
</html>