package br.com.techfullit.mycluby.common.utils;

import br.com.techfullit.mycluby.common.constants.Constants;

public class StringUtils {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public static String pad(String text, int position, int number) {
	if(text == null) text = "";
	String num = "";
	int iterate = number - text.length();
	for (int i = 0; i < iterate; i++) {
	    num += " ";
	}
	if(position == LEFT){
	    text = num + text;
	}else{
	    text = text + num;
	}
	return text;
    }

    public static boolean isBlank(String value) {
	try {
	    if (value == null) {
		return true;
	    } else if (value.equals(Constants.EMPTY)) {
		return true;
	    } else {
		return false;
	    }
	} catch (Exception e) {
	    return true;
	}
    }

    public static boolean notIsBlank(String value) {
	try {
	    if (value == null) {
		return false;
	    } else if (value.equals(Constants.EMPTY)) {
		return false;
	    } else {
		return true;
	    }
	} catch (Exception e) {
	    return false;
	}
    }

}
