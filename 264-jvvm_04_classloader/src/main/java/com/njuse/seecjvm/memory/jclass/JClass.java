package com.njuse.seecjvm.memory.jclass;

import com.njuse.seecjvm.classloader.classfileparser.ClassFile;
import com.njuse.seecjvm.classloader.classfileparser.FieldInfo;
import com.njuse.seecjvm.classloader.classfileparser.MethodInfo;
import com.njuse.seecjvm.classloader.classfileparser.constantpool.ConstantPool;
import com.njuse.seecjvm.classloader.classfilereader.classpath.EntryType;
import com.njuse.seecjvm.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.seecjvm.runtime.Vars;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JClass {
    private short accessFlags;
    private String name;
    private String superClassName;
    private String[] interfaceNames;
    private RuntimeConstantPool runtimeConstantPool;
    private Field[] fields;
    private Method[] methods;
    private EntryType loadEntryType;
    private JClass superClass;
    private JClass[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private Vars staticVars;
    private InitState initState;
    public void setInitState(InitState initState) {
        this.initState = initState;
    }
    public void setLoadEntryType(EntryType loadEntryType) {
        this.loadEntryType = loadEntryType;
    }
    public void setStaticVars(Vars staticVars) {
        this.staticVars = staticVars;
    }
    public void setStaticSlotCount(int staticSlotCount) {
        this.staticSlotCount = staticSlotCount;
    }
    public void setInstanceSlotCount(int instanceSlotCount) {
        this.instanceSlotCount = instanceSlotCount;
    }
    public Vars getStaticVars() {
        return staticVars;
    }
    public InitState getInitState() {
        return initState;
    }
    public RuntimeConstantPool getRuntimeConstantPool() {
        return runtimeConstantPool;
    }
    public int getInstanceSlotCount() {
        return instanceSlotCount;
    }
    public int getStaticSlotCount() {
        return staticSlotCount;
    }
    public Field[] getFields() {
        return fields;
    }
    public JClass getSuperClass() {
        return superClass;
    }
    public String getName() {
        return name;
    }
    public JClass[] getInterfaces() {
        return interfaces;
    }
    public EntryType getLoadEntryType() {
        return loadEntryType;
    }
    public String getSuperClassName() {
        return superClassName;
    }
    public void setSuperClass(JClass superClass) {
        this.superClass = superClass;
    }
    public String[] getInterfaceNames() {
        return interfaceNames;
    }

    public JClass(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlags();
        this.name = classFile.getClassName();
        if (!this.name.equals("java/lang/Object")) {
            // index of super class of java/lang/Object is 0
            this.superClassName = classFile.getSuperClassName();
        } else {
            this.superClassName = "";
        }
        this.interfaceNames = classFile.getInterfaceNames();
        this.fields = parseFields(classFile.getFields());
        this.methods = parseMethods(classFile.getMethods());
        this.runtimeConstantPool = parseRuntimeConstantPool(classFile.getConstantPool());
    }

    private Field[] parseFields(FieldInfo[] info) {
        int len = info.length;
        fields = new Field[len];
        for (int i = 0; i < len; i++) {
            fields[i] = new Field(info[i], this);
        }
        return fields;
    }

    private Method[] parseMethods(MethodInfo[] info) {
        int len = info.length;
        methods = new Method[len];
        for (int i = 0; i < len; i++) {
            methods[i] = new Method(info[i], this);
        }
        return methods;
    }

    private RuntimeConstantPool parseRuntimeConstantPool(ConstantPool cp) {
        return new RuntimeConstantPool(cp, this);
    }

    public String getPackageName() {
        int index = name.lastIndexOf('/');
        if (index >= 0) return name.substring(0, index);
        else return "";
    }

    public boolean isPublic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PUBLIC);
    }

    public boolean isAccessibleTo(JClass caller) {
        //todo
        /**
         * Add some codes here.
         * Refer to jvm specification 5.4.4
         */
        if (isPublic()||caller.getPackageName().equals(getPackageName())) return true;
        return false;
    }
}
