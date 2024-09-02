package org.example.notifier.embed

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel

/**
 * embed를 보낼 수 있는 클래스가 구현해야할 추상 클래스
 */
interface EmbedSender<T> {

    /**
     * Embed 메시지를 보냅니다.
     * @param channel 전송할 채널입니다.
     * @param message 보내질 데이터입니다.
     */
    fun sendEmbedMessage(channel: TextChannel, message: T) {
        val embed = createEmbed(message)
        embed.createMessageAction(channel).queue()
    }

    /**
     * 보내질 데이터를 embed 형식으로 변환합니다.
     * @param data 전송될 데이터입니다
     * @return Embed로 변환된 데이터입니다.
     */
    fun createEmbed(data : T) : Embed
}