package com.selenium.ad.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE,ElementType.METHOD}) //可以标记在类和方法上
@Retention(RetentionPolicy.RUNTIME)  //source<class<runtime
public @interface IgnoreResponseAdvice {

}
