package com.ianarbuckle.dublinbushelper.authentication.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Ian Arbuckle on 08/08/2017.
 *
 */
@Scope
@Retention(RetentionPolicy.CLASS)
@interface AuthScope {
}
