package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Attributes extends Info<Attributes> {

    private U2 attributeCount;

    private List<Attribute> attributes;

    @Override
    public Attributes read(DataInputStream in) throws IOException, ClassNotFoundException {
        attributeCount = new U2().read(in);
        int size = attributeCount.intValue();
        attributes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            attributes.add(new Attribute().read(in));
        }
        return this;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {

    }

    @Override
    public void link(ConstantPool constantPool) {

    }
}
