import java.lang.Math.*

class PendulumSimulation{

    val G = 9.80665

    val armLength = 1.0 // in meters
    val initialAngle = 0.1 // in radians
    val initialAngularVelocity = 0.0 // in radians/second

    val forcingAmplitude = 0.0 // in meters
    val forcingFreq = 2.0 * PI

    var t = 0.0

    val equation = DiffEquationSolver(2, this::calculate).apply {
        this.y[0] = initialAngle
        this.y[1] = initialAngularVelocity
    }

    private fun calculate(p: Double, y: DoubleArray, k: DoubleArray){
        k[0] = y[1]

        //motion equation
        //d/dt[dL/dθ] = dL/dθ
        //d/dt[m * l^2 * θ. = m * w[f] F * l * cos(w[f] * t) * sinθ] = m*g*l*sinθ - m * w[f] * F * l * θ. * cos(w[f] * t) * cosθ
        //θ.. = 1 / l * (g * sinθ - w[f]^2 * F * sin(w[f] * t] * sinθ)
        k[1] = (G - forcingFreq * forcingFreq * forcingAmplitude * sin(forcingFreq * p)) * sin(y[0]) / armLength
    }
}