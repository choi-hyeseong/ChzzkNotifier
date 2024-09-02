package org.example.notifier

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import org.example.bot.ChzzkBot
import org.example.notifier.embed.Embed
import org.example.notifier.embed.EmbedSender
import org.example.notifier.embed.NormalEmbed
import org.example.streamer.domain.StreamerInfo

/**
 * 스트리머 라이브 정보 갱신시 알려주는 역할 클래스
 */
class StreamerNotifier : EmbedSender<StreamerInfo> {

    fun notify(streamers : List<StreamerInfo>) {
        val jda = ChzzkBot.jda
        if (jda == null) {
            println("작동할 수 없습니다.")
            return
        }

        jda.guilds.forEach {
            guild -> guild.getTextChannelsByName("치지직-알림", true).forEach { channel ->
                streamers.forEach { streamer ->
                    sendEmbedMessage(channel, streamer)
                }
            }
        }
    }

    override fun createEmbed(data: StreamerInfo): Embed {
        val builder = EmbedBuilder()
        val online = if (data.isBroadcasting) "온라인" else "오프라인"
        builder.setAuthor(data.name, null, null)
        builder.setTitle("방송 상태가 변경되었습니다! - $online") //타이틀
        builder.setDescription("")
        builder.setThumbnail(data.avatarUrl)
        builder.addField(MessageEmbed.Field("치지직","[치지직 링크](https://chzzk.naver.com/live/${data.chzzkId})", false)) //필드 링크
        return NormalEmbed(builder.build())
    }
}