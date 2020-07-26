package com.tbs.domain.interactor

import com.tbs.domain.executer.PostExecutionThread
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class CompletableUseCase<in Params>
constructor(private val postExecutionThread: PostExecutionThread) {
    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    fun execute(observer: DisposableCompletableObserver, params: Params?) {
        val completable = buildUseCaseCompletable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)

        addDisposable(completable.subscribeWith(observer))
    }

    fun dispose() {
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}