package org.example.command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.example.command.add.StreamerAddCommand
import org.example.command.list.StreamerListCommand
import org.example.command.remove.StreamerRemoveCommand

/**
 * /치지직 명령어를 처리하는 커맨드 클래스
 *
 * /치지직 추가 - 스트리머 추가
 * /치지직 삭제 - 스트리머 삭제
 * /치지직 목록 - 스트리머 목록 확인
 * /치지직 리로드 - 봇 리로드 수행
 *
 * @property addCommand 스트리머를 추가하는 커맨드를 핸들링합니다.
 * @property removeCommand 스트리머를 삭제하는 커맨드를 핸들링합니다.
 * @property listCommand 스트리머 목록을 확인하는 커맨드를 핸들링합니다.
 */
class ChzzkCommand(
    private val addCommand: StreamerAddCommand,
    private val removeCommand: StreamerRemoveCommand,
    private val listCommand: StreamerListCommand,
) : AbstractCommand() {

    override fun onCommand(event: MessageReceivedEvent) {
        val message = event.message.contentRaw
        //bot의 메시지인경우 리턴
        if (event.author.isBot)
            return
        //해당 커맨드가 아닌경우 리턴
        if (!message.startsWith("/치지직"))
            return

        val command = getArgument(event, 0)
        when (command) {
            "추가" -> addCommand.onCommand(event)
            "삭제" -> removeCommand.onCommand(event)
            "목록" -> listCommand.onCommand(event)
            else -> sendUsage(event)
        }
    }

    private fun sendUsage(event: MessageReceivedEvent) {
        event.channel.sendMessage("/치지직 [ 추가 <이름> / 삭제 <번호> / 목록 ]로 사용해주시기 바랍니다.").queue()
    }

}

// arg 자를때 자르거나 arg가 없는경우 empty list 반환
fun <T> List<T>.subListOrEmpty(from : Int, to : Int) = kotlin.runCatching {
    this.subList(from, to)
}.getOrElse { listOf() }