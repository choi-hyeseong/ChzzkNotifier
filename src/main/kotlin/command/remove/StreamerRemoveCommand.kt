package org.example.command.remove

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.example.bot.ChzzkBot
import org.example.command.SubCommand
import org.example.streamer.manager.StreamerInfoManager

/**
 * 스트리머 삭제 커맨드
 */
class StreamerRemoveCommand(private val streamerInfoManager: StreamerInfoManager, private val chzzkBot: ChzzkBot) : SubCommand {
    override fun invoke(event: MessageReceivedEvent, args: List<String>) {
        if (args.isEmpty()) {
            event.channel.sendMessage("식제할 스트리머 번호를 입력해주세요.").queue()
            return
        }

        val index = args[0].toIntOrNull()
        if (index == null) {
            event.channel.sendMessage("올바른 번호를 입력해주세요").queue()
            return
        }

        kotlin.runCatching {
            streamerInfoManager.removeStreamerByIndex(index - 1) //표기되는 번호보다 -1 위치에 인덱스 저장
        }.onSuccess {
            event.channel.sendMessage("해당 스트리머가 삭제되었습니다.").queue()
            chzzkBot.updateStatus("현재 ${streamerInfoManager.getCount()}명 확인중!")
        }.onFailure {
            event.channel.sendMessage("번호를 올바르게 입력해주세요.").queue()
        }

    }
}