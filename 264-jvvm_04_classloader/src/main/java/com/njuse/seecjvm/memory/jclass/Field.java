package com.njuse.seecjvm.memory.jclass;

import com.njuse.seecjvm.classloader.classfileparser.FieldInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Field extends ClassMember {
    private int slotID;
    private int constValueIndex;

    public int getSlotID() {
        return slotID;
    }
    public void setSlotID(int slotID) {
        this.slotID = slotID;
    }
    public int getConstValueIndex() {
        return constValueIndex;
    }
    public Field(FieldInfo info, JClass clazz) {
        this.clazz = clazz;
        accessFlags = info.getAccessFlags();
        name = info.getName();
        descriptor = info.getDescriptor();
        if (info.getConstantValueAttr() != null) {
            constValueIndex = info.getConstantValueAttr().getConstantValueIndex();
        }
    }
}
