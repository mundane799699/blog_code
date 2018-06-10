package me.mundane.daggerfragmentinjectdemo.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by mundane on 2018/5/14 下午4:07
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface ActivityScope {}
