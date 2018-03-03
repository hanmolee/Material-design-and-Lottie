package com.material.hanmo.material_design_strudy

import android.app.Activity
import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Space
import android.widget.TextView

/**
 * Created by hanmo on 2018. 3. 2..
 */
class MainActivity : Activity(), View.OnTouchListener {

    private var buttonsContainer: ViewGroup? = null
    private var activeButton: ViewGroup? = null
    private val MAX_BUTTONS = 3

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.buttonsContainer = findViewById(R.id.buttonsContainer) as ViewGroup

        val buttonsSpacing = resources.getDimension(R.dimen.activity_horizontal_margin).toInt()
        val buttonSize = resources.getDimension(R.dimen.button_size).toInt()
        val circularOutline = Outline()
        circularOutline.setOval(0, 0, buttonSize, buttonSize)

        for (i in 0 until MAX_BUTTONS) {
            val buttonHost = layoutInflater.inflate(R.layout.circularbutton_layout, buttonsContainer, false) as ViewGroup
            val button = buttonHost.getChildAt(0) as TextView

            button.text = "Test $i"

            buttonHost.setOnTouchListener(this)
            buttonsContainer!!.addView(buttonHost)

            //Add margin between buttons manually
            if (i != MAX_BUTTONS - 1) {
                buttonsContainer!!.addView(Space(this), ViewGroup.LayoutParams(buttonsSpacing, buttonSize))
            }
        }
        selectButton(buttonsContainer!!.getChildAt(0) as ViewGroup, false)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun selectButton(buttonHost: ViewGroup, reveal: Boolean) {
        selectButton(buttonHost, reveal, buttonHost.width, buttonHost.height)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun selectButton(buttonHost: ViewGroup, reveal: Boolean, startX: Int, startY: Int) {
        if (buttonHost === activeButton) {
            return
        }

        if (activeButton != null) {
            activeButton!!.setSelected(false)
            activeButton = null
        }

        activeButton = buttonHost
        activeButton!!.setSelected(true)

        val button = activeButton!!.getChildAt(0)

        if (reveal) {
            ViewAnimationUtils.createCircularReveal(button,
                    startX,
                    startY,
                    0f,
                    button.height.toFloat()).start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> (view as ViewGroup).getChildAt(0).background.setHotspot(motionEvent.x, motionEvent.y)
            MotionEvent.ACTION_UP -> selectButton(view as ViewGroup, true, motionEvent.x.toInt(), motionEvent.y.toInt())
        }
        return false
    }

}
