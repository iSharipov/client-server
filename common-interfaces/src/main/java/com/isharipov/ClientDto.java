package com.isharipov;

import java.io.Serializable;

/**
 * Created by Илья on 12.09.2016.
 */
public class ClientDto implements Serializable {

    private static final long serialVersionUID = 7072817687653240027L;

    private String methodName;
    private Object[] parameters;

    public ClientDto(String methodName, Object[] parameters) {
        this.methodName = methodName;
        this.parameters = parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }
}
