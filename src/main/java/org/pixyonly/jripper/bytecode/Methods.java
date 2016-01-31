package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Methods extends Info<Methods> {

    private U2 methodCount;

    private List<Method> methods;

    public U2 getMethodCount() {
        return methodCount;
    }

    public List<Method> getMethods() {
        return methods;
    }

    @Override
    public Methods read(DataInputStream in) throws IOException, ClassNotFoundException {
        methodCount = new U2().read(in);
        int size = methodCount.intValue();
        methods = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            methods.add(new Method().read(in));
        }
        return this;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {

    }

    @Override
    public void link(ConstantPool constantPool) {
        for(Method method : methods)
            method.link(constantPool);
    }
}
