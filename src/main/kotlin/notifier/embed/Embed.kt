package org.example.notifier.embed

import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction

/**
 * EmbedSender에서 제네릭인 T값을 디스코드 embed 데이터형식으로 변환한것
 */
interface Embed {

    /**
     * 디스코드에서 전송가능한 Action 형태로 변환해서 반환합니다.
     * @param channel 전송할 채널입니다.
     * @return 메시지 데이터입니다. queue 호출시 메시지 전송됩니다.
     */
    fun createMessageAction(channel: MessageChannel): MessageCreateAction

}

/**
 * 기본적인 embed 객체입니다.
 * @param messageEmbed 디스코드 embed 객체입니다. 해당 embed를 래핑하였습니다.
 */
class NormalEmbed(
    private val messageEmbed: MessageEmbed,
) : Embed {

    override fun createMessageAction(channel: MessageChannel): MessageCreateAction {
        return channel.sendMessageEmbeds(messageEmbed)
    }
}

/**
 * 데코레이터를 이용해서 button 지원하는 embed.
 * @param embed 버튼이 달릴 embed 입니다.
 * @param buttons 버튼 데이터입니다.
 */
class ButtonEmbed(
    private val embed : NormalEmbed,
    private val buttons : List<ActionRow>
) : Embed {

    /**
     * 버튼을 추가한 embed를 반환합니다. 기존 embed 객체의 반환값에 compoent를 추가합니다
     */
    override fun createMessageAction(channel: MessageChannel): MessageCreateAction {
        return embed.createMessageAction(channel).setComponents(buttons)
    }
}