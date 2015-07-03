/**
* Developed by Nasser
* Login Controller
*/
package com.User.Controller.UserOperation;

import com.User.Domain.User;
import com.DataType.StringType;
import com.User.ObjectRequest.LoginContain;
import com.User.Repository.UserRepository;
import com.CommonService.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class LoginController {

	@Autowired
	private UserRepository UserRepo;

	@Autowired
	private StringType date;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<StringType> login(@RequestBody LoginContain loginReqobject) {

		try {
			String username = loginReqobject.getUsername();
			String password = loginReqobject.getPassword();
			List<User> usr = UserRepo.findByusername(username);
			if (usr.size() != 0) {
				User getusr = usr.get(0);

				if (getusr.getPassword().equals(password)) {
					date.setString("exist");
					return new ResponseEntity<StringType>(date, HttpStatus.OK);
				}else{
                    date.setString("password wrong");
                    return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
                }
			}else{
                date.setString("user not Found");
                return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
            }
		} catch (Exception e) {
			date.setString("not exist");
			return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
		}
	}

    MailService mailService;
    ApplicationContext context;

    @RequestMapping(value = "/forgetPassword" , method = RequestMethod.POST)
    public ResponseEntity forgetPassword(@RequestBody String email){
        System.out.println("forget password " + email);
        context = new ClassPathXmlApplicationContext("/spring/mail.xml");
        mailService = (MailService) context.getBean("mailMail");

        List<User> user = UserRepo.findByemail(email);
        if(user.size() != 0 ){
            User usr = user.get(0);
            StringBuilder msg = new StringBuilder("Hello " + usr.getName());
            msg.append(" this is generated mail From CodeSnipping Website to know your password \n\n");
            msg.append("your email is " + email + "\n\n");
            msg.append("your password is " + usr.getPassword() + "\n\n");
            msg.append("your username is " + usr.getUsername() + "\n\n");
            mailService.sendMail("codesnipping2014@gmail.com", email, "forget passwordPassword", msg.toString());
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/eclipselogin", method = RequestMethod.POST,headers = "content-type=application/x-www-form-urlencoded")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<StringType> eclipseLogin(@ModelAttribute LoginContain loginReqobject) {

        try {
            String username = loginReqobject.getUsername();
            String password = loginReqobject.getPassword();
            List<User> usr = UserRepo.findByusername(username);
            if (usr.size() != 0) {
                User getusr = usr.get(0);

                if (getusr.getPassword().equals(password)) {
                    date.setString("exist");
                    return new ResponseEntity<StringType>(date, HttpStatus.OK);
                }else{
                    date.setString("password wrong");
                    return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
                }
            }else{
                date.setString("user not Found");
                return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            date.setString("not exist");
            return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
        }
    }
}
