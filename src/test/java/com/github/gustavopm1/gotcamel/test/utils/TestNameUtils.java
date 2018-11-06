package com.github.gustavopm1.gotcamel.test.utils;


import com.github.gustavopm1.gotcamel.test.annotations.MethodName;
import lombok.extern.slf4j.Slf4j;
import org.junit.rules.TestName;

@Slf4j
public class TestNameUtils {

    public static TestName getTestName(Class clazz) {

        return new TestName() {

            @Override
            public String getMethodName() {
                try {
                    MethodName methodName = clazz.getMethod(super.getMethodName()).getAnnotation(MethodName.class);
                    if (methodName != null) {
                        return HostUtil.appendHostName(methodName.value());
                    }
                } catch (Exception e) {
                }
                return HostUtil.appendHostName(super.getMethodName());
            }
        };
    }
}

