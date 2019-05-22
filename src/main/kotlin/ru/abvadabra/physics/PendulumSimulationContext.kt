package ru.abvadabra.physics

import java.lang.Math.*

class PendulumSimulationContext(val view: PendulumUI){

    val G = 9.80665

    var armLength = 1.0
    var initialAngle = 0.0
    var initialAngularVelocity = 1.0

    var forcingAmplitude = 0.15
    var forcingFreq = 10 * 2.0 * PI
        set(value) {
            field = PI * 2.0 * value
        }

    var t = 0.0
    var speed = 1.0
    var isSumulating = false

    var equation = createEquationSolver()
    val simulatedAngle: Double
        get() = equation.y[0]

    val simulatedAngularVelocity: Double
        get() = equation.y[1]


    init {
        view.setup(this)
    }

    fun tick(delta: Double){
        if(isSumulating) {
            equation.rungeKutta4Order(t, delta * speed)
            t += delta * speed
            view.pendulumRenderer.update()
        }
    }


    fun getPinPoint(): Vector2d{
        return Vector2d(0.0, forcingAmplitude * sin(forcingFreq * t))
    }

    private fun calculate(p: Double, y: DoubleArray, k: DoubleArray){
        k[0] = y[1]
        k[1] = (G - forcingFreq * forcingFreq * forcingAmplitude * sin(forcingFreq * p)) * sin(y[0]) / armLength
    }

    private fun createEquationSolver() = DiffEquationSolver(2, this::calculate).apply {
        this.y[0] = initialAngle
        this.y[1] = initialAngularVelocity
    }

    fun stopSimulation() {
        isSumulating = false
    }

    fun startSimulation() {
        isSumulating = true
        equation = createEquationSolver()
        t = 0.0
    }

    fun restart(){
        stopSimulation()
        startSimulation()
    }
}