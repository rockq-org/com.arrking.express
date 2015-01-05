package com.arrking.android.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hain on 26/12/2014.
 */
public class ResponseException extends Exception {

    public List<String>  getErrors(){
        List<String> errs = new ArrayList<String>();
        errs.add(getMessage());
        return errs;
    }
}
