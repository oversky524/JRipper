package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class ConstantPool extends Info<ConstantPool> {

    public final static byte CONSTANT_UTF8_TAG = 1;

    public final static byte CONSTANT_INTEGER_TAG = 3;

    public final static byte CONSTANT_FLOAT_TAG = 4;

    public final static byte CONSTANT_LONG_TAG = 5;

    public final static byte CONSTANT_DOUBLE_TAG = 6;

    public final static byte CONSTANT_CLASS_TAG = 7;

    public final static byte CONSTANT_STRING_TAG = 8;

    public final static byte CONSTANT_FIELDREF_TAG = 9;

    public final static byte CONSTANT_METHODREF_TAG = 10;

    public final static byte CONSTANT_INTERFACEMETHODREF_TAG = 11;

    public final static byte CONSTANT_NAMEANDTYPE_TAG = 12;

    public final static byte CONSTANT_METHODHANDLE_TAG = 15;

    public final static byte CONSTANT_METHODTYPE_TAG = 16;

    public final static byte CONSTANT_INVOKEDYNAMIC_TAG = 18;

    private U2 poolSize;

    private List<ConstantItem> items;

    public ConstantPool() {
    }

    public U2 getPoolSize() {
        return poolSize;
    }

    public List<ConstantItem> getItems() {
        return items;
    }

    private void readItem(U1 tag, DataInputStream in, int index) throws IOException, ClassNotFoundException {
        ConstantItem item = ConstantItem.parseConstantItem(tag, in).setIndex(index);
        items.add(item);
    }

    public ConstantPool read(DataInputStream in) throws IOException, ClassNotFoundException {
        poolSize = new U2(in.readByte(), in.readByte());
        int size = poolSize.intValue() - 1;
        items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            readItem(new U1(in.readByte()), in, i + 1);
        }
        return this;
    }

    public void write(DataOutputStream out) throws IOException {

    }

    @Override
    public void link(ConstantPool constantPool) {
        for (ConstantItem item : items)
            item.link(this);
    }
}
