package com.proton.bystone.exception;

/**
 * Created by MasterFan on 2016/7/15.
 */
public class BuildException extends RuntimeException {

    public BuildException() {
        super("构建参数错误");
    }

    public BuildException(String msg) {
        super(msg);
    }

}
