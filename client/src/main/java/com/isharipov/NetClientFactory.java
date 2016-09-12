package com.isharipov;

import static java.lang.ClassLoader.getSystemClassLoader;
import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * Created by Илья on 12.09.2016.
 */
public class NetClientFactory {
    private final String host;
    private final int port;

    public NetClientFactory(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        NetClientFactory netClientFactory = new NetClientFactory("localhost", 7500);
        Calculator client = netClientFactory.createClient(Calculator.class);
        Double result = client.calculate(5, 6);
        System.out.println(result);
    }

    public <T> T createClient(Class<T> interfaceClass) {
        return (T) newProxyInstance(getSystemClassLoader(), new Class[]{interfaceClass}, new ClientInvocationHandler(host, port));
    }
}
