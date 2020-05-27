package com.njuse.seecjvm.runtime.struct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slot {
    private JObject object;
    private Integer value;

    public JObject getObject() {
        return object;
    }
    public void setObject(JObject object) {
        this.object = object;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
}
