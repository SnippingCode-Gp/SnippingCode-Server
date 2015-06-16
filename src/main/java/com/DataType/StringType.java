package com.DataType;

/**
 * Developed by Nasser
 * Login Controller
 */
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringType {
	private String string;

	public StringType() {

	}

    public StringType(String newString){
        string = newString;
    }

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
}
