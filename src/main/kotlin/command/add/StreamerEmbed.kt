package org.example.command.add

import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button

/**
 * 스트리머 선택용 embed와 버튼 데이터
 */
data class StreamerEmbed(val embed : MessageEmbed, val buttons : List<ActionRow>)