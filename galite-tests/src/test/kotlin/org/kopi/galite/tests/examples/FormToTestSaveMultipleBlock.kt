/*
 * Copyright (c) 2013-2020 kopiLeft Services SARL, Tunis TN
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
package org.kopi.galite.tests.examples

import java.util.Locale

import org.jetbrains.exposed.sql.transactions.transaction
import org.kopi.galite.demo.Application
import org.kopi.galite.domain.INT
import org.kopi.galite.domain.STRING
import org.kopi.galite.form.VConstants
import org.kopi.galite.form.dsl.DictionaryForm
import org.kopi.galite.form.dsl.FormBlock
import org.kopi.galite.form.dsl.Key
import org.kopi.galite.visual.VExecFailedException

class FormToTestSaveMultipleBlock : DictionaryForm() {
  override val locale = Locale.UK

  override val title = "Training Form"
  val action = menu("Action")
  val autoFill = actor(
    ident = "Autofill",
    menu = action,
    label = "Autofill",
    help = "Autofill",
  )
  val saveBlock = actor(
    ident = "saveBlock",
    menu = action,
    label = "Save Block",
    help = " Save Block",
  ) {
    key = Key.F9
    icon = "save"
  }
  val list = actor(
    ident = "list",
    menu = action,
    label = "list",
    help = "List data",
  ) {
    key = Key.F5
    icon = "list"
  }
  val block = insertBlock(Trainee())
  val multipleBlock = insertBlock(Centers())

  inner class Trainee: FormBlock(1, 1, "Training") {
    val t = table(Training)

    val trainingID = visit(domain = INT(25), position = at(1, 1)) {
      label = "training ID"
      help = "training ID"
      columns(t.id) {
        priority = 1
      }
    }

    init {
      trigger(POSTQRY) {
        multipleBlock.load()
      }

      command(item = list) {
        action = {
          transaction {
            recursiveQuery()
          }
        }
      }
    }
  }

  inner class Centers : FormBlock(20, 20, "Centers") {
    val c = table(Center)

    val centerId = hidden(domain = INT(20)) {
      label = "center id"
      help = "The Center id"
      columns(c.id)
    }
    val ts = hidden(domain = INT(20)) {
      label = "ts"
      value = 0
      columns(c.ts)
    }
    val uc = hidden(domain = INT(20)) {
      label = "uc"
      value = 0
      columns(c.uc)
    }
    val trainingId = visit(domain = INT(20), position = at(1, 1)) {
      label = "training id"
      help = "The training id"
      columns(c.refTraining)
    }
    val centerName = visit(domain = STRING(20), position = at(1, 1)) {
      label = "center name"
      help = "center name"
      columns(c.centerName)
    }
    val address = visit(domain = STRING(20), position = at(1, 2)) {
      label = "address"
      help = "address"
      columns(c.address)
    }
    val mail = visit(domain = STRING(20), position = at(1, 3)) {
      label = "mail"
      help = "mail"
      columns(c.mail)
    }

    init {
      border = VConstants.BRD_LINE

      command(item = saveBlock) {
        action = {
          val b = vBlock
          val rec: Int = b.activeRecord

          b.validate()

          if (!b.isFilled()) {
            b.currentRecord = 0
            throw VExecFailedException()
          }

          transaction {
            b.save()
          }

          b.form.gotoBlock(b)
          b.gotoRecord(if (b.isRecordFilled(rec)) rec + 1 else rec)
        }
      }
    }
  }
}

fun main() {
  Application.runForm(formName = FormToTestSaveMultipleBlock())
}
