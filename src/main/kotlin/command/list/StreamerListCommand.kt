package org.example.command.list

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.example.command.AbstractCommand
import org.example.notifier.embed.Embed
import org.example.notifier.embed.EmbedSender
import org.example.notifier.embed.NormalEmbed
import org.example.streamer.domain.StreamerDisplayInfo
import org.example.streamer.domain.StreamerInfo
import org.example.streamer.manager.data.CacheStreamerManager

/**
 * 스트리머 목록 커맨드
 */
class StreamerListCommand(private val cacheStreamerManager: CacheStreamerManager) : AbstractCommand(),
    EmbedSender<List<StreamerDisplayInfo>> {

    override fun onCommand(event: MessageReceivedEvent) {
        sendEmbedMessage(event.message.channel.asTextChannel(), cacheStreamerManager.getStreamerInfos())
    }

    override fun createEmbed(data: List<StreamerDisplayInfo>): Embed {
        val builder = EmbedBuilder()
        builder.setTitle("스트리머 목록")
        data.forEach {
            builder.addField(MessageEmbed.Field("${it.index + 1}. ${it.name}", "", false))
        }
        return NormalEmbed(builder.build())
    }

}