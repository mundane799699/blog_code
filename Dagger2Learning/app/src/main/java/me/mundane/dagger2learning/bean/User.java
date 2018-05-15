package me.mundane.dagger2learning.bean;

import javax.inject.Inject;

/**
 * Created by mundane on 2018/5/11 上午10:52
 */

public class User {
    
    public String name;
    public int age;
    
    @Inject
    public User() {
        name = "mundane";
        age = 10;
    }
}
