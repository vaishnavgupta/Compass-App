package com.example.compassapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() ,SensorEventListener {
    var sensor:Sensor?=null
    private lateinit var sensorManager: SensorManager
    private lateinit var compassImage:ImageView
    private lateinit var rotationText:TextView
    var currentDegree=0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        compassImage=findViewById(R.id.imageView)
        rotationText=findViewById(R.id.textView)

    }

    override fun onSensorChanged(event: SensorEvent?) {
        //action to be performed when sensor is changed
        var degree=Math.round(event!!.values[0])
        rotationText.text=degree.toString()+" degrees"
        var rotationAnimation=RotateAnimation(currentDegree,(-degree).toFloat(),Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        rotationAnimation.duration=210
        rotationAnimation.fillAfter=true
        compassImage.startAnimation(rotationAnimation)
        currentDegree=(-degree).toFloat()

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}