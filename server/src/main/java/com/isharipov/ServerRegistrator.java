package com.isharipov;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Илья on 12.09.2016.
 */
public class ServerRegistrator {
    public static void listen(String host, int port, Object impl) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ServerSocket serverSocket = new ServerSocket(7500);
        while (true) {
            try (Socket client = serverSocket.accept()) {

                ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                ClientDto o = (ClientDto) objectInputStream.readObject();
                System.out.println(o.getMethodName());
                Class[] params = new Class[o.getParameters().length];
                for (int i = 0; i < o.getParameters().length; i++) {
                    params[i] = o.getParameters()[i].getClass();
                }
                System.out.println(Arrays.toString(o.getParameters()));
                Double invoke = (Double) impl.getClass().getMethod(o.getMethodName(), params).invoke(impl, o.getParameters());
                OutputStream outputStream = client.getOutputStream();
                outputStream.write(invoke.byteValue());
                outputStream.flush();
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ServerRegistrator.listen("localhost", 7500, new CalculatorImpl());
    }
}