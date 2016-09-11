package com.lufax.ui.auto.caseobj;

import org.springframework.stereotype.Component;

/**
 * Created by Jc on 16/9/11.
 */

@Component
public class AssertKey {

    public String value;
    public String locationType;
    public String compareValue;

    public String getValue() {
        return value;
    }

    public AssertKey setValue(String value) {
        this.value = value;
        return this;
    }

    public String getLocationType() {
        return locationType;
    }

    public AssertKey setLocationType(String locationType) {
        this.locationType = locationType;
        return this;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public AssertKey setCompareValue(String compareValue) {
        this.compareValue = compareValue;
        return this;
    }
}
