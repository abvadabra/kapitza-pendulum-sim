package ru.abvadabra.physics

import javax.swing.JFrame
import javax.swing.UIManager
import java.awt.Toolkit.getDefaultToolkit
import java.awt.Dimension
import java.awt.Toolkit


object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        val frame = PendulumUI("Маятник Капицы")
        val simulation = PendulumSimulationContext(frame)

        val dim = Toolkit.getDefaultToolkit().screenSize
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(480, 640)
        frame.setLocation(dim.width / 2 - frame.size.width / 2, dim.height / 2 - frame.size.height / 2)
        frame.isVisible = true
        frame.isResizable = false

        val step = 16L
        while (true){
            simulation.tick(step.toDouble() / 1000.0)
            Thread.sleep(step)
        }
    }
}