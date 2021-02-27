package com.nek.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.nek.sunset.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    private val animatorSet = AnimatorSet()
    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky) }
    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky) }
    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky) }
    private val sunColor: Int by lazy {
        ContextCompat.getColor(this, R.color.bright_sun) }
    private val duskSunColor: Int by lazy {
        ContextCompat.getColor(this, R.color.dusk_sun) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.scene.setOnClickListener {
            if (animatorSet.isStarted) {
                //trigger
                animatorSet.currentPlayTime = animatorSet.currentPlayTime
                animatorSet.reverse()
            }
            else
                startAnimation()
        }

    }

    private fun startAnimation() {

        val sunYStart = b.sun.top.toFloat()
        val sunYEnd = b.sky.height.toFloat()
        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(b.sky, "backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val heightAnimator = ObjectAnimator
            .ofFloat(b.sun, "y", sunYStart, sunYEnd)
            .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

        val nightSkyAnimator = ObjectAnimator
            .ofInt(b.sky, "backgroundColor", sunsetSkyColor, nightSkyColor)
            .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        val sunPulseAnimator = ObjectAnimator
            .ofInt(b.sun, "foregroundColor", sunColor, duskSunColor)
            .setDuration(250)
        sunPulseAnimator.setEvaluator(ArgbEvaluator())
        sunPulseAnimator.repeatCount = (3000+1500)/250 + 1

        animatorSet.play(heightAnimator)
            .with(sunsetSkyAnimator)
            .with(sunPulseAnimator)
            .before(nightSkyAnimator)
        animatorSet.start()
    }


}
