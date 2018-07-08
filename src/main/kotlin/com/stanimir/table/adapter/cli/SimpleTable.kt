package com.stanimir.table.adapter.cli

import com.stanimir.table.core.*
import java.util.*

/**
 * Simple implementation of table.
 *
 * @param headerWeight weight of the separating lines of header
 * @param overflow defines if the the value is longer than the width
 * should overflow of a new row
 *
 * @author Stanimir Iliev <stanimir.iliev@clouway.com />
 */

class SimpleTable(private val headerWeight: Table.LineWeight = Table.LineWeight.LIGHT, private val overflow: Boolean = false) : Table {

    private var headers = LinkedList<Header>()
    private val data = StringBuilder()

    override fun setHeader(headers: List<Header>): Table {
        if (headers.isEmpty() || headers.find { it.width < 0 || it.value.isEmpty() } != null) throw InvalidHeaderListProvidedException()

        this.headers = LinkedList(headers)
        data.setLength(0)// clears the data

        val formattedHeaders = headers.map { formatValue(it.width, it.value, it.align) }

        addSeparatingLine(headerWeight)
        for (i in 0 until formattedHeaders.map { it.size }.max()!!) {
            formattedHeaders.forEach { header ->
                data.append("|${header.getOrNull(i) ?: " ".repeat(header.first().length)}")
            }
            data.appendln('|')
        }
        addSeparatingLine(headerWeight)

        return this
    }

    override fun addRow(row: List<Cell>): Table {
        if (headers.isEmpty()) throw NoHeaderAddedException()

        val rows = mutableListOf<List<String>>()

        for (i in headers.indices) {
            val cell = row.getOrNull(i) ?: Cell("", headers[i].align)
            rows.add(formatValue(headers[i].width, cell.value, cell.align))
        }

        val rowsCount = rows.map { it.size }.max()!!

        for (i in 0 until rowsCount) {
            rows.forEach {
                data.append("|${it.getOrNull(i) ?: " ".repeat(it.first().length)}")
            }
            data.appendln('|')
        }

        return this
    }

    override fun addSeparatingLine(lineWeight: Table.LineWeight): Table {
        if (headers.isEmpty()) throw NoHeaderAddedException()

        val result = StringBuilder()

        headers.forEach { header ->
            result.append("+${
            when (lineWeight) {
                Table.LineWeight.LIGHT -> "-".repeat(header.width)
                Table.LineWeight.BOLD -> "=".repeat(header.width)
            }
            }")
        }
        result.append("+")

        data.appendln(result)

        return this
    }

    override fun build(): String {
        if (headers.isEmpty()) throw NoHeaderAddedException()

        return data.toString()
    }

    /**
     * Formats value. If the value's length is smaller than the width, then spaces are added
     * according the align and singleton list is returned.
     * If the value's length is bigger than the width and overflow is true, list of values are returned
     */
    private fun formatValue(width: Int, value: String, align: Table.Align): List<String> {
        val result: List<String>

        if (value.length > width && overflow) {
            val lastElementLength = value.chunked(width).last().length
            result =
                    if (lastElementLength < width) {
                        value.plus(" ".repeat(width - lastElementLength)).chunked(width)
                    } else {
                        value.chunked(width)
                    }
        } else if (value.length >= width) {
            result = listOf(value.substring(0, width))
        } else {
            result = listOf(when (align) {
                Table.Align.LEFT -> {
                    value.plus(" ".repeat(width - value.length))
                }
                Table.Align.RIGHT -> {
                    " ".repeat(width - value.length).plus(value)
                }
                Table.Align.CENTER -> {
                    " ".repeat((width - value.length) / 2)
                            .plus(value)
                            .plus(" ".repeat(
                                    if (((width - value.length) % 2) != 0)
                                        (width - value.length) / 2 + 1
                                    else
                                        (width - value.length) / 2)
                            )
                }
            })
        }
        return result
    }
}