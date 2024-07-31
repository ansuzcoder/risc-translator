package org.example

import org.example.translator.Encoder
import org.example.translator.encodeCodeLine
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea

fun main(args: Array<String>) {
    // Create the text areas
    val inputTextArea = JTextArea()
    val outputTextArea = JTextArea()

    // Set a larger font for the text areas
    val font = Font("Arial", Font.PLAIN, 16)
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
            // For demonstration, we'll just reverse each line
            val lineComponents = encoder.splitIntoComponents(line)
            val encodedLine = encodeCodeLine(lineComponents)
            output.append(encodedLine).append("\n")
        }

        // Set the processed output to the output text area
        outputTextArea.text = output.toString()
    }

    // Create a panel to hold the button
    val buttonPanel = JPanel()
    buttonPanel.add(button)

    // Create the frame
    val frame = JFrame("RISC-V translator for Riscambler IDE")
    frame.layout = BorderLayout()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(500, 500)
    frame.setLocationRelativeTo(null)

    // Create a panel for text areas
    val textPanel = JPanel()
    textPanel.layout = BorderLayout()
    textPanel.add(inputScrollPane, BorderLayout.CENTER)
    textPanel.add(outputScrollPane, BorderLayout.SOUTH)

    // Add components to the frame
    frame.add(textPanel, BorderLayout.CENTER)
    frame.add(buttonPanel, BorderLayout.NORTH)

    // Make the frame visible
    frame.isVisible = true
}
