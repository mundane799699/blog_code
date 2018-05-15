package me.mundane.dagger2learning.di.components;

import dagger.Component;
import me.mundane.dagger2learning.bean.User;

/**
 * Created by mundane on 2018/5/11 下午4:30
 */

@Component
public interface MainComponent2 {
    User getUser();
}
