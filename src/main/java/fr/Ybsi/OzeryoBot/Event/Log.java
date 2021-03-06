/*
 * Decompiled with CFR 0.145.
 */
package fr.Ybsi.OzeryoBot.Event;

import fr.Ybsi.OzeryoBot.Utils.TextFileWriter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdatePermissionsEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdatePermissionsEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.*;
import net.dv8tion.jda.api.hooks.EventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Log implements EventListener {
    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof GuildMessageUpdateEvent) {
            this.onModif((GuildMessageUpdateEvent) event);
        } else if (event instanceof GuildMessageDeleteEvent) {
            this.onDelete((GuildMessageDeleteEvent) event);
        } else if (event instanceof GuildMemberJoinEvent) {
            this.onJoin((GuildMemberJoinEvent) event);
        } else if (event instanceof GuildMemberLeaveEvent) {
            this.onLeave((GuildMemberLeaveEvent) event);
        } else if (event instanceof GuildMemberRoleAddEvent) {
            this.onRoleAdd((GuildMemberRoleAddEvent) event);
        } else if (event instanceof GuildMemberRoleRemoveEvent) {
            this.onRoleRemove((GuildMemberRoleRemoveEvent) event);
        } else if (event instanceof GuildVoiceJoinEvent) {
            this.onVoiceJoin((GuildVoiceJoinEvent) event);
        } else if (event instanceof GuildVoiceLeaveEvent) {
            this.onVoiceLeave((GuildVoiceLeaveEvent) event);
        } else if (event instanceof GuildVoiceMoveEvent) {
            this.onVoiceMove((GuildVoiceMoveEvent) event);
        } else if (event instanceof GuildBanEvent) {
            this.onBan((GuildBanEvent) event);
        } else if (event instanceof GuildUnbanEvent) {
            this.onUnban((GuildUnbanEvent) event);
        } else if (event instanceof GuildMessageReactionRemoveAllEvent) {
            this.onRemoveAllEmote((GuildMessageReactionRemoveAllEvent) event);
        } else if (event instanceof GuildVoiceMuteEvent) {
            this.onmute((GuildVoiceMuteEvent) event);
        } else if (event instanceof TextChannelCreateEvent) {
            this.onTextChannelCreate((TextChannelCreateEvent) event);
        } else if (event instanceof TextChannelDeleteEvent) {
            this.onTextChannelDelete((TextChannelDeleteEvent) event);
        } else if (event instanceof VoiceChannelCreateEvent) {
            this.onVoiceChannelCreate((VoiceChannelCreateEvent) event);
        } else if (event instanceof VoiceChannelDeleteEvent) {
            this.onVoiceChannelDelete((VoiceChannelDeleteEvent) event);
        } else if (event instanceof TextChannelUpdatePermissionsEvent) {
            this.onTextChannelUpdatePermission((TextChannelUpdatePermissionsEvent) event);
        } else if (event instanceof VoiceChannelUpdatePermissionsEvent) {
            this.onVoiceChannelUpdatePermission((VoiceChannelUpdatePermissionsEvent) event);
        } else if (event instanceof RoleCreateEvent) {
            this.onRoleCreate((RoleCreateEvent) event);
        } else if (event instanceof RoleDeleteEvent) {
            this.onRoleDelete((RoleDeleteEvent) event);
        } else if (event instanceof RoleUpdateColorEvent) {
            this.onRoleColorUpdate((RoleUpdateColorEvent) event);
        } else if (event instanceof RoleUpdateNameEvent) {
            this.onRoleNameUpdate((RoleUpdateNameEvent) event);
        } else if (event instanceof RoleUpdateMentionableEvent) {
            this.onRoleMentionalbeUpdate((RoleUpdateMentionableEvent) event);
        } else if (event instanceof RoleUpdateHoistedEvent) {
            this.onRoleHoistedUpdate((RoleUpdateHoistedEvent) event);
        } else if (event instanceof RoleUpdatePositionEvent) {
            this.onRolePositionUpdate((RoleUpdatePositionEvent) event);
        } else if (event instanceof RoleUpdatePermissionsEvent) {
            this.onRolePermissionUpdate((RoleUpdatePermissionsEvent) event);
        }
    }

    private void onRolePermissionUpdate(RoleUpdatePermissionsEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le role " + event.getRole().getAsMention() + " a été modifié.\n ");
        builder.addField("Anciennes permissions", ((List) event.getOldValue()).toString(), false);
        builder.addField("Nouvelle permission", ((List) event.getNewValue()).toString(), false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRolePositionUpdate(RoleUpdatePositionEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le role " + event.getRole().getAsMention() + " a été modifié.\n ");
        builder.addField("Ancienne place", event.getOldValue().toString() + "e position",
                false);
        builder.addField("Nouvelle place", event.getNewValue().toString() + "e position",
                false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRoleHoistedUpdate(RoleUpdateHoistedEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        String mess = event.getOldValue() != false ? "separé des autres roles"
                : "Non séparé des autres roles";
        String mess2 = event.getNewValue() != false ? "separé des autres roles"
                : "Non séparé des autres roles";
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le role " + event.getRole().getAsMention() + " a été modifié.\n ");
        builder.addField("Avant", mess, false);
        builder.addField("Desormais", mess2, false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRoleMentionalbeUpdate(RoleUpdateMentionableEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        String mess = event.getOldValue() != false ? "Mentionable" : "Non-Mentionable";
        String mess2 = event.getNewValue() != false ? "Mentionable" : "Non-Mentionable";
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le role " + event.getRole().getAsMention() + " a été modifié.\n ");
        builder.addField("Avant", mess, false);
        builder.addField("Desormais", mess2, false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRoleNameUpdate(RoleUpdateNameEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le role " + event.getRole().getAsMention() + " a été modifié.\n ");
        builder.addField("Ancien nom", event.getOldValue(), false);
        builder.addField("Nouveau nom", event.getNewValue(), false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRoleColorUpdate(RoleUpdateColorEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le role " + event.getRole().getAsMention() + " \u00e0 été modifié.\n ");
        builder.addField("Ancienne couleur", event.getOldValue().toString(), false);
        builder.addField("Nouvelle couleur", event.getNewValue().toString(), false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onTextChannelUpdatePermission(TextChannelUpdatePermissionsEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Modification du role : \n" + event.getChangedRoles().get(0).getAsMention()
                + " dans le channel " + event.getChannel().getAsMention());
        builder.addField(event.getChangedRoles().get(0).getAsMention(),
                event.getChangedPermissionHolders().get(0).getPermissions().toString(), false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRoleDelete(RoleDeleteEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le role " + event.getRole().getAsMention() + " a été supprimé.\n ");
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRoleCreate(RoleCreateEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le role " + event.getRole().getAsMention() + " a été créé.\n ");
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onVoiceChannelUpdatePermission(VoiceChannelUpdatePermissionsEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Modification du role : \n" + event.getChangedRoles().get(0).getAsMention()
                + " dans le channel " + event.getChannel().getName());
        builder.addField(event.getChangedRoles().get(0).getAsMention(),
                event.getChangedPermissionHolders().get(0).getPermissions().toString(), false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le channel vocal " + event.getChannel().getName() + " \u00e0 bien été supprimé");
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le channel vocal " + event.getChannel().getName() + " \u00e0 bien été créé");
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onTextChannelDelete(TextChannelDeleteEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le channel textuel " + event.getChannel().getAsMention() + " \u00e0 bien été supprimé");
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onTextChannelCreate(TextChannelCreateEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Le channel textuel " + event.getChannel().getAsMention() + " \u00e0 bien été créé");
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onmute(GuildVoiceMuteEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription(event.getMember().getAsMention() + " a été mute dans le salon "
                + event.getVoiceState().getChannel().getName());
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRemoveAllEmote(GuildMessageReactionRemoveAllEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription(
                "Tout les emojis ont été retirés d'un message dans le channel " + event.getChannel().getAsMention());
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onUnban(GuildUnbanEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getUser().getName(), null, event.getUser().getAvatarUrl());
        builder.setDescription(event.getUser().getAsMention() + " a été ban du serveur");
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onBan(GuildBanEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getUser().getName(), null, event.getUser().getAvatarUrl());
        builder.setDescription(event.getUser().getAsMention() + " a été ban du serveur");
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onVoiceMove(GuildVoiceMoveEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getName(), null, event.getMember().getUser().getAvatarUrl());
        builder.setDescription(event.getMember().getAsMention() + " s'est deplacé du salon "
                + event.getChannelLeft().getName() + " vers le salon " + event.getChannelJoined());
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onVoiceLeave(GuildVoiceLeaveEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getName(), null, event.getMember().getUser().getAvatarUrl());
        builder.setDescription(event.getMember().getAsMention() + " a quitté un salon vocal : "
                + event.getChannelLeft().getName());
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onVoiceJoin(GuildVoiceJoinEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getName(), null, event.getMember().getUser().getAvatarUrl());
        builder.setDescription(event.getMember().getAsMention() + " a rejoint un salon vocal : "
                + event.getChannelJoined().getName());
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onModif(GuildMessageUpdateEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (event.getAuthor().isBot()) {
            return;
        }
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
        builder.setDescription("Un message a été edité dans le channel " + event.getChannel().getAsMention());
        builder.addField("**Avant**", " ??? " + event, false);
        builder.addField("**Apres**", event.getMessage().getContentDisplay(), false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onDelete(GuildMessageDeleteEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("Un message a été supprimé dans le channel " + event.getChannel().getAsMention());
        builder.addField("Message : ", event.getClass().getName(), true);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onNick(GuildMemberUpdateNicknameEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getName(), null, event.getMember().getUser().getAvatarUrl());
        builder.setDescription("Un joueur a changé de pseudo");
        builder.addField("**Ancien Pseudo**", event.getOldValue(), false);
        builder.addField("**Nouveau Pseudo**", event.getNewValue(), false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onJoin(GuildMemberJoinEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getName(), null, event.getMember().getUser().getAvatarUrl());
        builder.addField("Join", event.getUser().getName() + " a rejoint le serveur", false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onLeave(GuildMemberLeaveEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getName(), null, event.getMember().getUser().getAvatarUrl());
        builder.addField("Leave", event.getUser().getName() + " a quitté le serveur", false);
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRoleAdd(GuildMemberRoleAddEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getName(), null, event.getMember().getUser().getAvatarUrl());
        builder.setDescription(event.getUser().getAsMention() + " \u00e0 re\u00e7u le role "
                + event.getRoles().get(0).getName());
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }

    private void onRoleRemove(GuildMemberRoleRemoveEvent event) {
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Users/" + event.getMember().getUser().getId());
        TextFileWriter.folder("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId());
        String channel = TextFileWriter
                .read("/home/DiscordBot/Rasberry/données/Guild/" + event.getGuild().getId() + "/log.txt");
        if (channel == "0") {
            return;
        }
        TextChannel log = event.getGuild().getTextChannelById(channel);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getName(), null, event.getMember().getUser().getAvatarUrl());
        builder.setDescription(event.getUser().getAsMention() + " \u00e0 été retiré du role "
                + event.getRoles().get(0).getName());
        builder.setFooter(new SimpleDateFormat("dd/MM HH:mm:ss", Locale.FRANCE).format(new Date()), null);
        log.sendMessage(builder.build()).queue();
    }
}
