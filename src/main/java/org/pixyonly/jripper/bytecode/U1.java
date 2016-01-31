package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by pixyonly on 16/1/28.
 */
public class U1 extends Atom<U1> {

    private byte b;

    public U1() {
    }

    public U1(byte b) {
        this.b = b;
    }

    public U1(int v) {
        this.b = (byte) v;
    }

    public byte getB() {
        return b;
    }

    public U1 setB(byte b) {
        this.b = b;
        return this;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeByte(b);
    }

    public U1 read(DataInputStream in) throws IOException, ClassNotFoundException {
        b = in.readByte();
        return this;
    }

}
