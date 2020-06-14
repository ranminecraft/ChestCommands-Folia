/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.filoghost.chestcommands.action;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.filoghost.chestcommands.bridge.BarAPIBridge;
import me.filoghost.chestcommands.parser.ParseException;
import me.filoghost.chestcommands.parser.NumberParser;
import me.filoghost.chestcommands.util.FormatUtils;
import me.filoghost.chestcommands.variable.RelativeString;

public class DragonBarAction extends Action {

	private RelativeString message;
	private int seconds;

	public DragonBarAction(String serialiazedAction) {
		seconds = 1;
		String message = serialiazedAction;
		
		String[] split = serialiazedAction.split("\\|", 2); // Max of 2 pieces
		if (split.length > 1) {
			try {
				seconds =  NumberParser.getStrictlyPositiveInteger(split[0].trim());
				message = split[1].trim();
			} catch (ParseException ex) {
				disable(ChatColor.RED + "Invalid dragon bar time: " + split[0]);
				return;
			}
		}

		this.message = RelativeString.of(FormatUtils.addColors(message));
	}

	@Override
	protected void executeInner(Player player) {
		if (BarAPIBridge.hasValidPlugin()) {
			BarAPIBridge.setMessage(player, message.getValue(player), seconds);
		}
	}

}
