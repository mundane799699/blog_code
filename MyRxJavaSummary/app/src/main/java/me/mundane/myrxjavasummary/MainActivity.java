package me.mundane.myrxjavasummary;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.jakewharton.rxbinding.view.RxView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.mundane.myrxjavasummary.bean.Student;
import me.mundane.myrxjavasummary.db.DBFlowModel;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView mIv;
    private Button button;
    private int mDivisor;
    private final int maxRetries = 10;
    private int retryCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = findViewById(R.id.iv);
        button = findViewById(R.id.btn);
        registerThrottleFirst();
    }

    public void subscribe(View view) {
        // 开关作为被观察者
        Observable<String> switcher = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("on");
                subscriber.onNext("off");
                subscriber.onNext("on");
                subscriber.onNext("on");
                subscriber.onCompleted();
            }
        });

        // 电灯作为观察者, 对始终在观察者开关的动作, 对开关的动作而做出相应的反应
        Subscriber<String> light = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // 被观察者订阅了观察者
        Subscription subscription = switcher.subscribe(light);
        subscription.unsubscribe();
    }

    public void onError(View view) {
        // 感觉像是一个回调接口, 在call()方法执行的时候会调用传进来的那个接口对象subscriber的相应的方法
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(0);
                subscriber.onNext(3);
                subscriber.onNext(5 / 0);
                subscriber.onCompleted();
            }
        });

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                // subscriber的onNext中出现的异常和observable和call方法中的给onNext()传参数出现的异常都会出现在这里
                // 总之就是call()方法中发生的异常都会出现在这里
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + 10 / integer);
            }
        };

        observable.subscribe(subscriber);
    }

    public void filter(View view) {
        Observable.just("on", "off", "on", "on").filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                // 如果这里返回了true数据就会被回调到onNext, 否则返回了false就会被过滤掉
                return TextUtils.equals("on", s);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }
        });
    }

    public void scheduler(View view) {
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                Drawable drawable = getResources().getDrawable(R.mipmap.avatar);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe()发生在IO线程, 即在订阅这个过程发生在IO线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        Log.d(TAG, "onNext: thread = " + Thread.currentThread().getName());
                        mIv.setImageDrawable(drawable);
                    }
                });
    }

    public void map(View view) {
        Observable.just(R.mipmap.avatar).map(new Func1<Integer, Drawable>() {
            @Override
            public Drawable call(Integer integer) {
                Drawable drawable = getResources().getDrawable(integer);
                return drawable;
            }
        }).subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {
                mIv.setImageDrawable(drawable);
            }
        });
    }

    private Drawable getDrawableFromResId(Integer integer) {
        return getResources().getDrawable(integer);
    }

    public void compose(View view) {
        Observable.Transformer<Integer, Drawable> transformer =
                new Observable.Transformer<Integer, Drawable>() {
                    @Override
                    public Observable<Drawable> call(Observable<Integer> observable) {
                        return observable.map(new Func1<Integer, Drawable>() {
                            @Override
                            public Drawable call(Integer integer) {
                                Drawable drawable = getResources().getDrawable(integer);
                                return drawable;
                            }
                        }).map(new Func1<Drawable, Drawable>() {
                            @Override
                            public Drawable call(Drawable drawable) {
                                return drawable;
                            }
                        }); // observable.map后面可以继续.lift
                    }
                };
        Observable.just(R.mipmap.avatar)
                // compose是为了将一系列的变换方法封装起来
                .compose(transformer).subscribe(new Action1<Drawable>() {
            @Override
            public void call(Drawable drawable) {
                mIv.setImageDrawable(drawable);
            }
        });
    }

    public void flatmap(View view) {
        Student student1 = new Student();
        student1.setStudentName("小明");
        List<Student.Course> courseList1 = new ArrayList<>();
        courseList1.add(new Student.Course("语文"));
        courseList1.add(new Student.Course("数学"));
        student1.setCourses(courseList1);

        Student student2 = new Student();
        student2.setStudentName("小红");
        List<Student.Course> courseList2 = new ArrayList<>();
        courseList2.add(new Student.Course("英语"));
        courseList2.add(new Student.Course("化学"));
        student2.setCourses(courseList2);

        Student[] students = { student1, student2 };

        Observable.from(students).flatMap(new Func1<Student, Observable<Student.Course>>() {
            @Override
            public Observable<Student.Course> call(Student student) {
                return Observable.from(student.getCourses());
            }
        }).subscribe(new Subscriber<Student.Course>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Student.Course course) {
                Log.d(TAG, "onNext: course = " + course.courseName);
            }
        });
    }

    public void scheduler2(View view) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                subscriber.onNext("1");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // 指定数据发出所发生的线程, 只有第一个有效
                .observeOn(Schedulers.newThread()) // 指定下面那段代码执行的线程
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                        return "变换后 " + s;
                    }
                }).observeOn(AndroidSchedulers.mainThread()) // 指定下面那段代码执行的线程
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                        return "第二次变换 " + s;
                    }
                }).observeOn(AndroidSchedulers.mainThread()) // 指定下面那段代码执行的线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                        Log.d(TAG, "call: 最终的输出结果: " + s);
                    }
                });
    }

    public void doOnSubscribe(View view) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // 指定事件发生在io线程
                .doOnSubscribe(new Action0() { // doOnSubscribe在subscribe()调用后而且在事件发送前执行
                    @Override
                    public void call() {
                        Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                        Log.d(TAG, "call: 数据发送之前显示progressbar");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread()) // 指定doOnSubscribe()发生在主线程
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                Log.d(TAG, "call: 输出最终的数据" + integer);
            }
        });
    }

    public void doOnNext(View view) {
        Observable.just(1, 2, 3).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "doOnNext: integer = " + integer);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "call: 最终输出 " + integer);
            }
        });
    }

    public void timer(View view) {
        Observable.timer(3, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "onNext: " + aLong);
            }
        });
    }

    public void interval(View view) {
        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "onNext: " + aLong);
            }
        });
    }

    private void registerThrottleFirst() {
        // 只返回一秒内的第一个, 后续发射出来的全部丢弃
        RxView.clicks(button).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "onNext: button clicked");
            }
        });
    }

    public void schedulePeriodically(View view) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> observer) {
                Schedulers.newThread().createWorker().schedulePeriodically(new Action0() {
                    @Override
                    public void call() {
                        observer.onNext("呵呵");
                    }
                }, 0, 3, TimeUnit.SECONDS);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "call: " + s);
            }
        });
    }

    public void retrywhen2() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> s) {
                System.out.println("subscribing");
                s.onError(new RuntimeException("always fails"));
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> attempts) {
                return attempts.zipWith(Observable.range(1, 3),
                        new Func2<Throwable, Integer, Integer>() {
                            @Override
                            public Integer call(Throwable throwable, Integer integer) {
                                return integer;
                            }
                        }).flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer i) {
                        System.out.println("delay retry by " + i + " second(s)");
                        return Observable.timer(i, TimeUnit.SECONDS);
                    }
                });
            }
        }).toBlocking().forEach(new Action1<String>() {
            @Override
            public void call(String o) {
                Log.d(TAG, "o = " + o);
                System.out.println(o);
            }
        });
    }

    public void retrywhen(View view) {
        mDivisor = 0;
        retryCount = 0;
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3 / mDivisor);
                subscriber.onCompleted();
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(final Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {

                        if (throwable instanceof ArithmeticException) {
                            if (++retryCount <= maxRetries) {
                                Log.d(TAG, "正在重试");
                                if (retryCount == 3) {
                                    mDivisor = 1;
                                }
                                return Observable.timer(2, TimeUnit.SECONDS);
                            }
                        }
                        return Observable.error(throwable);
                    }
                });
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: integer = " + integer);
            }
        });
    }

    public void concat(View view) {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "concat: " + integer);
                    }
                });
    }

    public void zip(View view) {
        Observable.zip(Observable.just(1, 2, 3), Observable.just("A", "B", "C"),
                new Func2<Integer, String, String>() {
                    @Override
                    public String call(Integer integer, String s) {
                        return integer + s;
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "zip: " + s);
            }
        });
    }

    public void merge(View view) {
        // 拼接两个Observable的输出，不保证顺序，按照事件产生的顺序发送给订阅者
        // 与concat的区别在于不保证顺序, 按照事件产生的顺序
        Observable.merge(Observable.just("1", "2", "3"), Observable.just("A", "B", "C"))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "merge: " + s);
                    }
                });
    }

    // 使用rxjava代替asynctask
    public void asynctask(View view) {


        Observable.create(new OnSubscribe<List<DBFlowModel>>() {
            @Override
            public void call(Subscriber<? super List<DBFlowModel>> subscriber) {
                Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                List<DBFlowModel> dbFlowModels = SQLite.select()
                        .from(DBFlowModel.class)
                        .queryList();
                subscriber.onNext(dbFlowModels);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DBFlowModel>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                        Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(List<DBFlowModel> dbFlowModels) {
                        Log.d(TAG, "onNext: ");
                        Log.d(TAG, "call: thread = " + Thread.currentThread().getName());
                        Log.d(TAG, "dbFlowModels = " + dbFlowModels);
                        Log.d(TAG, "dbFlowModels.size() = " + dbFlowModels.size());
                    }
                });
    }
}
