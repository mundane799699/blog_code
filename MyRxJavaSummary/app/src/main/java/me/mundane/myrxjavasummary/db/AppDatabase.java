package me.mundane.myrxjavasummary.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by mundane on 2018/2/25 下午3:14
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    //数据库名称
    public static final String NAME = "AppDataBase";
    //数据库版本
    public static final int VERSION = 1;
}
