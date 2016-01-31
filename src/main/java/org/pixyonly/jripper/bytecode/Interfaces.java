package org.pixyonly.jripper.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixyonly on 16/1/28.
 */
public class Interfaces extends Info<Interfaces> {

    private U2 interfaceCount;

    private List<U2> interfaces;

    private List<String> interfaceName;

    public U2 getInterfaceCount() {
        return interfaceCount;
    }

    public List<U2> getInterfaces() {
        return interfaces;
    }

    public List<String> getInterfaceName() {
        return interfaceName;
    }

    @Override
    public Interfaces read(DataInputStream in) throws IOException, ClassNotFoundException {
        interfaceCount = new U2().read(in);
        int size = interfaceCount.intValue();
        interfaces = new ArrayList<>(size);
        interfaceName = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            interfaces.add(new U2().read(in));
        }
        return this;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {

    }

    @Override
    public void link(ConstantPool constantPool) {
        for (U2 u2 : interfaces) {
            ConstantItem interfaceItem = constantPool.getItems().get(u2.intValue() - 1);
            if (interfaceItem.value() == null)
                interfaceItem.link(constantPool);
            interfaceName.add(interfaceItem.value());
        }
    }
}
