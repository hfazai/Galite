/*
 * Copyright (c) 2013-2022 kopiLeft Services SARL, Tunis TN
 * Copyright (c) 1990-2022 kopiRight Managed Solutions GmbH, Wien AT
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
package org.kopi.galite.visual.ui.vaadin.form

import java.awt.event.KeyEvent

import org.kopi.galite.visual.form.UChartLabel
import org.kopi.galite.visual.form.ULabel
import org.kopi.galite.visual.form.VBlock
import org.kopi.galite.visual.form.VConstants
import org.kopi.galite.visual.form.VFieldUI
import org.kopi.galite.visual.ui.vaadin.base.BackgroundThreadHandler.access
import org.kopi.galite.visual.ui.vaadin.base.Utils
import org.kopi.galite.visual.ui.vaadin.grid.GridEditorLabel
import org.kopi.galite.visual.VActor

import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.UI

/**
 * The editor label used as grid component header.
 */
class DGridEditorLabel(text: String?,
                       help: String?,
                       val fieldIndex: Int? = null,
                       val model: VBlock.OrderModel? = null)
  : GridEditorLabel(), ULabel, UChartLabel {

  //---------------------------------------------------
  // DATA MEMBERS
  //---------------------------------------------------
  private var tooltip: String? = null

  init {
    init(text, help)
  }

  //---------------------------------------------------
  // IMPLEMENTATION
  //---------------------------------------------------
  override fun init(text: String?, tooltip: String?) {
    this.tooltip = tooltip
    access(currentUI) {
      add(text.orEmpty())
      setDescription(Utils.createTooltip(tooltip))
    }
  }

  private fun setDescription(tooltip: String) {
    element.setProperty("title", tooltip)
  }

  override fun orderChanged() {}

  override fun repaint() {}

  /**
   * Updates the label content.
   * @param model The field model.
   * @param row The field row.
   */
  fun update(model: VFieldUI, row: Int) {
    access(currentUI) {
      if (model.model.getAccess(row) == VConstants.ACS_SKIPPED) {
        // Only show base help on a skipped field
        // Actors are not shown since they are not active.
        setDescription(Utils.createTooltip(tooltip))
      } else {
        setDescription(Utils.createTooltip(buildDescription(model, tooltip)))
      }
    }
  }

  /**
   * Builds full field description.
   * @param model The field model.
   * @param tooltip The initial field tooltip.
   * @return The full field description.
   */
  fun buildDescription(model: VFieldUI, tooltip: String?): String {
    var description: String
    val commands = model.getAllCommands()
    val tooltip = tooltip.orEmpty() // avoid writing null in help tooltip.

    description = tooltip
    if (commands.isNotEmpty()) {
      description = "<html>$tooltip"
      for (i in commands.indices) {
        if (commands[i].actor != null) {
          if (description.trim { it <= ' ' }.isNotEmpty()) {
            description += "<br>"
          }
          description += getDescription(commands[i].actor!!)
        }
      }
    }
    return description
  }

  var currentUI: UI? = null

  override fun onAttach(attachEvent: AttachEvent) {
    currentUI = attachEvent.ui
  }

  companion object {
    /**
     * Creates the actor description.
     * @param actor The actor model.
     * @return The actor description.
     */
    private fun getDescription(actor: VActor): String? {
      return if (actor.acceleratorKey > 0) {
        if (actor.acceleratorModifier == 0) {
          actor.menuItem + " [" + KeyEvent.getKeyText(actor.acceleratorKey) + "]"
        } else {
          actor.menuItem + " [" + KeyEvent.getKeyModifiersText(actor.acceleratorModifier) +
                  "-" + KeyEvent.getKeyText(actor.acceleratorKey) + "]"
        }
      } else {
        actor.menuItem
      }
    }
  }
}
