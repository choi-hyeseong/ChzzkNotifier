package bot

import io.mockk.*
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.example.bot.ChzzkBot
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ChzzkBotTest {

    //companion test - null token
    @Test
    fun testCreateBotWithNullToken() {
        assertThrows<IllegalArgumentException> { ChzzkBot.create(null) }
    }

    //empty token
    @Test
    fun testCreateBotWithEmptyToken() {
        assertThrows<IllegalArgumentException> { ChzzkBot.create("") }
    }


    //토큰값이 정상적일때 init 테스트
    @Test
    fun testInitMethod() {
        // 정적 mock
        mockkStatic(JDABuilder::class)

        //메소드 사용시 capture용 슬롯
        val token : CapturingSlot<String> = CapturingSlot()
        val intents : CapturingSlot<MutableList<GatewayIntent>> = CapturingSlot()

        val builder : JDABuilder = mockk() //create시 리턴할 mock Builder
        val jda : JDA = mockk() // 최종적으로 빌드될 jda

        //결과 반환 필요시 mock 반환
        every { JDABuilder.create(capture(token), capture(intents)) } returns builder
        every { builder.build() } returns jda

        // 생성
        ChzzkBot.create("asdf")

        assertEquals(listOf(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_MESSAGE_POLLS), intents.captured) //인텐트값 확인
        assertEquals("asdf", token.captured) //토큰값 확인
        assertEquals(ChzzkBot.jda, jda) //전역 jda에 할당이 됐는지 확인

        unmockkAll()
    }


}