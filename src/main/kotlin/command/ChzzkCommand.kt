package org.example.command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.example.command.add.StreamerAddCommand
import org.example.command.list.StreamerListCommand
import org.example.command.reload.StreamerReloadCommand
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
 * @property reloadCommand 봇을 리로드하는 커맨드를 핸들링합니다.
 */
class ChzzkCommand(
    private val addCommand: StreamerAddCommand,
    private val removeCommand: StreamerRemoveCommand,
    private val listCommand: StreamerListCommand,
    private val reloadCommand: StreamerReloadCommand
) : AbstractCommand("/치지직") {

    override fun onCommand(event: MessageReceivedEvent) {
        val command = getArgument(event, 0)
        val arguments : List<String> = getArguments(event)
        val otherArgs : List<String> = arguments.subListOrEmpty(1, arguments.size)
        when (command) {
            "추가" -> addCommand(event, otherArgs)
            "삭제" -> removeCommand(event, otherArgs)
            "목록" -> listCommand(event, otherArgs)
            "리로드" -> reloadCommand(event, otherArgs)
            else -> sendUsage(event)
        }
    }

    private fun sendUsage(event: MessageReceivedEvent) {
        event.channel.sendMessage("/치지직 [ 추가 <이름> / 삭제 <번호> / 목록 / 리로드 ]로 사용해주시기 바랍니다.").queue()
    }

}

/**
 * 부가 커맨드 처리를 위한 커맨드 인터페이스. Front-Controller 처리 패턴
 */
interface SubCommand {
    // invoke함수를 통해 그냥 바로 호출가능하게 설정. args는 0번부터 바로 처리할 arg
    // /치지직 추가 asdf 일경우 추가를 담당하는 subcommand라면 args[0]은 asdf
    operator fun invoke(event: MessageReceivedEvent, args : List<String>)

}

// arg 자를때 자르거나 arg가 없는경우 empty list 반환
fun <T> List<T>.subListOrEmpty(from : Int, to : Int) = kotlin.runCatching {
    this.subList(from, to)
}.getOrElse { listOf() }