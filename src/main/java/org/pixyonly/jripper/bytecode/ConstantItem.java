package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public abstract class ConstantItem extends Info<ConstantItem> {

    protected U1 tag;

    protected int index;

    protected ConstantItem setTag(U1 tag) {
        this.tag = tag;
        return this;
    }

    public ConstantItem setIndex(int index) {
        this.index = index;
        return this;
    }

    public abstract String value();

    public static ConstantItem parseConstantItem(U1 tag, DataInputStream in) throws IOException, ClassNotFoundException {
        switch (tag.getB()) {
            case ConstantPool.CONSTANT_UTF8_TAG:
                return new ConstantUtf8().setTag(tag).read(in);
            case ConstantPool.CONSTANT_INTEGER_TAG:
                return new ConstantInteger().setTag(tag).read(in);
            case ConstantPool.CONSTANT_CLASS_TAG:
                return new ConstantClass().setTag(tag).read(in);
            case ConstantPool.CONSTANT_DOUBLE_TAG:
                return new ConstantDouble().setTag(tag).read(in);
            case ConstantPool.CONSTANT_FIELDREF_TAG:
                return new ConstantFieldRef().setTag(tag).read(in);
            case ConstantPool.CONSTANT_FLOAT_TAG:
                return new ConstantFloat().setTag(tag).read(in);
            case ConstantPool.CONSTANT_INTERFACEMETHODREF_TAG:
                return new ConstantInterfaceMethodRef().setTag(tag).read(in);
            case ConstantPool.CONSTANT_LONG_TAG:
                return new ConstantLong().setTag(tag).read(in);
            case ConstantPool.CONSTANT_METHODREF_TAG:
                return new ConstantMethodRef().setTag(tag).read(in);
            case ConstantPool.CONSTANT_METHODTYPE_TAG:
                return new ConstantMethodRef().setTag(tag).read(in);
            case ConstantPool.CONSTANT_NAMEANDTYPE_TAG:
                return new ConstantNameAndType().setTag(tag).read(in);
            case ConstantPool.CONSTANT_STRING_TAG:
                return new ConstantString().setTag(tag).read(in);

        }
        throw new ClassResolveException();
    }

    @Override
    public String toString() {
        return String.format("#%d(%s)->%s", index,
                this.getClass().getSimpleName(),
                value());
    }

    public static class ConstantUtf8 extends ConstantItem {

        private U2 length;

        private List<U1> bytes;

        private String utf8;

        public ConstantUtf8 read(DataInputStream in) throws IOException, ClassNotFoundException {
            length = new U2().read(in);
            int size = length.intValue();
            bytes = new ArrayList<>(size);
            byte[] array = new byte[size];
            for (int i = 0; i < size; i++) {
                bytes.add(new U1().read(in));
                array[i] = bytes.get(i).getB();
            }
            //TODO : utf-8压缩编码
            utf8 = new String(array, Charset.forName("utf-8"));
            return this;
        }

        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {

        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantClass extends ConstantItem {

        private U2 nameIndex;

        private String utf8;

        @Override
        public ConstantItem read(DataInputStream in) throws IOException, ClassNotFoundException {
            nameIndex = new U2().read(in);
            return this;
        }

        @Override
        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {
            ConstantItem item = constantPool.getItems().get(nameIndex.intValue() - 1);
            if (item.value() == null) {
                item.link(constantPool);
            }
            utf8 = item.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantInteger extends ConstantItem {

        private U4 bytes;

        public ConstantInteger read(DataInputStream in) throws IOException, ClassNotFoundException {
            bytes = new U4().read(in);
            return this;
        }

        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {

        }

        @Override
        public String value() {
            return String.valueOf(bytes.intValue());
        }
    }

    public static class ConstantFloat extends ConstantItem {

        private U4 bytes;

        @Override
        public ConstantItem read(DataInputStream in) throws IOException, ClassNotFoundException {
            bytes = new U4().read(in);
            return this;
        }

        @Override
        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {

        }

        @Override
        public String value() {
            //TODO: float编码
            return String.valueOf(bytes.intValue());
        }

    }

    public static class ConstantLong extends ConstantItem {

        private U8 bytes;

        @Override
        public ConstantItem read(DataInputStream in) throws IOException, ClassNotFoundException {
            bytes = new U8().read(in);
            return this;
        }

        @Override
        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {

        }

        @Override
        public String value() {
            return String.valueOf(bytes.longValue());
        }
    }

    public static class ConstantDouble extends ConstantItem {

        private U8 bytes;

        @Override
        public ConstantItem read(DataInputStream in) throws IOException, ClassNotFoundException {
            bytes = new U8().read(in);
            return this;
        }

        @Override
        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {

        }

        @Override
        public String value() {
            //TODO: double编码
            return String.valueOf(bytes.longValue());
        }

    }

    public static class ConstantString extends ConstantItem {

        private U2 liteIndex;

        private String utf8;

        @Override
        public ConstantItem read(DataInputStream in) throws IOException, ClassNotFoundException {
            liteIndex = new U2().read(in);
            return this;
        }

        @Override
        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {
            ConstantItem item = constantPool.getItems().get(liteIndex.intValue() - 1);
            if (item.value() == null) {
                item.link(constantPool);
            }
            utf8 = item.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantFieldRef extends ConstantItem {

        private U2 classIndex;

        private U2 nameIndex;

        private String utf8;

        @Override
        public ConstantItem read(DataInputStream in) throws IOException, ClassNotFoundException {
            classIndex = new U2().read(in);
            nameIndex = new U2().read(in);
            return this;
        }

        @Override
        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {
            ConstantItem classItem = constantPool.getItems().get(classIndex.intValue() - 1);
            if (classItem.value() == null) {
                classItem.link(constantPool);
            }
            ConstantItem nameItem = constantPool.getItems().get(nameIndex.intValue() - 1);
            if (nameItem.value() == null) {
                nameItem.link(constantPool);
            }
            utf8 = classItem.value() + " " + nameItem.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantMethodRef extends ConstantItem {

        private U2 classIndex;

        private U2 nameIndex;

        private String utf8;

        @Override
        public ConstantItem read(DataInputStream in) throws IOException, ClassNotFoundException {
            classIndex = new U2().read(in);
            nameIndex = new U2().read(in);
            return this;
        }

        @Override
        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {
            ConstantItem classItem = constantPool.getItems().get(classIndex.intValue() - 1);
            if (classItem.value() == null) {
                classItem.link(constantPool);
            }
            ConstantItem nameItem = constantPool.getItems().get(nameIndex.intValue() - 1);
            if (nameItem.value() == null) {
                nameItem.link(constantPool);
            }
            utf8 = classItem.value() + " " + nameItem.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantInterfaceMethodRef extends ConstantItem {

        private U2 classIndex;

        private U2 nameIndex;

        private String utf8;

        @Override
        public ConstantItem read(DataInputStream in) throws IOException, ClassNotFoundException {
            classIndex = new U2().read(in);
            nameIndex = new U2().read(in);
            return this;
        }

        @Override
        public void write(DataOutputStream out) throws IOException {

        }

        @Override
        public void link(ConstantPool constantPool) {
            ConstantItem classItem = constantPool.getItems().get(classIndex.intValue() - 1);
            if (classItem.value() == null) {
                classItem.link(constantPool);
            }
            ConstantItem nameItem = constantPool.getItems().get(nameIndex.intValue() - 1);
            if (nameItem.value() == null) {
                nameItem.link(constantPool);
            }
            utf8 = classItem.value() + " " + nameItem.value();
        }

        @Override
        public String value() {
            return utf8;
        }
    }

    public static class ConstantNameAndType extends ConstantItem {

        private U2 nameIndex;

        private U2 descriptorIndex;

        private String utf8;

        @Override
        public ConstantItem read(DataInputStream in) throws IOException, ClassNotFoundException {
            nameIndex = new U2().read(in);
            descriptorIndex = new U2().read(in);
            return this;
        }

        @Override
        public void write(DataOutputStream out) throws IOException {

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
            String name = nameItem.value();
            String descriptor = descriptorItem.value();
            utf8 = String.format("%s(%s)", name, descriptor);
        }

        @Override
        public String value() {
            return utf8;
        }
    }
}
