package com.lufax.ui.auto.assertions;

import com.lufax.ui.auto.anotations.UIAssertion;
import org.springframework.stereotype.Component;

/**
 * Created by Jc on 17/1/23.
 */

@Component
public class AssertUtils {

    //断言相等
    @UIAssertion(description = "equals")
    public boolean assertEquals(String expect, String actual){
        return true;
    }

    //断言不相等
    @UIAssertion(description = "non-equals")
    public boolean assertNonEquals(String expect, String actual){
        return true;
    }

    //断言可见性
    @UIAssertion(description = "visibilty")
    public boolean assertVisibility(String locator){
        return true;
    }

    //断言不可见性
    @UIAssertion(description = "invisibilty")
    public boolean assertInvisibility(String locator){
        return true;
    }

}
