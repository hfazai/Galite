package org.kopi.galite.tests.chart

import org.junit.Test
import org.kopi.galite.visual.chart.*
import org.kopi.galite.visual.domain.*
import org.kopi.galite.visual.dsl.chart.Chart
import org.kopi.galite.visual.type.Decimal
import org.kopi.galite.visual.visual.Color
import org.kopi.galite.visual.visual.VColor
import java.util.*
import kotlin.test.assertEquals

class VMeasureTests {
    val chart =  TestChart()

    @Test
    fun vDecimalCodeMeasure(){
        val vDecimalCodeMeasure = chart.decimalCodeMeasure.model as VDecimalCodeMeasure
        assertEquals("Decimal code measure",vDecimalCodeMeasure.label)
    }
    @Test
    fun vDecimalMeasure(){
        val vDecimalMeasure = chart.decimalMeasure.model as VDecimalMeasure
        assertEquals("Decimal measure",vDecimalMeasure.label)
    }
    @Test
    fun vIntegerCodeMeasure(){
        val vIntegerCodeMeasure = chart.integerCodeMeasure.model as VIntegerCodeMeasure
        assertEquals("Integer code measure",vIntegerCodeMeasure.label)
    }
    @Test
    fun vIntegerMeasure(){
        val vIntegerMeasure = chart.integerMeasure.model as VIntegerMeasure
        assertEquals("Integer measure", vIntegerMeasure.label)
    }
}

object IntegerCode : CodeDomain<Int>() {
    init {
        "OK" keyOf 1
        "NO" keyOf 0
    }
}

object DecimalCode : CodeDomain<Decimal>() {
    init {
        "OK" keyOf Decimal(1.0)
        "NO" keyOf Decimal(0.0)
    }
}

class TestChart: Chart() {
    override val locale = Locale.UK
    override val title = "Test Chart for measures"
    override val help = "This chart presents a test chart"

    val decimalMeasure = measure(DECIMAL(width = 10, scale = 5)) {
        label = "Decimal measure"
    }

    val integerMeasure = measure(INT(10)) {
        label = "Integer measure"
    }

    val decimalCodeMeasure = measure(DecimalCode){
        label = "Decimal code measure"
    }

    val integerCodeMeasure = measure(IntegerCode){
        label = "Integer code measure"
    }

    val integerDimension = dimension(STRING(10)){
       label = "Integer dimension"
    }
    val decimalDimension = dimension(STRING(10)){
        label = "Decimal dimension"
    }

    val type = trigger(CHARTTYPE) {
        VChartType.BAR
    }

    init {
        integerDimension.add("First Integer"){
            this[integerMeasure] = 15
            this[integerCodeMeasure] = 1
        }

        decimalDimension.add("First Decimal"){
            this[decimalMeasure] = Decimal(15.0)
            this[decimalCodeMeasure] = Decimal(0.0)
        }
    }
}