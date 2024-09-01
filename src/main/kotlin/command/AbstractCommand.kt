package org.example.command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

/**
 * 메시지를 받는 Command에서 필요한 메소드와 보일러플레이트 코드 제거를 위한 추상클래스
 * @property command 실행할 명령어 입니다.
 */
abstract class AbstractCommand(private val command : String) : ListenerAdapter() {

    abstract fun onCommand(event: MessageReceivedEvent)

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message.contentRaw
        //bot의 메시지인경우 리턴
        if (event.author.isBot)
            return
        //해당 커맨드가 아닌경우 리턴
        if (!message.startsWith(command))
            return
        onCommand(event)
    }

    // arg값 가져오는 함수
    protected fun getArgument(event: MessageReceivedEvent, index: Int): String? {
        val splitArgument = getArguments(event)
        return if (splitArgument.size <= index || splitArgument[0].isEmpty()) //out of index
            null
        else
            splitArgument[index]
    }

    // arg 합쳐서 가져오기
    protected fun getConcatArgument(event: MessageReceivedEvent, from: Int): String? {
        val splitArgument = getArguments(event)
        // out of index
        if (splitArgument.size <= from)
            return null

        val builder: StringBuilder = StringBuilder()
        // argument concat
        splitArgument.forEachIndexed { index, argument ->
            if (index >= from)
                builder.append(argument).append(" ")
        }
        // remove space of end
        return builder.toString().trim()
    }

    // 분리 arg값 가져오기
    private fun getArguments(event: MessageReceivedEvent): List<String> {
        var message = event.message.contentStripped //string으로 strip된 메시지
        message = message.replace(command, "").trim() //커맨드 제거
        return message.split(" ").toList()
    }

}