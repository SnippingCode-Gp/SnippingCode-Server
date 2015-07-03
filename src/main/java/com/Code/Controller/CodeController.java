package com.Code.Controller;

import com.Code.Domain.Code;
import com.Code.ObjectRequest.CodeObject;
import com.Code.ObjectRequest.CodeObjectRequest;
import com.Code.ObjectRequest.ValidationObject;
import com.Code.Repository.CodeRepository;
import com.Code.Service.CodeService;
import com.CommonService.ParseFileToString;
import com.User.Service.UserService;
import com.DataType.StringType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sngv
 */

@RestController
@RequestMapping("/Code")
public class CodeController {

	@Autowired
	private ParseFileToString parsing;

	@Autowired
	private StringType date;

	@Autowired
	private CodeRepository codeRepository;

	@Autowired
	private CodeService codeService;

	@Autowired
    UserService userService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Long> UploadFile(@RequestBody CodeObject uploadReqObj) {
		Code code = codeService.CreateCodeEntity(uploadReqObj);
		if (code == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		Code tmp = null;
		try {
			List<Code> all = codeRepository.findByusername(uploadReqObj
					.getUsername());
			for (int i = 0; i < all.size(); i++) {
				if (all.get(i).equals(uploadReqObj.getName())) {
					return new ResponseEntity(HttpStatus.CONFLICT);
				}
			}
			try {
				codeRepository.save(code);
			} catch (Exception e) {
				System.out.println("Exception is \n " + e.toString());
				return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
			}
			return new ResponseEntity(code.getId(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@RequestMapping(value = "/update/{oldName}", method = RequestMethod.POST)
	public ResponseEntity<StringType> updateCode(@PathVariable String oldName,
			@RequestBody CodeObject uploadReqObj) {
		List<Code> codes = codeRepository.findByname(oldName);
		if (codes.size() == 0)
			return new ResponseEntity<StringType>(HttpStatus.NOT_FOUND);
		Code code = codes.get(0);
		Code codeDomain = codeService.updateCode(code, uploadReqObj);

		if (codeDomain == null)
			return new ResponseEntity<StringType>(new StringType("Not Found"),
					HttpStatus.NOT_FOUND);

		try {
			codeRepository.save(codeDomain);
			date.setString("Done");
			return new ResponseEntity<StringType>(date, HttpStatus.OK);
		} catch (Exception e) {
			date.setString("make sure of user name");
			return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/view/{number}", method = RequestMethod.POST)
	@org.springframework.transaction.annotation.Transactional
	public ResponseEntity<List<CodeObjectRequest>> getFilesCodes(
			@PathVariable String number,
			@RequestBody ValidationObject validationObject) {
		System.out.println(" number of :: " + number);
		if (!userService.checkUserExist(validationObject)) {
			return new ResponseEntity<List<CodeObjectRequest>>(
					HttpStatus.NOT_FOUND);
		}
		List<Code> userCodes = null;
		try {
			int num = Integer.parseInt(number);
			Pageable range = new PageRequest(num, 5);
			userCodes = codeRepository.findByusername(
					validationObject.getUsername(), range);
			if (userCodes.size() == 0) {
				return new ResponseEntity<List<CodeObjectRequest>>(
						HttpStatus.NOT_FOUND);
			} else {
				List<CodeObjectRequest> codeObjectRequests = codeService
						.changeFromDomainToRequestObj(userCodes);
				return new ResponseEntity<List<CodeObjectRequest>>(
						codeObjectRequests, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CodeObjectRequest>>(
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@RequestMapping(value = "/getFile/{name}", method = RequestMethod.POST)
	@org.springframework.transaction.annotation.Transactional
	public ResponseEntity<CodeObjectRequest> getCode(@PathVariable String name,
			@RequestBody ValidationObject validationObject) {

		if (!userService.checkUserExist(validationObject)) {
			return new ResponseEntity<CodeObjectRequest>(HttpStatus.NOT_FOUND);
		}
		try {
			List<Code> code = codeRepository.findByname(name);
			System.out.println("name of code " + name + " size of codes is "
					+ code.size());
			if (code.size() == 0)
				return new ResponseEntity<CodeObjectRequest>(
						HttpStatus.NOT_FOUND);

			CodeObjectRequest codeObjectRequests = codeService
					.changeToCodeObject(code.get(0));
			return new ResponseEntity<CodeObjectRequest>(codeObjectRequests,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<CodeObjectRequest>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete/{name}", method = RequestMethod.POST)
	public ResponseEntity<StringType> deleteObject(
			@RequestBody ValidationObject deleteObjReq,
			@PathVariable("name") String name) {

		if (!userService.checkUserExist(deleteObjReq.getUsername(),
				deleteObjReq.getPassword())) {
			date.setString("user invalid");
			return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
		}

		if (name.length() <= 2) {
			date.setString("naming of file is invalid");
			return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
		}

		name = name.substring(1, name.length() - 1);
		List<Code> code = codeRepository.findByname(name);
		if (code.size() == 0) {
			date.setString("code not found");
			return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
		}
		System.out.println("creid :: "
				+ userService.checkUserExist(deleteObjReq.getUsername(),
						deleteObjReq.getPassword()));
		try {
			codeRepository.delete(code.get(0));
			date.setString("Success");
			return new ResponseEntity<StringType>(date, HttpStatus.OK);
		} catch (Exception e) {
			date.setString("server error");
			return new ResponseEntity<StringType>(date, HttpStatus.NOT_FOUND);
		}
	}
}
