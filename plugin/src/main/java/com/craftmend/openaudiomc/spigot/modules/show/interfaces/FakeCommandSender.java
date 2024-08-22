package com.craftmend.openaudiomc.spigot.modules.show.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Proxy;
import java.util.Set;

public abstract interface FakeCommandSender extends CommandSender {

    // create a proxy class for a command sender, where getWorld will return a given world and isOp will return a given boolean
    static FakeCommandSender createCommandSenderProxy(World world) {
        FakeCommandSender sender = (FakeCommandSender) Proxy.newProxyInstance(
                FakeCommandSender.class.getClassLoader(),
                new Class[]{FakeCommandSender.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("getWorld")) {
                        return world;
                    } else if (method.getName().equals("isOp")) {
                        return true;
                    } else {
                        throw new UnsupportedOperationException("Method " + method.getName() + " is not supported");
                    }
                }
        );
        return sender;
    }

    World getWorld();

}
