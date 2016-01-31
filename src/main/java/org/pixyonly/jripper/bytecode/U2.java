package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by pixyonly on 16/1/28.
 */
public class U2 extends Atom<U2> {

    private byte b1, b2;

    public U2(){}

    public U2(byte b1, byte b2){
        this.b1 = b1;
        this.b2 = b2;
    }

    public byte getB1() {
        return b1;
    }

    public U2 setB1(byte b1) {
        this.b1 = b1;
        return this;
    }

    public byte getB2() {
        return b2;
    }

    public int intValue(){
        return (b1 << 8) + b2;
    }

    public U2 setB2(byte b2) {
        this.b2 = b2;
        return this;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeByte(b1);
        out.writeByte(b2);
    }

    public U2 read(DataInputStream in) throws IOException, ClassNotFoundException {
        b1 = in.readByte();
        b2 = in.readByte();
        return this;
    }
}
