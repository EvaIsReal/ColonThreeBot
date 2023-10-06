package me.eva.commands;

import me.eva.database.Database;
import me.eva.database.Userdata;
import me.eva.utils.SlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;

@SlashCommand
public class NewUserdataCommand extends ListenerAdapter {

    public SlashCommandData init() {
        return Commands.slash("write-user", "Writes a user to the database")
                .addOption(OptionType.USER, "user", "User to write to DB", true)
                ;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("write-user")) {
            Userdata userdata = e.getOption("user", optionMapping ->
                    Userdata.of(optionMapping.getAsUser().getId(), System.currentTimeMillis()));
            Database.writeUser(userdata);

            e.reply(MessageCreateData.fromEmbeds(
                    new EmbedBuilder()
                            .setTitle("Wrote user to database")
                            .setDescription("Wrote new user to database with following values:")
                            .addField("id", userdata.getId(), false)
                            .addField("joined_at", userdata.getJoinedAt(), false)
                            .setFooter("Command issued by " + e.getUser().getName())
                            .setColor(new Color(225, 62, 250))
                            .setThumbnail(e.getGuild().getIconUrl())
                            .build()
            )).queue();
        }
    }
}
