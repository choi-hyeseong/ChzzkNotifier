package org.example.command.list

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.example.command.AbstractCommand
import org.example.streamer.manager.data.CacheStreamerManager

/**
 * 스트리머 목록 커맨드
 */
class StreamerListCommand(private val cacheStreamerManager: CacheStreamerManager) : AbstractCommand() {

    override fun onCommand(event: MessageReceivedEvent) {
        event.channel.sendMessageEmbeds(buildEmbed()).queue()
    }

    private fun buildEmbed() : MessageEmbed {
        val builder = EmbedBuilder()
        builder.setTitle("스트리머 목록")
        cacheStreamerManager.getStreamerInfos().forEach {
            builder.addField(MessageEmbed.Field("${it.index + 1}. ${it.name}", "", false))
        }
        return builder.build()
    }
}