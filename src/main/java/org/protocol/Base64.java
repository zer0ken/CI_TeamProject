package org.protocol;

import java.io.*;

public class Base64 implements Serializable {
    public static String encode(Serializable object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(object);
            return java.util.Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object decode(String base64) {
        ByteArrayInputStream bis = new ByteArrayInputStream(java.util.Base64.getDecoder().decode(base64));
        try {
            ObjectInput in = new ObjectInputStream(bis);
            return in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
