package ru.abvadabra.physics

import com.sun.javafx.geom.Vec2d
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.text.NumberFormat
import javax.swing.JPanel
import javax.swing.text.NumberFormatter

class PendulumRenderer: JPanel(true), KeyEventDispatcher {


    init {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this)
    }

    lateinit var context: PendulumSimulationContext
    var isSpacePressed = false

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        (g as? Graphics2D)?.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.color = Color.GRAY

        val scale = 1

        val angle = context.simulatedAngle
        val velocity = context.simulatedAngularVelocity
        val length = context.armLength
        val pin = context.getPinPoint()

        val center = Vector2d(width / 2 + pin.x * 25 * scale, height / 2 + pin.y * 25 * scale)

        val armVec = Vector2d(Math.sin(angle), Math.cos(angle))
        val ballPos = Vector2d(armVec.x * length * 100 + center.x, -armVec.y * length * 100 + center.y)

        g.fillRect(center.x.toInt() - 5 * scale, center.y.toInt() - 5 * scale, 10 * scale, 10 * scale)
        g.drawLine(center.x.toInt(), center.y.toInt(), ballPos.x.toInt(), ballPos.y.toInt())
        g.fillOval(ballPos.x.toInt() - 8 * scale, ballPos.y.toInt() - 8 * scale, 16 * scale, 16 * scale)

        if(isSpacePressed) {
            val angularVelVec = Vector2d(armVec.y, armVec.x)
            val angularVecStart = Vector2d(armVec.x * length * 108 + center.x, -armVec.y * length * 108 + center.y)
            val angularVecEnd = Vector2d(
                angularVecStart.x + angularVelVec.x * velocity * 10,
                angularVecStart.y + angularVelVec.y * velocity * 10
            )

            g.color = Color.RED
            g.drawLine(
                angularVecStart.x.toInt(),
                angularVecStart.y.toInt(),
                angularVecEnd.x.toInt(),
                angularVecEnd.y.toInt()
            )
        }

        g.color = Color.BLACK
        g.drawString("Угол поворота: ${String.format("%.3f", angle)} рад", 0, 10)
        g.drawString("Угловая скорость: ${String.format("%.3f", velocity)} (Нажмите E для отображения)", 0, 25)

    }

    override fun dispatchKeyEvent(e: KeyEvent): Boolean {
        if(e.keyCode == KeyEvent.VK_E){
            if(e.id == KeyEvent.KEY_PRESSED)
                isSpacePressed = true
            else if(e.id == KeyEvent.KEY_RELEASED)
                isSpacePressed = false
        }
        return false
    }

    fun update(){
        repaint()
    }
}