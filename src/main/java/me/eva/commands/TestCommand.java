package me.eva.commands;

import me.eva.utils.SlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@SlashCommand
public class TestCommand extends ListenerAdapter {

    public SlashCommandData init() {
        return Commands.slash("hello", "Be polite");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("hello")) {
            event.reply("Hello :3").queue();
        }
    }
}
