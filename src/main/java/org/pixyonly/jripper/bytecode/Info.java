package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by pixyonly on 16/1/28.
 */
public abstract class Info<K extends Segment> implements Segment {

    @Override
    public abstract K read(DataInputStream in) throws IOException, ClassNotFoundException;

    @Override
    public abstract void write(DataOutputStream out) throws IOException;

    public abstract void link(ConstantPool constantPool);
}
