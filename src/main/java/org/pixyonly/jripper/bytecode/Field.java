package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Field extends Info<Field> {

    public static final int ACC_PUBLIC = 0x0001;

    public static final int ACC_PRIVATE = 0x0002;

    public static final int ACC_PROTECTED = 0x0004;

    public static final int ACC_STATIC = 0x0008;

    public static final int ACC_FINAL = 0x0010;

    public static final int ACC_VOLATILE = 0x0040;

    public static final int ACC_TRANSIENT = 0x0080;

    public static final int ACC_SYNTHETIC = 0x1000;

    public static final int ACC_ENUM = 0x4000;

    private U2 accessFlag;

    private U2 nameIndex;

    private U2 descriptorIndex;

    private U2 attributeCount;

    private String name;

    private String descriptor;

    private List<Attribute> attributes;

    private static final String[] FOCUS_ATTACHMENT = {
            "Deprecated", "Signature", "Synthetic", "RuntimeVisibleAnnotations", "RuntimeInvisibleAnnotations"
    };

    public Field read(DataInputStream in) throws IOException, ClassNotFoundException {
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
        if ((ops & ACC_STATIC) == ACC_STATIC)
            sb.append("static ");
        if ((ops & ACC_FINAL) == ACC_FINAL) {
            sb.append("final ");
        } else if ((ops & ACC_VOLATILE) == ACC_VOLATILE) {
            sb.append("volatile ");
        }
        if ((ops & ACC_ENUM) == ACC_ENUM)
            sb.append("enum ");
        if ((ops & ACC_TRANSIENT) == ACC_TRANSIENT)
            sb.append("transient ");
        return sb.toString();
    }

    @Override
    public String toString() {
        return resolveAccessFlag() + descriptor + " " + name;
    }
}
