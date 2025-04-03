
package com.mgsoft.netconfsimulator.examples.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/**
 *
 * @author MG-SOFT <support@mg-soft.com>
 * Copyright (C) 2018-2025 MG-SOFT Corporation. All rights reserved.
 */
public class ObjectStorage {
    
    public static Object loadData(Path filePath) {
        try {
            String content = new String(Files.readAllBytes( filePath));
            return fromString(content);
        } catch (IOException | ClassNotFoundException ex) {
        }
        return null;
    }
    
    public static void saveData(Serializable object, Path filePath) {
        try {
            String content = toString(object);
            Files.write(filePath, content.getBytes());
        } catch (IOException ex) {
        }
    }
    
    /** Read the object from Base64 string. */
   private static Object fromString( String s ) throws IOException ,
                                                       ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream( 
                                        new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
   }

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
    }
    
}
