package org.example.command.add

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button
import org.example.command.AbstractCommand
import org.example.streamer.domain.StreamerInfo
import org.example.streamer.parser.search.StreamerSearcher

/**
 * 스트리머 추가 커맨드
 */
class StreamerAddCommand(
    private val streamerSearcher: StreamerSearcher,
) : AbstractCommand() {


    // embed 빌드
    private fun buildEmbed(streamers : List<StreamerInfo>) : StreamerEmbed {
        val builder = EmbedBuilder()
        val rows : MutableList<ActionRow> = mutableListOf()
        val buttons : MutableList<Button> = mutableListOf()
        builder.setTitle("추가할 스트리머를 선택해주세요.")
        builder.setDescription(" ")
        streamers.forEachIndexed { index, streamerInfo ->
            builder.addField(MessageEmbed.Field("${index + 1}. ${streamerInfo.name}", "치지직 고유 id : ${streamerInfo.chzzkId}", false))
            buttons.add(Button.secondary(streamerInfo.chzzkId, streamerInfo.name))
            if (buttons.size >= 5) {
                rows.add(ActionRow.of(ArrayList(buttons))) //클론해서 넣기.
                buttons.clear()
            }
        }
        if (buttons.isNotEmpty())
            rows.add(ActionRow.of(buttons))

        return StreamerEmbed(builder.build(), rows)
    }

    override fun onCommand(event: MessageReceivedEvent) {
        // 스트리머 이름이 비어있는경우
        val name = getArgument(event, 1)
        if (name == null) {
            event.channel.sendMessage("스트리머 이름을 입력해주세요.").queue()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val streamers = streamerSearcher.searchStreamer(name)
            val streamerEmbed : StreamerEmbed = buildEmbed(streamers)
            event.channel.sendMessageEmbeds(streamerEmbed.embed).setComponents(streamerEmbed.buttons).queue()
        }
    }
}