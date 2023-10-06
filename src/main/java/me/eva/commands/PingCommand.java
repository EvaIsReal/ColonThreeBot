package me.eva.commands;

import me.eva.database.Database;
import me.eva.database.Userdata;
import me.eva.utils.SlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@SlashCommand
public class PingCommand extends ListenerAdapter {

    public SlashCommandData init() {
        return Commands.slash("ping", "Says \"Haiii\"")

                ;
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("ping")) {
            e.reply("Haiii, <@" + e.getUser().getId() + ">").queue();
            Database.writeUser(Userdata.of(e.getUser().getId(), System.currentTimeMillis()));
        }
    }
}
