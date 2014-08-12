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

package com.sk89q.worldguard.bukkit.event.entity;

import com.sk89q.worldguard.util.cause.Cause;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fired when an entity is interacted with.
 */
public class UseEntityEvent extends AbstractEntityEvent {

    private static final HandlerList handlers = new HandlerList();

    /**
     * Create a new instance.
     *
     * @param originalEvent the original event
     * @param causes a list of causes, where the originating causes are at the beginning
     * @param target the target entity being affected
     */
    public UseEntityEvent(Event originalEvent, List<? extends Cause<?>> causes, Entity target) {
        super(originalEvent, causes, checkNotNull(target));
    }

    @Override
    @Nonnull
    public Entity getEntity() {
        return super.getEntity();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}