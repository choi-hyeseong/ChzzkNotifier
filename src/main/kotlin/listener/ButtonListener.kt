package org.example.listener

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.example.bot.ChzzkBot
import org.example.streamer.domain.StreamerInfo
import org.example.streamer.manager.ChzzkAPIManager
import org.example.streamer.manager.data.CacheStreamerManager

class ButtonListener(private val chzzkAPIManager: ChzzkAPIManager, private val cacheStreamerManager: CacheStreamerManager, val chzzkBot: ChzzkBot) : ListenerAdapter() {

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        super.onButtonInteraction(event)
        val id = event.componentId

        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                val result = chzzkAPIManager.searchDetail(id)
                cacheStreamerManager.addStreamer(StreamerInfo(result.channelName, result.channelId, result.channelImageUrl ?: "", false))
            }.onSuccess {
                //성공시
                event.channel.sendMessage("해당 스트리머가 추가되었습니다.").queue()
                chzzkBot.updateStatus("현재 ${cacheStreamerManager.getCount()}명 확인중!")
            }.onFailure {
                event.channel.sendMessage("해당 스트리머를 추가할 수 없습니다. 이미 추가된 스트리머이거나 잘못된 버튼입니다.")
            }

        }
    }
}
