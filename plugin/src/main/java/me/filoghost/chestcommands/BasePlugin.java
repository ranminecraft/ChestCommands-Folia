package me.filoghost.chestcommands;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.filoghost.fcommons.EnhancedJavaPlugin;
import me.filoghost.fcommons.ExceptionUtils;
import me.filoghost.fcommons.FCommons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public abstract class BasePlugin extends EnhancedJavaPlugin {
    public BasePlugin() {
    }

    public final void onEnable() {
        try {
            FCommons.setPluginInstance(this);
            this.onCheckedEnable();
        } catch (me.filoghost.fcommons.FCommonsPlugin.PluginEnableException var2) {
            this.criticalShutdown(var2.getMessageLines(), var2.getCause());
        } catch (Throwable var3) {
            this.criticalShutdown((List)null, var3);
        }

    }

    protected abstract void onCheckedEnable() throws me.filoghost.fcommons.FCommonsPlugin.PluginEnableException;

    private void criticalShutdown(List<String> errorMessageLines, Throwable throwable) {
        this.printCriticalError(errorMessageLines, throwable);
        Bukkit.getServer().getGlobalRegionScheduler().runDelayed(this, scheduledTask -> {
            Bukkit.getConsoleSender().sendMessage(this.getFatalErrorPrefix() + "Fatal error while enabling the plugin. Check previous logs for more information.");
        }, 10L);
        this.setEnabled(false);
    }

    protected void printCriticalError(List<String> errorMessageLines, Throwable throwable) {
        List<String> output = new ArrayList();
        if (errorMessageLines != null) {
            output.add(this.getFatalErrorPrefix() + "Fatal error while enabling plugin:");
        } else {
            output.add(this.getFatalErrorPrefix() + "Fatal unexpected error while enabling plugin:");
        }

        if (errorMessageLines != null) {
            output.add("");
            output.addAll(errorMessageLines);
        }

        if (throwable != null) {
            output.add("");
            output.addAll(ExceptionUtils.getStackTraceOutputLines(throwable));
            output.add("");
        }

        output.add(this.getDescription().getName() + " has been disabled.");
        output.add("");
        Bukkit.getConsoleSender().sendMessage(String.join("\n", output));
    }

    private String getFatalErrorPrefix() {
        return ChatColor.DARK_RED + "[" + this.getDescription().getName() + "] " + ChatColor.RED;
    }

    public static class PluginEnableException extends Exception {
        private final List<String> messageLines;

        public PluginEnableException(String... message) {
            this((Throwable)null, message);
        }

        public PluginEnableException(Throwable cause, String... message) {
            super(String.join(" ", message), cause);
            this.messageLines = Arrays.asList(message);
        }

        public List<String> getMessageLines() {
            return this.messageLines;
        }
    }
}
