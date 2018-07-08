# Simple table written in kotlin

## Examples:

Table with bold header
```kotlin
SimpleTable(Table.LineWeight.BOLD)
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
```
```
+=====+==========+==========+
| Id  |   Name   |Last name |
+=====+==========+==========+
|1    |   John   |      Wick|
+-----+----------+----------+
|2    | Freddie  |   Mercury|
+-----+----------+----------+
```

Table with light header
```kotlin
SimpleTable(Table.LineWeight.LIGHT)
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
```
```
+-----+----------+----------+
| Id  |   Name   |Last name |
+-----+----------+----------+
|1    |   John   |      Wick|
|2    | Freddie  |   Mercury|
+-----+----------+----------+
```

Table with overflow

```kotlin
SimpleTable(overflow = true)
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
```
```
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
```