package org.example.command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

/**
 * 메시지를 받는 Command에서 필요한 메소드와 보일러플레이트 코드 제거를 위한 추상클래스
 * @property command 실행할 명령어 입니다.
 */
abstract class AbstractCommand : ListenerAdapter() {

    abstract fun onCommand(event: MessageReceivedEvent)

    override fun onMessageReceived(event: MessageReceivedEvent) {
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
        if (splitArgument.size <= from || splitArgument[0].isEmpty())
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
    fun getArguments(event: MessageReceivedEvent): List<String> {
        val message = event.message.contentStripped //string으로 strip된 메시지
        val split =  message.split(" ")
        return split.subListOrEmpty(1, split.size) //맨 처음 명령어 부분 제거
    }

}