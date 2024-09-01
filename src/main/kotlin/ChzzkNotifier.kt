package org.example

import kotlinx.coroutines.runBlocking
import org.example.bot.ChzzkBot
import org.example.command.ChzzkCommand
import org.example.streamer.parser.detail.StreamerDetailParser
import org.example.streamer.parser.search.StreamerSearcher

fun main(args : Array<String>) {
    ChzzkBot.create(args[0])
}