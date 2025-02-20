/*
 * Copyright (c) 2013-2022 kopiLeft Services SARL, Tunis TN
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.kopi.galite.visual.dsl.chart

import org.kopi.galite.visual.chart.CConstants
import org.kopi.galite.visual.chart.VMeasure
import org.kopi.galite.visual.domain.Domain
import org.kopi.galite.visual.dsl.common.Action
import org.kopi.galite.visual.dsl.common.ChartTrigger
import org.kopi.galite.visual.dsl.common.Trigger
import org.kopi.galite.visual.Color
import org.kopi.galite.visual.VColor

/**
 * Represents a measure used to store numeric values in chart.
 *
 * @param domain dimension domain.
 */
open class ChartMeasure<T>(domain: Domain<T>, override val source: String = "") : ChartField<T>(domain) where T : Comparable<T>?, T : Number? {

  /** Measure's color in chart */
  lateinit var color: Color

  /** Color trigger */
  internal var colorTrigger: Trigger? = null

  /**
   * Called to specify a measure color. The trigger should return a [VColor] instance.
   *
   * @param method    The method to execute when compute trigger is executed.
   */
  fun color(method: () -> VColor): ChartTrigger {
    val fieldAction = Action(null, method)
    return ChartTrigger(0L or (1L shl CConstants.TRG_COLOR), fieldAction).also {
      colorTrigger = it
    }
  }

  val model: VMeasure
    get() {
      val color: VColor? = if (colorTrigger != null) {
        colorTrigger!!.action.method() as VColor
      } else {
        null
      }

      return domain.buildMeasureModel(this, color).also {
        if (ident != "") {
          it.label = label
          it.help = help
        }
      }
    }
}
