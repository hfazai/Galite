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
package org.kopi.galite.type

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.util.Locale

/**
 * This class represents the fixed type
 */
class Fixed internal constructor(internal var value: BigDecimal) : Number(), Comparable<Fixed> {
  internal constructor(b: BigInteger) : this(BigDecimal(b))

  internal constructor(value: Long, scale: Int) : this(BigDecimal.valueOf(value, scale))

  internal constructor(d: Double) : this(BigDecimal(d))

  internal constructor(s: String) : this(BigDecimal(s))
  // ----------------------------------------------------------------------
  // DEFAULT OPERATIONS
  // ----------------------------------------------------------------------

  /**
   * add (f1 + f2) operator
   */
  operator fun plus(f: Fixed): Fixed {
    if (value.compareTo(BigDecimal.ZERO) == 0) {
      return f
    } else if (f.value.compareTo(BigDecimal.ZERO) == 0) {
      return Fixed(value)
    }
    return Fixed(value.add(f.value, MATH_CONTEXT))
  }

  /**
   * plusAssign (f1 += f2) operator
   */
  operator fun plusAssign(f: Fixed) {
    if (value.compareTo(BigDecimal.ZERO) == 0) {
      value = f.value
    } else if (f.value.compareTo(BigDecimal.ZERO) != 0) {
      value.add(f.value, MATH_CONTEXT)
    }
  }

  /**
   * divide (f1 / f2) operator
   */
  operator fun div(f: Fixed): Fixed {
    return Fixed(value.divide(f.value, DIV_CONTEXT).plus(MATH_CONTEXT))
  }

  /**
   * plusAssign (f1 /= f2) operator
   */
  operator fun divAssign(f: Fixed) {
    value = value.divide(f.value, DIV_CONTEXT).plus(MATH_CONTEXT)
  }

  /**
   * multiply (f1 * f2) operator
   */
  operator fun times(f: Fixed): Fixed {
    return Fixed(value.multiply(f.value, MATH_CONTEXT))
  }

  /**
   * timesAssign (f1 *= f2) operator
   */
  operator fun timesAssign(f: Fixed) {
    value = value.multiply(f.value, MATH_CONTEXT)
  }

  /**
   * subtract (f1 - f2) operator
   */
  operator fun minus(f: Fixed): Fixed {
    if (value.compareTo(BigDecimal.ZERO) == 0) {
      return Fixed(f.value.negate())
    } else if (f.value.compareTo(BigDecimal.ZERO) == 0) {
      return Fixed(value)
    }
    return Fixed(value.subtract(f.value, MATH_CONTEXT))
  }

  /**
   * minusAssign (f1 -= f2) operator
   */
  operator fun minusAssign(f: Fixed) {
    if (value.compareTo(BigDecimal.ZERO) == 0) {
      value = f.value.negate()
    } else if (f.value.compareTo(BigDecimal.ZERO) != 0) {
      value = value.subtract(f.value, MATH_CONTEXT)
    }
  }

  /**
   * Unary minus (-f) operator
   */
  operator fun unaryMinus(): Fixed {
    return Fixed(value.negate())
  }

  /**
   * remainder (f1 % f2) operator
   */
  operator fun rem(f: Fixed): Fixed {
    return Fixed(value.remainder(f.value, MATH_CONTEXT))
  }

  /**
   * remAssign (f1 %= f2) operator
   */
  operator fun remAssign(f: Fixed) {
    value = value.remainder(f.value, MATH_CONTEXT)
  }
  // ----------------------------------------------------------------------
  // OTHER OPERATIONS
  // ----------------------------------------------------------------------
  /**
   * setScale
   */
  fun setScale(v: Int): Fixed {
    return Fixed(value.setScale(v, BigDecimal.ROUND_HALF_UP))
  }

  /**
   * setScale
   */
  fun setScale(v: Int, d: Int): Fixed {
    return Fixed(value.setScale(v, d))
  }

  /**
   * getScale
   */
  val scale: Int
    get() = value.scale()

  /**
   * Returns the fixed as a double
   */
  override fun toDouble(): Double {
    return value.toDouble()
  }

  /**
   * Comparisons
   */
  override operator fun compareTo(other: Fixed): Int {
    return value.compareTo(other.value)
  }

  // ----------------------------------------------------------------------
  // TYPE IMPLEMENTATION
  // ----------------------------------------------------------------------
  /**
   * Compares two objects
   */
  override fun equals(other: Any?): Boolean {
    return other is Fixed &&
            value == other.value
  }

  /**
   * Format the object depending on the current language
   */
  override fun toString(): String {
    return toString(Locale.GERMAN) // !!!
  }

  /**
   * Format the object depending on the current language
   * @param    locale    the current language
   */
  fun toString(locale: Locale?): String {
    val str = value.toString()
    var pos = 0
    var dot: Int

    return buildString {
      // has minus sign ?
      if (str[0] == '-') {
        append('-')
        pos = 1
      }

      // get number of digits in front of the dot
      if (str.indexOf('.').also { dot = it } == -1) {
        if (str.indexOf(' ').also { dot = it } == -1) {        // FRACTION DOT IS SPACE
          dot = str.length
        }
      }
      if (dot - pos <= 3) {
        append(str.substring(pos, dot))
        pos = dot
      } else {
        when ((dot - pos) % 3) {
          1 -> {
            append(str.substring(pos, pos + 1))
            pos += 1
          }
          2 -> {
            append(str.substring(pos, pos + 2))
            pos += 2
          }
          0 -> {
            append(str.substring(pos, pos + 3))
            pos += 3
          }
        }
        do {
          append(".").append(str.substring(pos, pos + 3))
          pos += 3
        } while (dot - pos > 0)
      }
      if (str.length > pos) {
        append(",").append(str.substring(pos + 1))
      }
    }
  }

  /**
   * Represents the value in sql
   */
  fun toSql(): String = value.toString()

  // ----------------------------------------------------------------------
  // IMPLEMENTATION OF NUMBER
  // ----------------------------------------------------------------------
  override fun toInt(): Int {
    return value.toInt()
  }

  override fun toLong(): Long {
    return value.toLong()
  }

  override fun toShort(): Short {
    return value.toShort()
  }

  override fun toFloat(): Float {
    return value.toFloat()
  }

  override fun toByte(): Byte {
    return value.toByte()
  }

  override fun toChar(): Char {
    return value.toChar()
  }

  /**
   * The Max scale
   */
  var maxScale = -1

  companion object {
    /**
     * Parse the String arguments and return the corresponding value
     */
    fun valueOf(value: String): Fixed {
      return Fixed(value)
    }

    // ----------------------------------------------------------------------
    // DATA MEMBERS
    // ----------------------------------------------------------------------
    private val MATH_CONTEXT: MathContext = MathContext(0, RoundingMode.HALF_UP)
    private val DIV_CONTEXT: MathContext = MathContext(30, RoundingMode.HALF_UP)

    // ----------------------------------------------------------------------
    // CONSTANTS
    // ----------------------------------------------------------------------
    val DEFAULT: Fixed = Fixed(0.0)

    /**
     * Comment for `serialVersionUID`
     */
    private const val serialVersionUID = 1L
  }
}

/**
 * Creates a Fixed type from a BigDecimal
 */
fun fixed(bigDecimal: BigDecimal): Fixed = Fixed(bigDecimal)

/**
 * Creates a Fixed type from a BigInteger
 */
fun fixed(bigInteger: BigInteger): Fixed = Fixed(bigInteger)

/**
 * Creates a Fixed type from a long value and scale
 */
fun fixed(value: Long, scale: Int): Fixed = Fixed(value, scale)

/**
 * Creates a Fixed type from a Double
 */
fun fixed(double: Double): Fixed = Fixed(double)

/**
 * Creates a Fixed type from a String
 */
fun fixed(string: String): Fixed = Fixed(string)
