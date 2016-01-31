package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Method extends Info<Method> {

    public static final int ACC_PUBLIC = 0x0001;

    public static final int ACC_PRIVATE = 0x0002;

    public static final int ACC_PROTECTED = 0x0004;

    public static final int ACC_STATIC = 0x0008;

    public static final int ACC_FINAL = 0x0010;

    public static final int ACC_SYNCHRONIZED = 0x0020;

    public static final int ACC_BRIDGE = 0x0040;

    public static final int ACC_VARARGS = 0x0080;

    public static final int ACC_NATIVE = 0x0100;

    public static final int ACC_ABSTRACT = 0x0400;

    public static final int ACC_STRICTFP = 0x0800;

    public static final int ACC_SYNTHETIC = 0x1000;

    private U2 accessFlag;

    private U2 nameIndex;

    private U2 descriptorIndex;

    private U2 attributeCount;

    private String name;

    private String descriptor;

    private List<Attribute> attributes;

    public U2 getAccessFlag() {
        return accessFlag;
    }

    public U2 getNameIndex() {
        return nameIndex;
    }

    public U2 getDescriptorIndex() {
        return descriptorIndex;
    }

    public U2 getAttributeCount() {
        return attributeCount;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Method read(DataInputStream in) throws IOException, ClassNotFoundException {
        accessFlag = new U2().read(in);
        nameIndex = new U2().read(in);
        descriptorIndex = new U2().read(in);
        attributeCount = new U2().read(in);
        int size = attributeCount.intValue();
        attributes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            attributes.add(new Attribute().read(in));
        }
        return this;
    }

    public void write(DataOutputStream out) throws IOException {

    }

    public String resolveAccessFlag() {
        int ops = accessFlag.intValue();
        StringBuilder sb = new StringBuilder();
        if ((ops & ACC_PUBLIC) == ACC_PUBLIC) {
            sb.append("public ");
        } else if ((ops & ACC_PRIVATE) == ACC_PRIVATE) {
            sb.append("private ");
        } else if ((ops & ACC_PROTECTED) == ACC_PROTECTED) {
            sb.append("protected ");
        }
        //no more verify
        if ((ops & ACC_STATIC) == ACC_STATIC)
            sb.append("static ");
        if ((ops & ACC_FINAL) == ACC_FINAL)
            sb.append("final ");
        if ((ops & ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED)
            sb.append("synchronized ");
        if ((ops & ACC_ABSTRACT) == ACC_ABSTRACT)
            sb.append("abstract ");
        if ((ops & ACC_STRICTFP) == ACC_STRICTFP)
            sb.append("strictfp ");
        if ((ops & ACC_NATIVE) == ACC_NATIVE)
            sb.append("native ");
        return sb.toString();
    }

    @Override
    public void link(ConstantPool constantPool) {
        ConstantItem descriptorItem = constantPool.getItems().get(descriptorIndex.intValue() - 1);
        if (descriptorItem.value() == null) {
            descriptorItem.link(constantPool);
        }
        ConstantItem nameItem = constantPool.getItems().get(nameIndex.intValue() - 1);
        if (nameItem.value() == null) {
            nameItem.link(constantPool);
        }
        name = nameItem.value();
        descriptor = descriptorItem.value();
    }

    @Override
    public String toString() {
        return resolveAccessFlag() + name + " " + descriptor;
    }
}
