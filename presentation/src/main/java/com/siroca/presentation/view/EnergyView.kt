package com.siroca.presentation.view


interface EnergyView:BaseView {
    fun startBaseHandler(millis: Long, onPostDelay: () -> Unit)
    fun stopBaseHandler()

    fun startSpendingEnergy(millis: Long)
    fun stopSpendingEnergy()

    fun successWrite()
    fun failWrite()
    fun sendInvite()
}