package com.stanimir.table.core

/**
 * @author Stanimir Iliev <stanimir.iliev@clouway.com />
 */

interface Table {

    enum class Align { LEFT, CENTER, RIGHT }
    enum class LineWeight { LIGHT, BOLD }


    /**
     * Set header of the table. Overrides if it's already set
     *
     * @param headers the headers to set
     * @throw [InvalidHeaderListProvidedException] if the headers list is empty
     * or with invalid width
     * @return the same instance of the table for chain
     */
    fun setHeader(headers: List<Header>): Table

    /**
     * Adds row to the table. The rows are with width of the header.
     * If the size of the row is bigger than the headers size, some rows
     * are missed. If opposite the, empty cells are used to reach the headers size.
     *
     * @param row the row to
     * @throw [NoHeaderAddedException] if header is not set
     * @return the same instance of the table for chain
     */
    fun addRow(row: List<Cell>): Table

    /**
     * Adds separating line
     *
     * @param lineWeight the weight of the separating line
     * @throw [NoHeaderAddedException] if header is not set
     * @return the same instance of the table for chain
     */
    fun addSeparatingLine(lineWeight: LineWeight): Table

    /**
     * Builds the table
     *
     * @throw [NoHeaderAddedException] if header is not set
     * @return the string representation of the table
     */
    fun build(): String
}