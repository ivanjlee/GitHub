package com.github.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation indicate that this class not to be proguard
 *
 * @author Ivan J. Lee on 2020-10-24 23:13
 * @version v0.1
 * @since v1.0
 **/
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface NoProguard {
}
