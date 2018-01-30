package me.mundane.myrxjavasummary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import me.mundane.myrxjavasummary.bean.Data;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func2;

public class EditTextSampleActivity extends AppCompatActivity {

    private EditText mEtFirst;
    private Observable<String> mFirstObservable;
    private EditText mEtSecond;
    private Observable<String> mSecondObservable;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_sample);
        bindView();
        bindListener();

    }

    private void bindListener() {
        mFirstObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                mEtFirst.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        subscriber.onNext(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        mSecondObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                mEtSecond.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        subscriber.onNext(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        // zip只有两个editText都发生改变的时候才会发射, 就是说first和second的变化一一对应
        // 如果一个一直在变化而另一个不变化, 也会把那个变化的那个发射出来的缓存起来, 一旦另一个Observable变化了就立刻发射出来
//        Observable<Data> zipedObservable = Observable.zip(mFirstObservable, mSecondObservable, new Func2<String, String, Data>() {
//            @Override
//            public Data call(String s, String s2) {
//                return new Data(s, s2);
//            }
//        });

        Observable<Data> combineLatest = Observable.combineLatest(mFirstObservable, mSecondObservable, new Func2<String, String, Data>() {
            @Override
            public Data call(String s, String s2) {
                return new Data(s, s2);
            }
        });

//        Observable<String> concat = Observable.concat(mFirstObservable, mSecondObservable);
//        concat.subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                mTv.setText(s);
//            }
//        });
		combineLatest.subscribe(new Action1<Data>() {
			@Override
			public void call(Data data) {
				mTv.setText("first = " + data.first +", seoncd = " + data.second);
			}
		});

//		Observable<String> merge = Observable.merge(mFirstObservable, mSecondObservable);
//		merge.subscribe(new Action1<String>() {
//			@Override
//			public void call(String s) {
//				mTv.setText(s);
//			}
//		});

//		zipedObservable.subscribe(new Action1<Data>() {
//			@Override
//			public void call(Data data) {
//				mTv.setText("first = " + data.first +", seoncd = " + data.second);
//			}
//		});


//		mFirstObservable.subscribe(new Action1<String>() {
//			@Override
//			public void call(String s) {
//				mEtSecond.setText(s);
//			}
//		});

    }

    private void bindView() {
        mEtFirst = findViewById(R.id.et_first);
        mEtSecond = findViewById(R.id.et_second);
        mTv = findViewById(R.id.tv);
    }

}
