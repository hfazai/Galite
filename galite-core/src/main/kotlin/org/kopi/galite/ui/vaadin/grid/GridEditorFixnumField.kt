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
package org.kopi.galite.ui.vaadin.grid

/**
 * Implementation of decimal grid editor field
 * @param minValue The minimum value to be accepted by the field.
 * @param maxValue The maximum value to be accepted by the field.
 * @param maxScale The max scale to be used with this field if it is a fixnum one.
 * @param fraction Is this field a fraction one ?
 */
class GridEditorFixnumField(
        width: Int,
        var minValue: Double,
        var maxValue: Double,
        var maxScale: Int,
        var fraction: Boolean
) : GridEditorTextField(width) {

  /**
   * The current scale of the decimal field.
   */
  var scale = maxScale
}
