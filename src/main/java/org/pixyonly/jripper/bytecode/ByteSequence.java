package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by pixyonly on 16/1/28.
 */
public class ByteSequence extends Info<ByteSequence> {

    public static final int ACC_PUBLIC = 0x0001;

    public static final int ACC_FINAL = 0x0010;

    public static final int ACC_SUPER = 0x0020;

    public static final int ACC_INTERFACE = 0x0200;

    public static final int ACC_ABSTRACT = 0x0400;

    public static final int ACC_SYNTHETIC = 0x1000;

    public static final int ACC_ANNOTATION = 0x2000;

    public static final int ACC_ENUM = 0x4000;

    private String name;

    private String superClassName;

    private U4 magicNumber;

    private U2 minorVersion;

    private U2 majorVersion;

    private ConstantPool constantPool;

    private U2 accessFlag;

    private U2 clazz;

    private U2 superClazz;

    private Interfaces interfaces;

    private Fields fields;

    private Methods methods;

    private Attributes attributes;

    public U4 getMagicNumber() {
        return magicNumber;
    }

    public U2 getMinorVersion() {
        return minorVersion;
    }

    public U2 getMajorVersion() {
        return majorVersion;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public U2 getAccessFlag() {
        return accessFlag;
    }

    public U2 getClazz() {
        return clazz;
    }

    public U2 getSuperClazz() {
        return superClazz;
    }

    public Interfaces getInterfaces() {
        return interfaces;
    }

    public Fields getFields() {
        return fields;
    }

    public Methods getMethods() {
        return methods;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public boolean isAbstract() {
        return (ACC_ABSTRACT & accessFlag.intValue()) == ACC_ABSTRACT;
    }

    public boolean isInterface() {
        return (ACC_INTERFACE & accessFlag.intValue()) == ACC_INTERFACE;
    }

    public String resolveAccessFlag() {
        StringBuilder sb = new StringBuilder();
        if ((ACC_PUBLIC & accessFlag.intValue()) == ACC_PUBLIC)
            sb.append("public ");
        if (isInterface()) {
            sb.append("interface ");
        } else if (isAbstract()) {
            sb.append("abstract class ");
        } else if ((ACC_ENUM & accessFlag.intValue()) == ACC_ENUM) {
            sb.append("enum ");
        } else if ((ACC_ANNOTATION & accessFlag.intValue()) == ACC_ANNOTATION) {
            sb.append("@interface ");
        } else {
            sb.append("class ");
        }
        return sb.toString();
    }

    @Override
    public ByteSequence read(DataInputStream in) throws IOException, ClassNotFoundException {
        magicNumber = new U4().read(in);
        minorVersion = new U2().read(in);
        majorVersion = new U2().read(in);
        constantPool = new ConstantPool().read(in);
        accessFlag = new U2().read(in);
        clazz = new U2().read(in);
        superClazz = new U2().read(in);
        interfaces = new Interfaces().read(in);
        fields = new Fields().read(in);
        methods = new Methods().read(in);
        attributes = new Attributes().read(in);
        return this;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {

    }

    @Override
    public void link(ConstantPool constantPool) {
        ConstantItem classItem = constantPool.getItems().get(clazz.intValue() - 1);
        if (classItem.value() == null)
            classItem.link(constantPool);
        name = classItem.value();
        ConstantItem superClassItem = constantPool.getItems().get(superClazz.intValue() - 1);
        if (superClassItem.value() == null)
            superClassItem.link(constantPool);
        superClassName = superClassItem.value();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DataInputStream in = new DataInputStream(new FileInputStream("/Users/pixyonly/Engineering/Hello.class"));
        ByteSequence bs = new ByteSequence().read(in);
        bs.getConstantPool().getItems().forEach(System.out::println);
        bs.getFields().link(bs.constantPool);
        bs.getFields().getFields().forEach(System.out::println);
        bs.getMethods().link(bs.constantPool);
        bs.getMethods().getMethods().forEach(System.out::println);
        bs.link(bs.constantPool);
        System.out.print(bs.resolveAccessFlag() + bs.getName() + " ");

    }
}
