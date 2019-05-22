package ru.abvadabra.physics

class DiffEquationSolver(val params: Int, val dataProvider: (Double, DoubleArray, DoubleArray) -> Unit) {

    private val data = Array(7) { DoubleArray(params) }

    val y: DoubleArray
        get() = data[0]
    val yp: DoubleArray
        get() = data[1]
    val ymid: DoubleArray
        get() = data[2]
    val k1: DoubleArray
        get() = data[3]
    val k2: DoubleArray
        get() = data[4]
    val k3: DoubleArray
        get() = data[5]
    val k4: DoubleArray
        get() = data[6]

    fun rungeKutta4Order(x: Double, delta: Double){
        val d = delta * 0.5
        dataProvider(x, this.y, this.k1)
        repeat(params) {
            this.ymid[it] = this.y[it] + d * this.k1[it]
        }
        dataProvider(x + d, this.ymid, this.k2)
        repeat(params){
            this.ymid[it] = this.y[it] + d * this.k2[it]
        }
        dataProvider(x + d, this.ymid, this.k3)
        repeat(params){
            this.ymid[it] = this.y[it] + delta * this.k3[it]
        }
        dataProvider(x + delta, this.ymid, this.k4)
        repeat(params){
            this.y[it] += delta * (this.k1[it] + 2.0 * this.k2[it] + 2.0 * this.k3[it] + this.k4[it]) / 6.0
        }
    }
}