package command

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction
import org.example.command.ChzzkCommand
import org.example.command.add.StreamerAddCommand
import org.example.command.list.StreamerListCommand
import org.example.command.remove.StreamerRemoveCommand
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

//치지직 커맨드 테스트
class ChzzkCommandTest {

    //mock용 커맨드
    val addCommand : StreamerAddCommand = mockk()
    val removeCommand : StreamerRemoveCommand = mockk()
    val listCommand : StreamerListCommand = mockk()
    val reloadCommand : StreamerReloadCommand = mockk()

    // 객체 초기화
    val chzzkCommand : ChzzkCommand = ChzzkCommand(addCommand, removeCommand, listCommand, reloadCommand)

    //mock용 이벤트
    val event : MessageReceivedEvent = mockk()
    val message : Message = mockk()

    init {
        every { event.message } returns message
    }



    @Test
    fun testCallAddCommand() {
        //addCommand 호출
        val command = "/치지직 추가"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command

        every { addCommand.invoke(any()) } returns mockk()

        chzzkCommand.onCommand(event)
        verify(atLeast = 1) { addCommand.invoke(any()) }
    }

    @Test
    fun testCallRemoveCommand() {
        //addCommand 호출
        val command = "/치지직 삭제"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command

        every { removeCommand.invoke(any()) } returns mockk()

        chzzkCommand.onCommand(event)
        verify(atLeast = 1) { removeCommand.invoke(any()) }
    }

    @Test
    fun testCallListCommand() {
        //addCommand 호출
        val command = "/치지직 목록"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command

        every { listCommand.invoke(any()) } returns mockk()

        chzzkCommand.onCommand(event)
        verify(atLeast = 1) { listCommand.invoke(any()) }
    }

    @Test
    fun testCallReloadCommand() {
        //addCommand 호출
        val command = "/치지직 리로드"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command

        every { reloadCommand.invoke(any()) } returns mockk()

        chzzkCommand.onCommand(event)
        verify(atLeast = 1) { reloadCommand.invoke(any()) }
    }

    @Test
    fun testNullArgCommand() {
        // arg가 오지 않은경우
        val command = "/치지직"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command

        val action = usageMock()
        chzzkCommand.onCommand(event)
        verify(atLeast = 1) { action.queue() }
    }

    @Test
    fun testWrongArgCommand() {
        // arg가 오지 않은경우
        val command = "/치지직 설정"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command

        val action = usageMock()
        chzzkCommand.onCommand(event)
        verify(atLeast = 1) { action.queue() }
    }

    private fun usageMock() : MessageCreateAction {
        //usage mock
        val channel : MessageChannelUnion = mockk()
        val messageCreateAction : MessageCreateAction = mockk()
        every { event.channel } returns channel
        every { channel.sendMessage(any<CharSequence>()) } returns messageCreateAction
        every { messageCreateAction.queue() } returns mockk()
        // create action 리턴하는 이유? = queue 호출 확인.
        return messageCreateAction
    }
}