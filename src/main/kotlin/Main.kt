object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val simulation = PendulumSimulation()


        while(simulation.t < 10.0){
            simulation.equation.rungeKutta4Order(simulation.t, 0.05)
            simulation.t += 0.05
            println("${simulation.t}: ${simulation.equation.y[0]},${simulation.equation.y[1]}")
        }

    }
}