/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
public class Mail21 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
       
      
            ResultSet rs2;
        
           String s1=null;
           String s2=null;
        try {
            Random rd=new Random();
            int rd1=rd.nextInt(8);
            int rd2=rd.nextInt(8);
            int rd3=rd.nextInt(8);
            int rd4=rd.nextInt(8);
             String rendom_key=""+rd1+rd2+rd3+rd4;
             rendom_key=rendom_key.trim();
             
              HttpSession session2=request.getSession(true);
              String dflm=request.getParameter("dfln");
session2.setAttribute("dfln", dflm);
             Class.forName("com.mysql.jdbc.Driver");


            // Connection conn = DriverManager.getConnection("jdbc:mysql://ec2-50-19-213-178.compute-1.amazonaws.com:3306/group","group","group");

Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudsecure","root","admin");
//  Connection conn = DriverManager.getConnection("jdbc:mysql://mysql92594-SCloud.j.layershift.co.uk/cloudsecure","root","AuaQhD3I3l");
            Statement st=conn.createStatement();
            Statement st1=conn.createStatement();
            Statement st2=conn.createStatement();
            //Random Key Creation
 
          
            String filename="";
            String Orfilocation="";
            String sql=("select * from upload where Orfilocation='"+dflm+"'  ");
          ResultSet rs=st.executeQuery(sql);
           while(rs.next())
           {

                 s2=rs.getString("name");
                 filename=rs.getString("filename");
                 Orfilocation=rs.getString("Orfilocation");
              //   s1=rs.getString("secretkey");
            }
           
           s1=rendom_key;
  st1.executeUpdate("update upload set secretkey='"+rendom_key+"' where Orfilocation='"+dflm+"' and name='"+s2+"'");
  System.out.println(rendom_key);
            //String message = request.getParameter("k");
            //String s = request.getParameter("m");

            boolean debug = true;

            Properties props = new Properties();

            props.put("mail.smtp.host", "smtp.gmail.com");

            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("mailfromcloud4", "Corona1!@#");
                }
            });
           //String[] too=s2.split(",");
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("mailfromcloud4@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(s2));
            msg.setSubject("Your Securet Key from Admin");
            String msge="The secret key of "+Orfilocation+" is "+s1+".";
            msg.setContent(msge, "text/plain");
            //msg.setContent(message, "text/plain");
            //msg.setText(message);
//            InternetAddress[] addressTo = new InternetAddress[too.length];
//            for (int i = 0; i < too.length; i++) {
//              addressTo[i] = new InternetAddress(too[i]);
//            }
//            msg.setRecipients(Message.RecipientType.TO, addressTo);
            
            Transport.send(msg);

            response.sendRedirect("secretkey.jsp");

        } catch (Exception e) {
            out.println(e);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
