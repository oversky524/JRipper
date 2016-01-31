package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by pixyonly on 16/1/28.
 */
public interface Segment<T> {

    T read(DataInputStream in) throws IOException, ClassNotFoundException;

    void write(DataOutputStream out) throws IOException;
}
