package me.eva;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import me.eva.utils.Initialize;
import me.eva.utils.SlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.lang.reflect.InvocationTargetException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ColonThreeBot {

    public static final String TEST_GUILD = "847132139777818644";
    public static final String PRODUCTION_GUILD = "1147726795655872614";
    public static Dotenv dotenv;


    public static void main(String[] args) throws InterruptedException {
        dotenv = Dotenv.load();
        JDABuilder builder = JDABuilder.createDefault(dotenv.get("TOKEN"));

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.watching("cute ppl chat :3"));
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS);

        System.out.println("LOGGING IN");
        JDA jda = builder.build().awaitReady();

        loadSlashCommands(jda);
        executeStartupMethods();

    }

    private static void loadSlashCommands(JDA jda) {
        try(ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
            ClassInfoList classInfos = result.getClassesWithAnnotation(SlashCommand.class.getName());
            for (Class<?> clazz : classInfos.loadClasses()) {
                Object instance = clazz.getDeclaredConstructor().newInstance();

                SlashCommand annotation = clazz.getAnnotation(SlashCommand.class);
                SlashCommandData data = (SlashCommandData) clazz.getMethod(annotation.methodName())
                        .invoke(instance);

                jda.getGuildById(TEST_GUILD).updateCommands().addCommands(data).queue(commands -> {
                    commands.forEach(c -> System.out.println(c.getName()));
                }, throwable -> throwable.printStackTrace(System.out));
                jda.addEventListener(instance);

                if(!annotation.isTestOnly()) {
                    //jda.getGuildById(PRODUCTION_GUILD).updateCommands().addCommands(data).queue();
                }
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void executeStartupMethods() {
        try(ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
            ClassInfoList classInfos = result.getClassesWithAnnotation(Initialize.class.getName());
            for (Class<?> clazz : classInfos.loadClasses()) {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                Initialize annotation = clazz.getAnnotation(Initialize.class);

                clazz.getMethod(annotation.methodName()).invoke(instance);
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }

}