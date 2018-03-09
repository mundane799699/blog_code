package me.mundane.myrxjavasummary.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by mundane on 2018/2/25 下午3:16
 */
@Table(database = AppDatabase.class)
public class DBFlowModel extends BaseModel {
    @PrimaryKey(autoincrement = true) //主键  //autoincrement 开启自增
    public int id;
    @Column
    public  String name;
    @Column
    public int age;
    @Column
    public String address;
    @Column
    public int phone;
}
