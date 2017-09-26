package com.example.administrator.mydribbble.tools

import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.android.schedulers.AndroidSchedulers.mainThread
import rx.schedulers.Schedulers

/**
 * Created by Administrator on 2017/9/26.
 */
class RxHelper {

    companion object {
        /**
         * 输入输出都为list的observable模板
         * @param subsrcibeThread 订阅的线程
         * @param unSubsrcibeThread 解除绑定的线程
         * @param observerThread 结果返回的线程
         * */
        fun <T> listModeThread(subsrcibeThread:Scheduler? = Schedulers.io(),
                               unSubsrcibeThread:Scheduler? = Schedulers.io(),
                               observableThread: Scheduler = mainThread()):Observable.Transformer<MutableList<T>,MutableList<T>>{

            return Observable.Transformer {

                observable ->
                observable.onErrorResumeNext(NetExceptionHandler.HttpResponseFunc())
                        .retry(Constant.RX_RETRY_TIME)
                        .subscribeOn(subsrcibeThread)
                        .unsubscribeOn(unSubsrcibeThread)
                        .observeOn(observableThread)
            }
        }

    /**
     * 输入输出都为单个对象的observable模板
     * @param subscribeThread 订阅的线程
     * @param ubSubcreibeThread 解除订阅的线程
     * @param observeThread 结果返回的线程
     * */

    fun <T> singleModeThread(subsrcibeThread:Scheduler? = Schedulers.io(),
                             unSubsrcibeThread:Scheduler? = Schedulers.io(),
                             observableThread: Scheduler = mainThread()):Observable.Transformer<T,T>{
        return Observable.Transformer { observable ->
            observable.onErrorResumeNext(NetExceptionHandler.HttpResponseFunc())
                    .retry(Constant.RX_RETRY_TIME)
                    .subscribeOn(subsrcibeThread)
                    .unsubscribeOn(unSubsrcibeThread)
                    .observeOn(observableThread)
        }
    }

    /**
     * 输入输出都为单个对象的observable模板
     *
     * @param subscribeThread 订阅的线程
     * @param unSubscribeThread 解除订阅的线程
     * @param observeThread 结果返回的线程
     */
    fun <T> singleModeThreadNormal(subscribeThread: Scheduler? = Schedulers.io(),
                                   unSubscribeThread: Scheduler? = Schedulers.io(),
                                   observeThread: Scheduler? = AndroidSchedulers.mainThread()): Observable.Transformer<T, T> {
        return Observable.Transformer { observable ->
            observable.subscribeOn(subscribeThread).
                    unsubscribeOn(unSubscribeThread).
                    observeOn(observeThread)
        }
    }
    }
}
