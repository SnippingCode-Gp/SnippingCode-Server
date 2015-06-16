/**
* Developed by Nasser
* Login Controller
*/
package com.User.Controller.UserOperation;

import com.User.Domain.UserDomain;
import com.DataType.StringType;
import com.User.ObjectRequest.LoginContain;
import com.User.Repository.UserRepository;
import com.CommonService.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
			List<UserDomain> usr = UserRepo.findByusername(username);
			if (usr.size() != 0) {
				UserDomain getusr = usr.get(0);

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

    MailService mm;
    ApplicationContext context;

    @RequestMapping(value = "/forgetPassword" , method = RequestMethod.POST)
    public ResponseEntity forgetPassword(@RequestBody String email){
        context = new ClassPathXmlApplicationContext("/spring/mail.xml");
        mm = (MailService) context.getBean("mailMail");

        List<UserDomain> user = UserRepo.findByemail(email);
        if(user.size() != 0 ){
            mm.sendMail("ahmednasser1993@gmail.com",
                    email, "resetPassword",
                    "your user name is " + user.get(0).getUsername() + "\n"
                    +"your password is " + user.get(0).getPassword());
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
