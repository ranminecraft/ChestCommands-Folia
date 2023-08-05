/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.chestcommands.action;

import me.filoghost.chestcommands.ChestCommands;
import me.filoghost.chestcommands.placeholder.PlaceholderString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OpCommandAction implements Action {

    private final PlaceholderString command;

    public OpCommandAction(String serializedAction) {
        command = PlaceholderString.of(serializedAction);
    }

    @Override
    public void execute(Player player) {
        Bukkit.getServer().getScheduler().runTask(ChestCommands.getInstance(),
                task -> player.chat("/" + command.getValue(player)));
    }

}
