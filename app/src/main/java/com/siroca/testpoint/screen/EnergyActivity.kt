package com.siroca.testpoint.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.util.Log
import android.widget.Toast
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.siroca.presentation.presenter.EnergyPresenter
import com.siroca.presentation.view.EnergyView
import com.siroca.testpoint.R
import com.siroca.testpoint.utils.CacheHelper
import com.siroca.testpoint.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_main.*

class EnergyActivity : KodeinAppCompatActivity(), EnergyView, SensorEventListener {

    /**
     *  Метод [successWrite] и [failWrite] записывают попытки траты нергии к кэш
     *  первый при удачном, второй при трех неудачных попытках
     * */
    override fun successWrite() {
        Toast.makeText(this, "Энергия успешно потрачена.", Toast.LENGTH_LONG).show()
        CacheHelper.writeSuccess()
    }

    override fun failWrite() {
        CacheHelper.writeFail()
    }

    /**
     *  Метод для траты энергии. При трате больше 10 сек отправляется запрос и телефон вибрирует
     * */
    override fun startSpendingEnergy(millis: Long) {
        if (spendingEnergyTimer == null) {
            spendingEnergyTimer = Handler()
            spendingEnergyTimer!!.removeCallbacksAndMessages(null)
            spendingEnergyTimer!!.postDelayed({
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    v.vibrate(500)
                }
                spendingEnergyTimer = null
                energyPresenter.sendSuccessSpend()
            }, millis)
        }
    }

    /**
     *  Метод для сброса таймера при спокойном поведении
     * */
    override fun stopSpendingEnergy() {
        spendSleepTimer.removeCallbacksAndMessages(null)
        spendSleepTimer.postDelayed({
            spendingEnergyTimer?.removeCallbacksAndMessages(null)
            spendingEnergyTimer = null
            energyPresenter.spendEnergyFail()
        }, 1000)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.d("Sensor", p1.toString())
    }

    private var mAccelLast: Float = 0f
    private var mAccelCurrent: Float = 0f
    private var mAccel: Float = 0f
    private var firstTime = true

    /**
     *  Слушатель акселерометра
     * */
    override fun onSensorChanged(se: SensorEvent?) {
        val x = se!!.values[0]
        val y = se.values[1]
        val z = se.values[2]
        mAccelLast = mAccelCurrent
        mAccelCurrent = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
        val delta = mAccelCurrent - mAccelLast
        mAccel = mAccel * 0.9f + delta

        if (mAccel.toInt() == 0 && firstTime) {
            firstTime = false
            sendInvite()
        }

        if (mAccel > 3 && !firstTime) {
            if (!baseDialog.isMyCopy("Вы тратите энергию")) {
                stopBaseHandler()
                if (!isFinishing)
                    baseDialog.showBaseDialog("Вы тратите энергию")
            }
            startBaseHandler(3000) {
                baseDialog.dismiss()
                startBaseHandler(5000) { sendInvite() }
            }

            startSpendingEnergy(10000)
            stopSpendingEnergy()
        }
    }

    /**
     *  Запускает [handler] с заданой отложеностью, по истечению которого выполняет метод invoke
     *  класса [Unit]
     * */
    override fun startBaseHandler(millis: Long, onPostDelay: () -> Unit) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({ onPostDelay.invoke() }, millis)
    }

    /**
     *  Закрывает [handler] и сообещение объекта [baseDialog]
     * */
    override fun stopBaseHandler() {
        handler.removeCallbacksAndMessages(null)
        baseDialog.dismiss()
    }

    /**
     *  DI Kodein
     * */
    override fun provideOverridingModule() = Kodein.Module {
        bind<SensorManager>() with provider { getSystemService(Context.SENSOR_SERVICE) as SensorManager }
        bind<EnergyView>() with provider { this@EnergyActivity }
    }

    private var spendingEnergyTimer: Handler? = null
    private val handler: Handler by instance()
    private val spendSleepTimer: Handler by instance()
    private val motionSensor: SensorManager by instance()
    private val baseDialog: DialogUtils by instance()
    private val energyPresenter: EnergyPresenter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /** Клик-событие для перехода на экран статистики*/
        openStat.setOnClickListener {
            startActivity(Intent(this, StatActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        /**
         * Регистрация сенсора акселерометра. Ждет 5 сек. чтобы дать приложению запуститься
         * */
        Handler().postDelayed({
            motionSensor.registerListener(
                this,
                motionSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME
            )
        }, 5000)
    }


    /**
     * Метод приглашения к трате энергии. Показвает AlertDialog-сообщение используя объект
     * [baseDialog] класса [DialogUtils] и ставит [handler] на 2 сек. по истечению которог закрывает
     * сообщение и запускает цикл [sendInvite] заного
     * */
    override fun sendInvite() {
        if (!isFinishing){
            baseDialog.showBaseDialog("Пожалуйста потратьте энергию")
            startBaseHandler(2000) {
                baseDialog.dismiss()
                startBaseHandler(5000) { sendInvite() }
            }
        }
    }

    /**
     * Остановка всех Handlers и Сенсора акселерометра
     * */
    override fun onPause() {
        super.onPause()
        firstTime = false
        spendSleepTimer.removeCallbacksAndMessages(null)
        spendingEnergyTimer?.removeCallbacksAndMessages(null)
        spendingEnergyTimer = null
        motionSensor.unregisterListener(this)
        stopBaseHandler()
        energyPresenter.spendEnergyFail()
    }
}
