package org.example

import org.example.memory.Memory
import org.example.memory.Registers
import org.example.translator.DecodedInstruction
import org.example.translator.Decoder
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

    val inputTextArea = JTextArea()
    val outputTextArea = JTextArea()

    val font = Font("Arial", Font.PLAIN, 40)
    inputTextArea.font = font
    outputTextArea.font = font

    outputTextArea.isEditable = false

    val inputScrollPane = JScrollPane(inputTextArea)
    val outputScrollPane = JScrollPane(outputTextArea)

    val button = JButton("Run")

    button.addActionListener {
        val userInput = inputTextArea.text
        val output = StringBuilder()

        val encoder = Encoder()
        val decoder = Decoder()

        userInput.lines().forEach { line ->

            if (line.all { char -> char.isDigit() }) {
                val opcode = decoder.retrieveOpcode(line)
                val functs = decoder.retrieveFuncts(line)
                val operands = decoder.retrieveOperands(line)
                val instructionName = decoder.retrieveInstructionName(opcode, functs)

                val decoderInstruction = DecodedInstruction(instructionName, operands)

                output.append(decoderInstruction.toString()).append("\n")
            } else {
                val lineComponents = encoder.splitIntoComponents(line)
                val encodedLine = encodeCodeLine(lineComponents)
                output.append(encodedLine).append("\n")

                memory.write(currentAddressPointer++, encodedLine.toUInt(2))
            }

        }

        outputTextArea.text = output.toString()
    }

    val buttonPanel = JPanel()
    buttonPanel.add(button)

    val splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT, inputScrollPane, outputScrollPane)
    splitPane.resizeWeight = 0.5

    val frame = JFrame("RISC-V translator for Riscambler IDE")
    frame.layout = BorderLayout()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(500, 500)
    frame.setLocationRelativeTo(null)

    frame.add(splitPane, BorderLayout.CENTER)
    frame.add(buttonPanel, BorderLayout.NORTH)

    frame.isVisible = true

    splitPane.setDividerLocation(0.5)
}
