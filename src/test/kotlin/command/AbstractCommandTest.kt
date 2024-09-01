package command

import io.mockk.every
import io.mockk.mockk
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.example.command.AbstractCommand
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

// abstract Command 메소드 테스트용 클래스
class AbstractCommandTest {

    // 성공여부 판단을 위한 boolean 프로퍼티.
    var isSuccess : Boolean = false

    //mock용 이벤트
    val event : MessageReceivedEvent = mockk()
    val author : User = mockk()
    val message : Message = mockk()

    init {
        every { event.message } returns message
        every { event.author } returns author
    }

    //봇 메시지일경우 return
    @Test
    fun testIsBotMessage() {
        //message mock
        every { message.contentRaw } returns "/test"
        //author mock

        every { author.isBot } returns true //봇임을 리턴

        ImplementCommand("/test").onMessageReceived(event)
        assertFalse(isSuccess)
    }

    //다른 커맨드 메시지 수신시
    @Test
    fun testIsOtherCommandMessage() {
        //message mock
        every { message.contentRaw } returns "/wrong"
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴

        ImplementCommand("/test").onMessageReceived(event)
        assertFalse(isSuccess)
    }

    @Test
    fun testHandleCommand() {
        //message mock
        every { message.contentRaw } returns "/test" //올바른 커맨드 사용
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴

        ImplementCommand("/test").onMessageReceived(event)
        assertTrue(isSuccess)
    }

    @Test
    fun testGetNullArgument() {
        //message mock
        val command = "/test"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴
        //arg없이 호출한 커맨드
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> { ArgumentCommand(0).onMessageReceived(event) }
    }

    @Test
    fun testGetNullOutIndexArgument() {
        //message mock
        val command = "/test index"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴
        //arg없이 호출한 커맨드
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> { ArgumentCommand(1).onMessageReceived(event) }
    }

    @Test
    fun testGetWrongArguments() {
        //message mock
        val command = "/test wrong"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴
        //arg다른값으로 호출한 커맨드
        ArgumentCommand(0).onMessageReceived(event)
        assertFalse(isSuccess)
    }


    @Test
    fun testGetSuccessArguments() {
        //message mock
        val command = "/test success"
        every { message.contentRaw } returns command//arg없이 호출한 커맨드
        every { message.contentStripped } returns command
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴
        //성공하게 호출한 커맨드
        ArgumentCommand(0).onMessageReceived(event)
        assertTrue(isSuccess)
    }

    @Test
    fun testGetSuccessArgumentsOtherIndex() {
        //message mock
        val command = "/test command success"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴
        //arg 인덱스 벗어난 요청
        ArgumentCommand(1).onMessageReceived(event)
        assertTrue(isSuccess)
    }

    @Test
    fun testNullConcat() {
        //message mock
        val command = "/test"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴
        //arg없이 호출한 커맨드
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> { ConcatCommand(0, "concat").onMessageReceived(event) }
    }

    @Test
    fun testOutOfIndexConcat() {
        //message mock
        val command = "/test hello"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴
        //arg없이 호출한 커맨드
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> { ConcatCommand(1, "concat").onMessageReceived(event) }
    }

    //0번째 인덱스부터 합치기
    @Test
    fun testZeroConcat() {
        //message mock
        val command = "/test concat start hello"
        every { message.contentRaw } returns command//arg없이 호출한 커맨드
        every { message.contentStripped } returns command
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴

        ConcatCommand(0, "concat start hello").onMessageReceived(event)
        assertTrue(isSuccess)
    }

    //다른 인덱스부터 합치기
    @Test
    fun testOtherConcat() {
        //message mock
        val command = "/test concat start hello"
        every { message.contentRaw } returns command //arg없이 호출한 커맨드
        every { message.contentStripped } returns command
        //author mock
        every { author.isBot } returns false //봇이 아님을 리턴
        //1번째 인덱스부터 호출
        ConcatCommand(1, "start hello").onMessageReceived(event)
        assertTrue(isSuccess)
    }


    // 추상클래스를 구현한 클래스를 이용해 내부 커맨드 호출시 isSuccess true로 변경
    inner class ImplementCommand(private val command : String) : AbstractCommand(command) {
        override fun onCommand(event: MessageReceivedEvent) {
            isSuccess = true
        }
    }

    //arg값이 성공일경우 성공 반환하기
    inner class ArgumentCommand(val index : Int) : AbstractCommand("/test") {
        override fun onCommand(event: MessageReceivedEvent) {
            val arg = getArgument(event, index)
            if (arg == null)
                // null 여부 확인하기
                throw IllegalArgumentException("Custom Exception")
            else if (arg == "success")
                isSuccess = true

        }
    }

    //concat된값이 결과값이랑 같은경우 성공 반환하기
    inner class ConcatCommand(val index : Int, val match : String) : AbstractCommand("/test") {
        override fun onCommand(event: MessageReceivedEvent) {
            val arg = getConcatArgument(event, index)
            if (arg == null)
            // null 여부 확인하기
                throw IllegalArgumentException("Custom Exception")
            else if (arg == match)
                isSuccess = true

        }
    }

}