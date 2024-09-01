package org.example.command.list

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.example.command.SubCommand
import org.example.streamer.manager.StreamerInfoManager

/**
 * 스트리머 목록 커맨드
 */
class StreamerListCommand(private val streamerInfoManager: StreamerInfoManager) : SubCommand {
    override fun invoke(event: MessageReceivedEvent, args: List<String>) {
        val builder = EmbedBuilder()
        builder.setTitle("스트리머 목록")
        streamerInfoManager.getStreamerInfos().forEach {
            builder.addField(MessageEmbed.Field("${it.index - 1}. ${it.name}", null, false))
        }
        event.channel.sendMessageEmbeds(builder.build()).queue()
    }
}