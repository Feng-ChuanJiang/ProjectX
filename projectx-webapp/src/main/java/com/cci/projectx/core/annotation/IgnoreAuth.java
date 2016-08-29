package com.cci.projectx.core.annotation;

import java.lang.annotation.*;

/**
 * Created by haiquanli on 15/11/20.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {
}
