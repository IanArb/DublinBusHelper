package com.ianarbuckle.dublinbushelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Ian Arbuckle on 13/08/2017.
 *
 */
@Scope
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {
}
