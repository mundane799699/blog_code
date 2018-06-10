package me.mundane.dagger2learning.di.qualifiers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

/**
 * Created by mundane on 2018/5/17 上午10:47
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Release {

}
