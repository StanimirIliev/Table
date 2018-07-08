package com.stanimir.table.adapter.cli

import com.stanimir.table.core.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * @author Stanimir Iliev (stanimir.iliev@clouway.com)
 */

class SimpleTableTest {

    @Test
    fun buildTableWithBoldHeader() {
        val result = SimpleTable(Table.LineWeight.BOLD)
                .setHeader(listOf(
                        Header(5, "Id", Table.Align.CENTER),
                        Header(10, "Name", Table.Align.CENTER),
                        Header(10, "Last name", Table.Align.CENTER)
                ))
                .addRow(listOf(
                        Cell("1", Table.Align.LEFT),
                        Cell("John", Table.Align.CENTER),
                        Cell("Wick", Table.Align.RIGHT)
                ))
                .addSeparatingLine(Table.LineWeight.LIGHT)
                .addRow(listOf(
                        Cell("2", Table.Align.LEFT),
                        Cell("Freddie", Table.Align.CENTER),
                        Cell("Mercury", Table.Align.RIGHT)
                ))
                .addSeparatingLine(Table.LineWeight.LIGHT)
                .build()

        assertThat(result, `is`(equalTo(
                """
                    +=====+==========+==========+
                    | Id  |   Name   |Last name |
                    +=====+==========+==========+
                    |1    |   John   |      Wick|
                    +-----+----------+----------+
                    |2    | Freddie  |   Mercury|
                    +-----+----------+----------+
                    """.trimIndent().plus('\n')
        )))
    }

    @Test
    fun buildTableWithLightHeader() {
        val result = SimpleTable()// by default header is light
                .setHeader(listOf(
                        Header(5, "Id", Table.Align.CENTER),
                        Header(10, "Name", Table.Align.CENTER),
                        Header(10, "Last name", Table.Align.CENTER)
                ))
                .addRow(listOf(
                        Cell("1", Table.Align.LEFT),
                        Cell("John", Table.Align.CENTER),
                        Cell("Wick", Table.Align.RIGHT)
                ))
                .addRow(listOf(
                        Cell("2", Table.Align.LEFT),
                        Cell("Freddie", Table.Align.CENTER),
                        Cell("Mercury", Table.Align.RIGHT)
                ))
                .addSeparatingLine(Table.LineWeight.LIGHT)
                .build()

        assertThat(result, `is`(equalTo(
                """
                    +-----+----------+----------+
                    | Id  |   Name   |Last name |
                    +-----+----------+----------+
                    |1    |   John   |      Wick|
                    |2    | Freddie  |   Mercury|
                    +-----+----------+----------+
                    """.trimIndent().plus('\n')
        )))
    }

    @Test
    fun setOnlyHeader() {
        val result = SimpleTable()
                .setHeader(listOf(
                        Header(5, "Id", Table.Align.CENTER),
                        Header(10, "Name", Table.Align.CENTER),
                        Header(10, "Last name", Table.Align.CENTER)
                ))
                .build()

        assertThat(result, `is`(equalTo(
                """
                    +-----+----------+----------+
                    | Id  |   Name   |Last name |
                    +-----+----------+----------+
                    """.trimIndent().plus('\n')
        )))
    }

    @Test
    fun overrideHeader() {
        val result = SimpleTable()
                .setHeader(listOf(
                        Header(5, "Id", Table.Align.CENTER),
                        Header(10, "Name", Table.Align.CENTER),
                        Header(10, "Last name", Table.Align.CENTER)
                ))
                .setHeader(listOf(
                        Header(10, "Name", Table.Align.CENTER),
                        Header(10, "Last name", Table.Align.CENTER)
                ))
                .build()

        assertThat(result, `is`(equalTo(
                """
                    +----------+----------+
                    |   Name   |Last name |
                    +----------+----------+
                    """.trimIndent().plus('\n')
        )))
    }

    @Test(expected = NoHeaderAddedException::class)
    fun tryToAddRowWithoutHeader() {
        SimpleTable().addRow(listOf(Cell("1", Table.Align.CENTER)))
    }

    @Test(expected = NoHeaderAddedException::class)
    fun tryToBuildTableWithoutHeader() {
        SimpleTable().build()
    }

    @Test(expected = NoHeaderAddedException::class)
    fun tryToAddSeparatingLineWithoutHeader() {
        SimpleTable().addSeparatingLine(Table.LineWeight.LIGHT)
    }

    @Test
    fun addRowWithMoreCellsThanHeader() {
        val result = SimpleTable()
                .setHeader(listOf(
                        Header(5, "Id", Table.Align.CENTER),
                        Header(10, "Name", Table.Align.CENTER),
                        Header(10, "Last name", Table.Align.CENTER)
                ))
                .addRow(listOf(
                        Cell("1", Table.Align.LEFT),
                        Cell("John", Table.Align.CENTER),
                        Cell("Wick", Table.Align.RIGHT),
                        Cell("Additional cell", Table.Align.RIGHT)
                ))
                .addSeparatingLine(Table.LineWeight.LIGHT)
                .build()

        assertThat(result, `is`(equalTo(
                """
                    +-----+----------+----------+
                    | Id  |   Name   |Last name |
                    +-----+----------+----------+
                    |1    |   John   |      Wick|
                    +-----+----------+----------+
                    """.trimIndent().plus('\n')
        )))
    }

    @Test
    fun addRowWithFewerCellsThanHeader() {
        val result = SimpleTable()
                .setHeader(listOf(
                        Header(5, "Id", Table.Align.CENTER),
                        Header(10, "Name", Table.Align.CENTER),
                        Header(10, "Last name", Table.Align.CENTER)
                ))
                .addRow(listOf(
                        Cell("1", Table.Align.LEFT),
                        Cell("John", Table.Align.CENTER)
                ))
                .addSeparatingLine(Table.LineWeight.LIGHT)
                .build()

        assertThat(result, `is`(equalTo(
                """
                    +-----+----------+----------+
                    | Id  |   Name   |Last name |
                    +-----+----------+----------+
                    |1    |   John   |          |
                    +-----+----------+----------+
                    """.trimIndent().plus('\n')
        )))
    }

    @Test(expected = InvalidHeaderListProvidedException::class)
    fun tryToSetHeaderOfEmptyList() {
        SimpleTable().setHeader(emptyList())
    }

    @Test(expected = InvalidHeaderListProvidedException::class)
    fun tryToSetHeaderWithInvalidWidth() {
        SimpleTable().setHeader(listOf(Header(0, "", Table.Align.CENTER)))
    }

    @Test
    fun buildTableWithOverflow() {
        val result = SimpleTable(overflow = true)
                .setHeader(listOf(
                        Header(5, "Id", Table.Align.CENTER),
                        Header(2, "Name", Table.Align.CENTER),
                        Header(10, "Last name", Table.Align.CENTER)
                ))
                .addRow(listOf(
                        Cell("1", Table.Align.LEFT),
                        Cell("John", Table.Align.CENTER),
                        Cell("Wick", Table.Align.RIGHT)
                ))
                .addSeparatingLine(Table.LineWeight.LIGHT)
                .addRow(listOf(
                        Cell("2", Table.Align.LEFT),
                        Cell("Freddie", Table.Align.CENTER),
                        Cell("Mercury", Table.Align.RIGHT)
                ))
                .addSeparatingLine(Table.LineWeight.LIGHT)
                .build()

        assertThat(result, `is`(equalTo(
                """
                    +-----+--+----------+
                    | Id  |Na|Last name |
                    |     |me|          |
                    +-----+--+----------+
                    |1    |Jo|      Wick|
                    |     |hn|          |
                    +-----+--+----------+
                    |2    |Fr|   Mercury|
                    |     |ed|          |
                    |     |di|          |
                    |     |e |          |
                    +-----+--+----------+
                    """.trimIndent().plus('\n')
        )))
    }

    @Test
    fun buildTableWithoutOverflow() {
        val result = SimpleTable()// by default overflow is set to false
                .setHeader(listOf(
                        Header(5, "Id", Table.Align.CENTER),
                        Header(2, "Name", Table.Align.CENTER),
                        Header(10, "Last name", Table.Align.CENTER)
                ))
                .addRow(listOf(
                        Cell("1", Table.Align.LEFT),
                        Cell("John", Table.Align.CENTER),
                        Cell("Wick", Table.Align.RIGHT)
                ))
                .addSeparatingLine(Table.LineWeight.LIGHT)
                .addRow(listOf(
                        Cell("2", Table.Align.LEFT),
                        Cell("Freddie", Table.Align.CENTER),
                        Cell("Mercury", Table.Align.RIGHT)
                ))
                .addSeparatingLine(Table.LineWeight.LIGHT)
                .build()

        assertThat(result, `is`(equalTo(
                """
                    +-----+--+----------+
                    | Id  |Na|Last name |
                    +-----+--+----------+
                    |1    |Jo|      Wick|
                    +-----+--+----------+
                    |2    |Fr|   Mercury|
                    +-----+--+----------+
                    """.trimIndent().plus('\n')
        )))
    }
}