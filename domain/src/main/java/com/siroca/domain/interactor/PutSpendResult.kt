package com.siroca.domain.interactor

import com.siroca.domain.entity.BaseEntity
import com.siroca.domain.executor.PostExecutionThread
import com.siroca.domain.repository.BaseRepository
import io.reactivex.Observable

/**
 * Created by azamat  on 9/15/19.
 */
open class PutSpendResult(
    private val baseModelRepository: BaseRepository<BaseEntity>,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<BaseEntity, Void?>(postExecutionThread) {

    private var status:Int = 1001

    fun setup(status:Int){
        this.status = status
    }

    override fun buildUseCaseObservable(params: Void?): Observable<BaseEntity> {
        return baseModelRepository.getBase(status)
    }
}