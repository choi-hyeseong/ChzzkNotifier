package org.example

import kotlinx.coroutines.runBlocking
import org.example.bot.ChzzkBot
import org.example.command.ChzzkCommand
import org.example.streamer.parser.detail.StreamerDetailParser
import org.example.streamer.parser.search.StreamerSearcher

fun main(args : Array<String>) {
    val interval = if (args.size == 2) args[1].toIntOrNull() else 60
    ChzzkBot.create(args[0], interval ?: 60)
}