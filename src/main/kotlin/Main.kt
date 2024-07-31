package org.example

import org.example.memory.Memory
import org.example.memory.Registers
import org.example.translator.Encoder
import org.example.translator.encodeCodeLine
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JSplitPane
import javax.swing.JTextArea

fun main(args: Array<String>) {
    val memory = Memory(64)
    val registers = Registers()
    var currentAddressPointer = 0

    // Create the text areas
    val inputTextArea = JTextArea()
    val outputTextArea = JTextArea()

    // Set a larger font for the text areas
    val font = Font("Arial", Font.PLAIN, 40)
    inputTextArea.font = font
    outputTextArea.font = font

    // Make the output text area non-editable
    outputTextArea.isEditable = false

    // Create the scroll panes for the text areas
    val inputScrollPane = JScrollPane(inputTextArea)
    val outputScrollPane = JScrollPane(outputTextArea)

    // Create the button
    val button = JButton("Run")

    // Add an action listener to the button
    button.addActionListener {
        val userInput = inputTextArea.text
        val output = StringBuilder()

        val encoder = Encoder()

        // Process each line of user input
        userInput.lines().forEach { line ->
            // Perform your processing on each line here
            val lineComponents = encoder.splitIntoComponents(line)
            val encodedLine = encodeCodeLine(lineComponents)
            output.append(encodedLine).append("\n")

            memory.write(currentAddressPointer++, encodedLine.toUInt(2))
        }

//        for (i in 0..currentAddressPointer) {
//            println(memory.fetch(i))
//        }

        // Set the processed output to the output text area
        outputTextArea.text = output.toString()
    }

    // Create a panel to hold the button
    val buttonPanel = JPanel()
    buttonPanel.add(button)

    // Create a split pane
    val splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT, inputScrollPane, outputScrollPane)
    splitPane.resizeWeight = 0.5 // Ensure equal resize weight

    // Create the frame
    val frame = JFrame("RISC-V translator for Riscambler IDE")
    frame.layout = BorderLayout()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(500, 500)
    frame.setLocationRelativeTo(null)

    // Add components to the frame
    frame.add(splitPane, BorderLayout.CENTER)
    frame.add(buttonPanel, BorderLayout.NORTH)

    // Make the frame visible
    frame.isVisible = true

    // Set divider location after the frame is visible
    splitPane.setDividerLocation(0.5)
}
