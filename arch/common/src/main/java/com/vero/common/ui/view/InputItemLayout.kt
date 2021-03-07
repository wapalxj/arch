package com.vero.common.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.vero.common.R

class InputItemLayout : LinearLayout {
    private lateinit var topLine: Line
    private lateinit var bottomLine: Line
    private lateinit var titleView: TextView
    private lateinit var editText: EditText

    var topLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bottomLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {

        orientation = LinearLayout.HORIZONTAL
        val array = context.obtainStyledAttributes(attrs, R.styleable.InputItemLayout)


        //解析title资源属性
        val titleStyleId = array.getResourceId(R.styleable.InputItemLayout_titleTextAppearance, 0)
        val title = array.getString(R.styleable.InputItemLayout_title)
        parseTitleStyle(titleStyleId, title)


        //解析Input资源属性
        val inputStyleId = array.getResourceId(R.styleable.InputItemLayout_inputTextAppearance, 0)
        val hint = array.getString(R.styleable.InputItemLayout_hint)
        val inputType = array.getInteger(R.styleable.InputItemLayout_inputType, 0)
        parseInputStyle(inputStyleId, hint, inputType)


        //解析上下分割线
        val topLineStyleId = array.getResourceId(R.styleable.InputItemLayout_topLineAppearance, 0)
        val bottomLineStyleId = array.getResourceId(R.styleable.InputItemLayout_bottomLineAppearance, 0)
        topLine = parseLineStyle(topLineStyleId)
        bottomLine = parseLineStyle(bottomLineStyleId)

        if (topLine.enable) {
            topLinePaint.color = topLine.color
            topLinePaint.style = Paint.Style.FILL_AND_STROKE
            topLinePaint.strokeWidth = topLine.height.toFloat()
        }
        if (bottomLine.enable) {
            bottomLinePaint.color = bottomLine.color
            bottomLinePaint.style = Paint.Style.FILL_AND_STROKE
            bottomLinePaint.strokeWidth = bottomLine.height.toFloat()
        }
        array.recycle()
    }

    private fun parseLineStyle(lineStyleId: Int): Line {
        var line = Line()
        val array = context.obtainStyledAttributes(lineStyleId, R.styleable.lineAppearance)
        line.color = array.getColor(R.styleable.lineAppearance_color, resources.getColor(R.color.color_d1d2))
        line.height = array.getDimensionPixelOffset(R.styleable.lineAppearance_height, 0)
        line.leftMargin = array.getDimensionPixelOffset(R.styleable.lineAppearance_leftMargin, 0)
        line.rightMargin = array.getDimensionPixelOffset(R.styleable.lineAppearance_rightMargin, 0)
        line.enable = array.getBoolean(R.styleable.lineAppearance_enable, true)
        array.recycle()

        return line
    }

    private fun parseInputStyle(inputStyleId: Int, hint: String?, inputType: Int) {
        val array = context.obtainStyledAttributes(inputStyleId, R.styleable.inputTextAppearance)
        val hintColor = array.getColor(R.styleable.inputTextAppearance_hintColor, resources.getColor(R.color.color_d1d2))
        val inputColor = array.getColor(R.styleable.inputTextAppearance_inputColor, resources.getColor(R.color.color_565))
        val textSize = array.getDimensionPixelSize(R.styleable.inputTextAppearance_textSize, applyUnit(TypedValue.COMPLEX_UNIT_SP, 14f))


        editText = EditText(context)
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        layoutParams.weight = 1f
        editText.layoutParams = layoutParams
        editText.hint = hint
        editText.setHintTextColor(hintColor)
        editText.setTextColor(inputColor)
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())

        editText.setBackgroundColor(Color.TRANSPARENT)
        editText.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL

        when (inputType) {
            0 -> {
                //text
                editText.inputType = InputType.TYPE_CLASS_TEXT
            }
            1 -> {
                //password
                editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or (InputType.TYPE_CLASS_TEXT)
            }
            2 -> {
                //number
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
        addView(editText)
        array.recycle()

    }

    private fun parseTitleStyle(titleStyleId: Int, title: String?) {
        val array = context.obtainStyledAttributes(titleStyleId, R.styleable.titleTextAppearance)
        val titleColor = array.getColor(R.styleable.titleTextAppearance_titleColor, resources.getColor(R.color.color_565))
        val titleMinWidth = array.getDimensionPixelSize(R.styleable.titleTextAppearance_minWidth, 0)
        val titleSize = array.getDimensionPixelSize(R.styleable.titleTextAppearance_titleSize, applyUnit(TypedValue.COMPLEX_UNIT_SP, 15f))

        titleView = TextView(context)

        titleView.setTextColor(titleSize)
        titleView.setTextColor(titleColor)
        titleView.minWidth = titleMinWidth
        titleView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)

        titleView.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        titleView.text = title
        addView(titleView)
        array.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (topLine.enable) {
            canvas?.drawLine(
                    topLine.leftMargin.toFloat(),
                    0f,
                    measuredWidth - topLine.rightMargin.toFloat(),
                    0f,
                    topLinePaint)
        }
        if (bottomLine.enable) {
            canvas?.drawLine(
                    bottomLine.leftMargin.toFloat(),
                    measuredHeight - bottomLine.height.toFloat(),
                    measuredWidth - bottomLine.rightMargin.toFloat(),
                    measuredHeight - bottomLine.height.toFloat(),
                    bottomLinePaint)
        }
    }

    private fun applyUnit(unit: Int, value: Float): Int {
        return TypedValue.applyDimension(unit, value, resources.displayMetrics).toInt()
    }

    inner class Line {
        var color = 0
        var height = 0
        var leftMargin = 0
        var rightMargin = 0
        var enable = true
    }


    fun getTitleView(): TextView {
        return titleView
    }

    fun getEditText(): EditText {
        return editText
    }
}