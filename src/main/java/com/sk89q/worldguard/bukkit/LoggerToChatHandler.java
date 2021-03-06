/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldguard.bukkit;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Sends all logger messages to a player.
 *
 * @author sk89q
 */
public class LoggerToChatHandler extends Handler {
    /**
     * Player.
     */
    private CommandSender player;

    /**
     * Construct the object.
     *
     * @param player
     */
    public LoggerToChatHandler(CommandSender player) {
        this.player = player;
    }

    /**
     * Close the handler.
     */
    @Override
    public void close() {
    }

    /**
     * Flush the output.
     */
    @Override
    public void flush() {
    }

    /**
     * Publish a log record.
     */
    @Override
    public void publish(LogRecord record) {
        player.sendMessage(ChatColor.GRAY + record.getLevel().getName() + ": "
                + ChatColor.WHITE + record.getMessage());
    }
}
