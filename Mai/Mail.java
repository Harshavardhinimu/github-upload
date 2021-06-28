package Mai;



import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class Mail {
public static void sendMail(String key,String tomail) 
{
    System.out.println("Mail");
		Properties props=new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port","465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session=Session.getDefaultInstance(props, new Authenticator()
		{
			protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
				return new javax.mail.PasswordAuthentication("mailapiserver@gmail.com", "Corona1!@#");
			}
		});
		try
                {
			MimeMessage msg=new MimeMessage(session);
			msg.setFrom(new InternetAddress("mailapiserver@gmail.com"));
			msg.addRecipients(Message.RecipientType.TO, tomail);

			msg.setSubject("Alert");
                       // String pass=password;
			msg.setText(key);
			Transport.send(msg);
			System.out.println("Mail sent successfully");

		}
		catch(Exception e)
		{

		}
	}


}
