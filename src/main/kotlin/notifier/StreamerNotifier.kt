package org.example.notifier

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import org.example.bot.ChzzkBot
import org.example.streamer.domain.StreamerInfo

/**
 * 스트리머 라이브 정보 갱신시 알려주는 역할 클래스
 */
class StreamerNotifier {

    fun notify(streamers : List<StreamerInfo>) {
        val jda = ChzzkBot.jda
        if (jda == null) {
            println("작동할 수 없습니다.")
            return
        }

        jda.guilds.forEach { guild ->
            guild.getTextChannelsByName("치지직-알림", true).forEach {
                it.sendMessageEmbeds(streamers.map { streamer -> buildEmbed(streamer) }.toList()).queue()
            }
        }
    }

    private fun buildEmbed(streamer : StreamerInfo) : MessageEmbed{
        val builder = EmbedBuilder()
        val online = if (streamer.isBroadcasting) "온라인" else "오프라인"
        builder.setAuthor(streamer.name, null, null)
        builder.setTitle("방송 상태가 변경되었습니다! - $online") //타이틀
        builder.setDescription("")
        builder.setThumbnail(streamer.avatarUrl)
        builder.addField(MessageEmbed.Field("치지직","[치지직 링크](https://chzzk.naver.com/live/${streamer.chzzkId})", false)) //필드 링크
        return builder.build()
    }
}