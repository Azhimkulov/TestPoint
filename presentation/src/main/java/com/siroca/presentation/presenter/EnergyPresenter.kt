package com.siroca.presentation.presenter

import com.siroca.domain.entity.BaseEntity
import com.siroca.domain.interactor.PutSpendResult
import com.siroca.presentation.view.EnergyView
import io.reactivex.observers.DisposableObserver

class EnergyPresenter(
    private val energyView: EnergyView,
    private val putSpendResult: PutSpendResult
) : BasePresenter(energyView) {

    private var failCount = 0

    fun sendSuccessSpend() {
        putSpendResult.setup(0)
        putSpendResult.execute(SuccessSubscriber())
    }

    fun spendEnergyFail() {
        failCount++
        if (failCount > 3) {
            failCount = 0
            putSpendResult.setup(1)
            putSpendResult.execute(ErrorSubscriber())
        }
    }

    inner class ErrorSubscriber : DisposableObserver<BaseEntity>() {
        override fun onComplete() {}

        override fun onNext(t: BaseEntity) {
            energyView.failWrite()}

        override fun onError(e: Throwable) {}
    }

    inner class SuccessSubscriber : DisposableObserver<BaseEntity>() {
        override fun onComplete() {}

        override fun onNext(t: BaseEntity) {
            energyView.successWrite()

        }

        override fun onError(exception: Throwable) {
        }
    }
}