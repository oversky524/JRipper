package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Fields extends Info<Fields> {

    private U2 fieldCount;

    private List<Field> fields;

    public U2 getFieldCount() {
        return fieldCount;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void link(ConstantPool constantPool) {
        for (Field f : fields)
            f.link(constantPool);
    }

    @Override
    public Fields read(DataInputStream in) throws IOException, ClassNotFoundException {
        fieldCount = new U2().read(in);
        int size = fieldCount.intValue();
        fields = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            fields.add(new Field().read(in));
        }
        return this;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {

    }
}
