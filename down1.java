/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */

import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
 
import javax.servlet.ServletContext;

import java.util.*;
import javax.servlet.http.HttpSession;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.servlet.ServletException;



/**
 *
 * @author uniq
 */
@WebServlet(name="down1", urlPatterns={"/down1"})
public class down1 extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);

   int counter=0;
String s1=null;
String s2=null;
String s3=null;
//String ofile=null;



        String key=request.getParameter("e");
        String file_owner="";
        String urname=session.getAttribute("usern").toString();
     

        try {
            // Connection conn = DriverManager.getConnection("jdbc:mysql://mysql91468-CloudSecure.j.layershift.co.uk/cloudsecure","root","admin");
            String url="jdbc:mysql://localhost:3306/cloudsecure";
//            String url="jdbc:mysql://mysql92594-SCloud.j.layershift.co.uk/cloudsecure";
ServletContext context = getServletContext();

		String dirName =context.getRealPath("/DFile/");
       Connection c=null;
        Class.forName("com.mysql.jdbc.Driver");
         c=DriverManager.getConnection(url, "root", "admin");

      Statement st=c.createStatement();

       
String fln=session.getAttribute("dfln").toString();
         ResultSet rs=st.executeQuery("select * from upload where Orfilocation='"+fln+"' ");
String filename=null;

          boolean f=false;
         while(rs.next())
             {
                 file_owner=rs.getString("name");
             filename=rs.getString("Orfilocation");
             String uu=rs.getString("secretkey");
           int conr=rs.getInt("counter");
           s1=rs.getString("split1");
           s2=rs.getString("split2");
           s3=rs.getString("split3");
        //   ofile=rs.getString("Orfilocation");
           

           if(key.equals(uu))
                          {

		try {
          
                     String yes="YES";
        String noo="NO";
                    Statement st1=c.createStatement();
                    Statement st2=c.createStatement();
                    Statement st3=c.createStatement();
                    ResultSet rss=st1.executeQuery("select * from notify where owner='"+file_owner+"' and suser='"+urname+"' and filenm='"+fln+"'");
                  boolean flg=false;
                    while(rss.next())
                    {
                        flg=true;
                        break;
                    }
                    if(flg)
                    {
                         st2.executeUpdate("update notify set fdownload='"+yes+"' where owner='"+file_owner+"' and filenm='"+fln+"' and suser='"+urname+"'");
                    }
                    else
                    {
                         st3.executeUpdate("insert notify(owner,suser,filenm,fview,fedit,fdownload,atime) values('"+file_owner+"','"+urname+"','"+filename+"','"+noo+"','"+noo+"','"+yes+"',now())");
                    }
                    
                              
   //----------------------------Decrypt-----------------------------
                    
                    int mode = Cipher.DECRYPT_MODE;
                    
                 String dirNameE1 =context.getRealPath("/splitE1/");
String dirNameE2 =context.getRealPath("/splitE2/");
String dirNameE3 =context.getRealPath("/splitE3/");  

String dirNameDF =context.getRealPath("/DFile/");  


 String dirNameD1 =context.getRealPath("/splitD1/");
String dirNameD2 =context.getRealPath("/splitD2/");
String dirNameD3 =context.getRealPath("/splitD3/");   

        String keye="Murali123";
        
	FileInputStream fis1 = new FileInputStream(dirNameE1+"/"+s1);
        FileInputStream fis2 = new FileInputStream(dirNameE2+"/"+s2);
        FileInputStream fis3 = new FileInputStream(dirNameE3+"/"+s3);
        
	FileOutputStream fos1 = new FileOutputStream(dirNameD1+"/"+s1);            
        FileOutputStream fos2 = new FileOutputStream(dirNameD2+"/"+s2);            
        FileOutputStream fos3 = new FileOutputStream(dirNameD3+"/"+s3);                        
                    
                    DESKeySpec dks = new DESKeySpec(keye.getBytes());
	SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
	SecretKey desKey = skf.generateSecret(dks);
	Cipher cipher = Cipher.getInstance("DES"); 
                    
              if (mode == Cipher.DECRYPT_MODE) {
                  
                  //-----FILE-1-----------
		cipher.init(Cipher.DECRYPT_MODE, desKey);
		CipherOutputStream cos = new CipherOutputStream(fos1, cipher);
		//doCopy(is, cos);
                byte[] bytes = new byte[64];
	int numBytes;
	while ((numBytes = fis1.read(bytes)) != -1) {
		cos.write(bytes, 0, numBytes);
	}
	cos.flush();
	cos.close();
	fis1.close();
        
             //-----FILE-2-----------
            
		cipher.init(Cipher.DECRYPT_MODE, desKey);
		CipherOutputStream cos2 = new CipherOutputStream(fos2, cipher);
		//doCopy(is, cos);
                byte[] bytes2 = new byte[64];
	int numBytes2;
	while ((numBytes2 = fis2.read(bytes2)) != -1) {
		cos2.write(bytes2, 0, numBytes2);
	}
	cos2.flush();
	cos2.close();
	fis2.close();
                
	      
              
               //-----FILE-3-----------
            
		cipher.init(Cipher.DECRYPT_MODE, desKey);
		CipherOutputStream cos3 = new CipherOutputStream(fos3, cipher);
		//doCopy(is, cos);
                byte[] bytes3 = new byte[64];
	int numBytes3;
	while ((numBytes3 = fis3.read(bytes3)) != -1) {
		cos3.write(bytes3, 0, numBytes3);
	}
	cos3.flush();
	cos3.close();
	fis3.close();
                
              }     
                    
             
                    
    //----------------------------END Decrypt-----------------------------                
              
    //----------------------------File Join-------------------------------
              
              FileInputStream  ff1=new FileInputStream (dirNameD1+"/"+s1);
	FileInputStream  ff2=new FileInputStream (dirNameD2+"/"+s2);
	FileInputStream  ff3=new FileInputStream (dirNameD3+"/"+s3);
        
        
        Vector<InputStream> inputStreams = new Vector<InputStream>();
	inputStreams.add(ff1);
	inputStreams.add(ff2);
	inputStreams.add(ff3);
		
	FileWriter fileWriter = new FileWriter(dirNameDF+"/"+filename);
	PrintWriter out1 = new PrintWriter(fileWriter);
	Enumeration<InputStream> enu = inputStreams.elements();
	SequenceInputStream sis = new SequenceInputStream(enu);
			
	int oneByte;
	while ((oneByte = sis.read()) != -1) {
	//	out.println(oneByte);
		//als.add(oneByte)));
		out1.write(oneByte);
		System.out.write(oneByte);
	}
	System.out.flush();
	
	
	out1.flush();
	out1.close();
	fileWriter.close();

        
        
              
    //----------------------------END File Join-------------------------------          
                    
                    
                    
                    
                    
                    


		//String filename1="";
//String filepath=rs.getString("resumename");
                    String filepath=dirName;
response.setContentType("APPLICATION/OCTET-STREAM");
response.setHeader("Content-Disposition", "attachment;name=\""+filename+"\"");
FileInputStream fileInputStream=new FileInputStream(dirName+"/"+filename);

int i;
while((i=fileInputStream.read())!=-1)
{
    System.out.println(i);
out.write(i);
}
fileInputStream.close();
out.close();

		} catch (Exception e) {
			System.out.println(e);
		}




  }
 else if(conr>2)
{
     String str=session.getAttribute("usern").toString();
     out.println("user  "+str);
     ResultSet rslt=st.executeQuery("select * from attacker where uname='"+str+"'");
     int flag=1;
while(rslt.next())
{
    flag=0;
    int ab=rslt.getInt("counter");



    if(ab>2)
         {
          st.executeUpdate("update register set counter='0' where mail='"+str+"'");
            st.executeUpdate("update upload set counter='0'");
          response.sendRedirect("index.jsp?blok=blk");
             
         }
 else
         {
             ab=ab+1;
                st.executeUpdate("update attacker set counter='"+ab+"'");
                 st.executeUpdate("update upload set counter='0'");
System.out.println("Sorry No More Access2");
 response.sendRedirect("index.jsp?blok=alrt");
 }
}
if(flag==1)
{
    st.executeUpdate("insert attacker(uname,counter) values('"+str+"','1')");
    st.executeUpdate("update upload set counter='0'");
System.out.println("Sorry No More Access1");
 response.sendRedirect("index.jsp?blok=alrt");

    }
      out.println("TEST");
  
}
 else
                   
{
    
    conr=conr+1;
    st.executeUpdate("update upload set counter='"+conr+"' where Orfilocation='"+fln+"'");
    response.sendRedirect("secretkey.jsp?blok=wrng");
               }
              

             
          
                
            }
        }
         catch(Exception e)
                 {
                 }
      //  out.println("your file succefully downloded");
//response.sendRedirect("download.jsp");
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
