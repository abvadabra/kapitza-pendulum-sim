package ru.abvadabra.physics

import java.awt.*
import javax.swing.JPanel

class PendulumRenderer: JPanel(true) {

    lateinit var context: PendulumSimulationContext

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        (g as? Graphics2D)?.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.color = Color.GRAY

        val scale = 1

        val angle = context.simulatedAngle
        val length = context.armLength
        val pin = context.getPinPoint()

        val center = Vector2d(width / 2 + pin.x * 25 * scale, height / 2 + pin.y * 25 * scale)

        val ballPos = Vector2d(Math.sin(angle) * length * 100 + center.x, -Math.cos(angle) * length * 100 + center.y)

        g.fillRect(center.x.toInt() - 5 * scale, center.y.toInt() - 5 * scale, 10 * scale, 10 * scale)
        g.drawLine(center.x.toInt(), center.y.toInt(), ballPos.x.toInt(), ballPos.y.toInt())
        g.fillOval(ballPos.x.toInt() - 8 * scale, ballPos.y.toInt() - 8 * scale, 16 * scale, 16 * scale)

        g.drawString("angle = " + angle, 0, 100)

    }

    fun update(){
        repaint()
    }
}