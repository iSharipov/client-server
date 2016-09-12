package com.isharipov;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by Илья on 12.09.2016.
 */
public class ClientInvocationHandler implements InvocationHandler {

    private final String host;
    private final int port;

    public ClientInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();

        ClientDto clientDto = new ClientDto(name, args);
        ObjectOutput out = null;
        byte[] bytes;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            out = new ObjectOutputStream(bos);
            out.writeObject(clientDto);
            bytes = bos.toByteArray();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        Socket socket = new Socket("localhost", 7500);
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        if (bytes.length > 0) {
            dataOutputStream.write(bytes, 0, bytes.length);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return (double) bufferedReader.read();
    }
}
