package com.ombremoon.archetypesmages.event;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.archetypesmages.Constants;
import com.ombremoon.archetypesmages.util.ModFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.concurrent.CopyOnWriteArrayList;

public class FirstSpawnEvent {
    public static CopyOnWriteArrayList<Pair<Integer, Runnable>> scheduledRunnables = new CopyOnWriteArrayList<Pair<Integer, Runnable>>();

    public static void onSpawn(Level level, Entity entity) {
        if (level.isClientSide) return;
        if (!(entity instanceof Player)) return;
        var player = (Player) entity;
        ModFunctions.enqueueCollectiveTask(level.getServer(), () -> {
            if (ModFunctions.isJoiningWorldForTheFirstTime(player, Constants.MOD_ID, false)) {
//                player.sendSystemMessage(Component.literal("[TugkanDeMans Elemental Swords]: ").withStyle(ChatFormatting.GREEN).append(Component.literal("Thanks for installing my mod! Check out my Patreon if you want to support the mod!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
//                player.sendSystemMessage(Component.literal("[TugkanDeMans Elemental Swords]: ").withStyle(ChatFormatting.GREEN).append(Component.literal("https://www.patreon.com/TugkanDeMan/").withStyle(ChatFormatting.RESET).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.patreon.com/TugkanDeMan/"))).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.WHITE)));
            }
        }, 5);
    }

    public static void onServerTick(MinecraftServer minecraftServer) {
        var serverTickCount = minecraftServer.getTickCount();
        for (var pair : scheduledRunnables)
            if (pair.getFirst() <= serverTickCount) {
                minecraftServer.tell(new TickTask(minecraftServer.getTickCount(), pair.getSecond()));
                scheduledRunnables.remove(pair);
            }
    }
}
