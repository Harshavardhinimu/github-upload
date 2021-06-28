
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author uniq
 */



@WebServlet(name="email1", urlPatterns={"/email1"})
public class email1 extends HttpServlet {
   
    
  
    
  static protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ResultSet rs2;
        PrintWriter out = response.getWriter();
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
  String mob="";
  String sql2="select * from register where mail='"+s2+"'";
  ResultSet rs5=st2.executeQuery(sql2);
  while(rs5.next())
  {
      mob=rs5.getString("mob");
      
  }

//CallSmscApi csapi=new CallSmscApi();
//csapi.sendsms(rendom_key,mob);

//--------------------------------------------------------------------------------------------------------------
//  23/03/2016 for temporary 
    boolean debug = true;            
Properties props = new Properties();
props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.auth", "true");
props.put("mail.debug", "true");
props.put("mail.smtp.port", "465");
props.put("mail.smtp.socketFactory.port", "465");
props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
props.put("mail.smtp.socketFactory.fallback", "false");

Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {

                @Override
protected PasswordAuthentication getPasswordAuthentication()
{
    return new PasswordAuthentication("mailapiserver@gmail.com","Corona1!@#");
}                                      
});
//String s="college2test@gmail.com";
String[] too=s2.split(",");
session.setDebug(debug);
Message msg = new MimeMessage(session);
InternetAddress addressFrom = new InternetAddress("mailapiserver@gmail.com");
msg.setFrom(addressFrom);
InternetAddress[] addressTo = new InternetAddress[too.length];
for (int i = 0; i < too.length; i++) {
addressTo[i] = new InternetAddress(too[i]);
}
msg.setRecipients(Message.RecipientType.TO, addressTo);

// Setting the Subject and Content Type
msg.setSubject("Your Securet Key from Admin");
String ma1=session2.getAttribute("usern").toString();
String msge="File Request From "+ma1+"The keyword of "+Orfilocation+" is "+filename+".The secret key of "+Orfilocation+" is "+s1+".";
msg.setContent(msge, "text/plain");
Transport.send(msg); 
 //23/03/2016 for temporary               
//---------------------------------------------------------------------------------------------------------------
            response.sendRedirect("secretkey.jsp");


        }
        catch(Exception e)
        {

            out.println(e);
        }
        finally {
            out.close();
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

}
