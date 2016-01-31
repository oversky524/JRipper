package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by pixyonly on 16/1/28.
 */
public class U4 extends Atom<U4> {

    private byte b1, b2, b3, b4;

    public byte getB1() {
        return b1;
    }

    public U4 setB1(byte b1) {
        this.b1 = b1;
        return this;
    }

    public byte getB2() {
        return b2;
    }

    public U4 setB2(byte b2) {
        this.b2 = b2;
        return this;
    }

    public byte getB3() {
        return b3;
    }

    public U4 setB3(byte b3) {
        this.b3 = b3;
        return this;
    }

    public byte getB4() {
        return b4;
    }

    public U4 setB4(byte b4) {
        this.b4 = b4;
        return this;
    }

    public int intValue() {
        return (b1 << 24) + (b2 << 16) + (b3 << 8) + b4;
    }

    public U4 read(DataInputStream in) throws IOException, ClassNotFoundException {
        b1 = in.readByte();
        b2 = in.readByte();
        b3 = in.readByte();
        b4 = in.readByte();
        return this;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeByte(b1);
        out.writeByte(b2);
        out.writeByte(b3);
        out.writeByte(b4);
    }
}
