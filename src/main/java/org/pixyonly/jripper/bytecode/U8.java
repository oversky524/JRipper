package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by pixyonly on 16/1/28.
 */
public class U8 extends Atom<U8> {

    private byte b1, b2, b3, b4, b5, b6, b7, b8;

    public byte getB1() {
        return b1;
    }

    public U8 setB1(byte b1) {
        this.b1 = b1;
        return this;
    }

    public byte getB2() {
        return b2;
    }

    public U8 setB2(byte b2) {
        this.b2 = b2;
        return this;
    }

    public byte getB3() {
        return b3;
    }

    public U8 setB3(byte b3) {
        this.b3 = b3;
        return this;
    }

    public byte getB4() {
        return b4;
    }

    public U8 setB4(byte b4) {
        this.b4 = b4;
        return this;
    }

    public byte getB5() {
        return b5;
    }

    public U8 setB5(byte b5) {
        this.b5 = b5;
        return this;
    }

    public byte getB6() {
        return b6;
    }

    public U8 setB6(byte b6) {
        this.b6 = b6;
        return this;
    }

    public byte getB7() {
        return b7;
    }

    public U8 setB7(byte b7) {
        this.b7 = b7;
        return this;
    }

    public byte getB8() {
        return b8;
    }

    public U8 setB8(byte b8) {
        this.b8 = b8;
        return this;
    }

    public long longValue() {
        return ((long) ((b1 << 24) + (b2 << 16) + (b3 << 8) + b4) << 32) + (b5 << 24) + (b6 << 16) + (b7 << 8) + b8;
    }

    public U8 read(DataInputStream in) throws IOException, ClassNotFoundException {
        b1 = in.readByte();
        b2 = in.readByte();
        b3 = in.readByte();
        b4 = in.readByte();
        b5 = in.readByte();
        b6 = in.readByte();
        b7 = in.readByte();
        b8 = in.readByte();
        return this;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeByte(b1);
        out.writeByte(b2);
        out.writeByte(b3);
        out.writeByte(b4);
        out.writeByte(b5);
        out.writeByte(b6);
        out.writeByte(b7);
        out.writeByte(b8);
    }
}
