package com.siroca.testpoint

import com.nhaarman.mockitokotlin2.*
import com.siroca.domain.entity.BaseEntity
import com.siroca.domain.interactor.PutSpendResult
import com.siroca.presentation.presenter.EnergyPresenter
import com.siroca.presentation.view.EnergyView
import io.reactivex.observers.DisposableObserver
import org.junit.Before
import org.junit.Test

/**
 * Created by azamat  on 9/15/19.
 */
class EnergyPresenterTest {

    private lateinit var energyView: EnergyView
    private lateinit var putSpendResult: PutSpendResult

    private lateinit var energyPresenter: EnergyPresenter
    private lateinit var captor: KArgumentCaptor<DisposableObserver<BaseEntity>>

    @Before
    fun setup(){
        captor = argumentCaptor()
        energyView = mock()
        putSpendResult = mock()
        energyPresenter = EnergyPresenter(energyView, putSpendResult)
    }

    @Test
    fun testSuccessSpend(){
        val baseEntity = BaseEntity(0)
        energyPresenter.sendSuccessSpend()
        verify(putSpendResult).execute(captor.capture(), eq(null))

        captor.firstValue.onNext(baseEntity)
        verify(energyView).successWrite()
    }

    @Test
    fun testEnergyFail(){
        val baseEntity = BaseEntity(1)
        for(failCount in 1..3){
            energyPresenter.spendEnergyFail()
            verify(putSpendResult, never()).execute(captor.capture(), eq(null))
            verify(energyView, never()).failWrite()
        }

        energyPresenter.spendEnergyFail()
        verify(putSpendResult).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(baseEntity)
        verify(energyView).failWrite()
    }
}