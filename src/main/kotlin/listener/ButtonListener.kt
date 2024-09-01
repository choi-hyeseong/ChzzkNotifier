package org.example.listener

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.example.streamer.domain.StreamerInfo
import org.example.streamer.manager.StreamerInfoManager
import org.example.streamer.parser.detail.StreamerDetailParser

class ButtonListener(private val streamerDetailParser: StreamerDetailParser, private val streamerInfoManager: StreamerInfoManager) : ListenerAdapter() {

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        super.onButtonInteraction(event)
        val id = event.componentId

        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                streamerDetailParser(id)
            }.onSuccess {
                //성공시
                streamerInfoManager.addStreamer(StreamerInfo(it.channelName, it.channelId, it.channelImageUrl, false))
                event.channel.sendMessage("해당 스트리머가 추가되었습니다.").queue()
                event.message.delete() //해당 메시지 제거
            }.onFailure {
                event.channel.sendMessage("올바르지 않은 버튼입니다. 다시 시도해주세요.")
            }

        }
    }
}