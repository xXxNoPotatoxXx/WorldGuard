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

package com.sk89q.worldguard.internal.listener;

import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.cause.Causes;
import com.sk89q.worldguard.internal.event.ItemInteractEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

/**
 * Handles blocked potions.
 */
public class BlockedPotionsListener extends AbstractListener {

    /**
     * Construct the listener.
     *
     * @param plugin an instance of WorldGuardPlugin
     */
    public BlockedPotionsListener(WorldGuardPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onItemInteract(ItemInteractEvent event) {
        // We only care about player caused events
        if (!Causes.mayInvolvePlayer(event.getCauses())) {
            return;
        }

        ConfigurationManager cfg = getPlugin().getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getWorld());
        ItemStack item = event.getItemStack();

        // Only block potions via PotionSplashEvent if "over reliable potion
        // blocking" is enabled
        if (!wcfg.blockPotionsAlways && event.getOriginalEvent() instanceof PotionSplashEvent) {
            return;
        }

        // We only care about portions
        if (item.getType() != Material.POTION || BukkitUtil.isWaterPotion(item)) {
            return;
        }

        if (!wcfg.blockPotions.isEmpty()) {
            PotionEffect blockedEffect = null;

            Potion potion = Potion.fromDamage(BukkitUtil.getPotionEffectBits(item));

            // Find the first blocked effect
            for (PotionEffect effect : potion.getEffects()) {
                if (wcfg.blockPotions.contains(effect.getType())) {
                    blockedEffect = effect;
                    break;
                }
            }

            if (blockedEffect != null) {
                Player player = Causes.getInvolvedPlayer(event.getCauses());

                if (player != null) {
                    if (getPlugin().hasPermission(player, "worldguard.override.potions")) {
                        if (potion.isSplash() && wcfg.blockPotionsAlways) {
                            player.sendMessage(ChatColor.RED + "Sorry, potions with " +
                                    blockedEffect.getType().getName() + " can't be thrown, " +
                                    "even if you have a permission to bypass it, " +
                                    "due to limitations (and because overly-reliable potion blocking is on).");
                            event.setCancelled(true);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Sorry, potions with "
                                + blockedEffect.getType().getName() + " are presently disabled.");
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

}
