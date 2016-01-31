package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Attribute extends Info<Attribute> {

    private U2 attributeNameIndex;

    private U4 attributeLength;

    private List<U1> attributes;

    public void write(DataOutputStream out) throws IOException {

    }

    @Override
    public void link(ConstantPool constantPool) {

    }

    public Attribute read(DataInputStream in) throws IOException, ClassNotFoundException {
        attributeNameIndex = new U2().read(in);
        attributeLength = new U4().read(in);
        int size = attributeLength.intValue();
        attributes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            attributes.add(new U1().read(in));
        }
        return this;
    }
}
