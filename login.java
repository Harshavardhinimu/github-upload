/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Murali1
 */
public class login extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         HttpSession session = request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
           String mail = request.getParameter("uname");
           String pass = request.getParameter("pass");
            Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudsecure","root","admin");
            Statement st=conn.createStatement();
            Statement st1=conn.createStatement();
            Statement st2=conn.createStatement();
          String sql=("select mail,password,counter from register");
          ResultSet rs=st.executeQuery(sql);
int f=0;


          
          while(rs.next())
          {
              String m=rs.getString("mail");
               String p=rs.getString("password");
               String c=rs.getString("counter");
               
         if( mail.equals(m) && pass.equals(p) && c.equals("1") )
         {
f=1;
             session.setAttribute("usern", mail);


        RequestDispatcher rd=request.getRequestDispatcher("upanddown.jsp");
                    rd.forward(request, response);
                    break;

        }
            }
          if(f==0)
          {
              response.sendRedirect("clientlogin.jsp?mgs=failed");
          }
        }
         catch(Exception e)
         {
          out.println(e);
         }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void regi() 
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
