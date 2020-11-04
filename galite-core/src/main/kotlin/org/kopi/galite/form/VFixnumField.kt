/*
 * Copyright (c) 2013-2020 kopiLeft Services SARL, Tunis TN
 * Copyright (c) 1990-2020 kopiRight Managed Solutions GmbH, Wien AT
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

package org.kopi.galite.form

import org.kopi.galite.type.Fixed

//TODO
class VFixnumField(width: Int, height: Int) : VField(width, height){

  companion object {
    /**
     * Computes the the width of a fixnum field : FIXNUM(digits, scale)
     *
     * @param     digits          the number of total digits.
     * @param     scale           the number of digits representing the fractional part.
     * @param     minVal          the minimal value the fixnum field can get.
     * @param     maxVal          the maximal value the fixnum field can get.
     */
    fun computeWidth(digits: Int, scale: Int, minVal: Fixed?, maxVal: Fixed?): Int {
      TODO()
    }
  }
}
