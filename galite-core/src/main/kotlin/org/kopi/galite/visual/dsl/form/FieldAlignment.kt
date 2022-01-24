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
package org.kopi.galite.visual.dsl.form

import org.kopi.galite.visual.form.VConstants

/**
 * The field alignment is used to define the localization of the field's content inside the field.
 */
enum class FieldAlignment(val value: Int) {
  /**
   * The value is centered in the field
   */
  CENTER(VConstants.ALG_CENTER),

  /**
   * The value is displayed at the left inside the field
   */
  LEFT(VConstants.ALG_LEFT),

  /**
   * The value is displayed at the right inside the field
   */
  RIGHT(VConstants.ALG_RIGHT),
}
