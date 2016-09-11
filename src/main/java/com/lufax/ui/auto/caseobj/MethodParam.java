package com.lufax.ui.auto.caseobj;

import org.springframework.stereotype.Component;

/**
 * Created by Jc on 16/9/11.
 */

@Component
public class MethodParam {

    public String name;
    public String type;
    public String value;

    public String getName() {
        return name;
    }

    public MethodParam setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public MethodParam setType(String type) {
        this.type = type;
        return this;
    }

    public String getValue() {
        return value;
    }

    public MethodParam setValue(String value) {
        this.value = value;
        return this;
    }
}
