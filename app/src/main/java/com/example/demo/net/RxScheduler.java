package com.example.demo.net;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Publisher;

/**
 * 统一线程处理
 */
public class RxScheduler {

    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return FlowableTransformer
     */
    public static <T> FlowableTransformer< T, T> Flo_io_main() {
        return new FlowableTransformer<T, T>() {
            @Override
            public @NonNull Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())//指定被观察者在哪个线程执行
                        .observeOn(AndroidSchedulers.mainThread());//指定观察者在哪个线程观察
            }
        };
    }

    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> Obs_io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public @NonNull ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return MaybeTransformer
     */
    public static <T> MaybeTransformer<T,T> May_io_main() {
        return new MaybeTransformer<T, T>() {
            @Override
            public @NonNull MaybeSource<T> apply(@NonNull Maybe<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return SingleTransformer
     */
    public static <T>SingleTransformer<T,T> Sin_io_main() {
        return new SingleTransformer<T, T>() {
            @Override
            public @NonNull SingleSource<T> apply(@NonNull Single<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
