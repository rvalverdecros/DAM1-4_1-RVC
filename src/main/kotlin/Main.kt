class Modulo(val maxAlumnos: Int = 20) {
    var numAlumnosMat = 0
    var lisAlumnos= arrayOfNulls<Alumno>(maxAlumnos)
    var lisnotas = Array(4){FloatArray(maxAlumnos)}

    companion object{
        const val EV_PRIMER="1"
        const val EV_SEGUN="2"
        const val EV_TERCER="3"
        const val EV_FINAl="4"
    }

    fun matricularAlumno(alumno: Alumno) : Boolean{ //12. Poder matricular alumnos siempre que no se llegue al máximo de alumnos
        var matriculado = false
        try {
            lisAlumnos[lisAlumnos.indexOf(lisAlumnos.first { it == null })] = alumno
            for(i in 0..3) {
                lisnotas[i][lisAlumnos.indexOf(alumno)] = alumno.notas[i]
            }
            matriculado = true
            numAlumnosMat++
        }catch (e:NoSuchElementException){println("No puedes meter más alumnos")}
        return true
    }

    fun bajaAlumno(idAlumno:String): Boolean{ //13. Dar de baja alumnos
        var eliminado = false
        val indice = lisAlumnos.indexOf(lisAlumnos.first { it?.id.toString() == idAlumno})
        val idAlumno = lisAlumnos[indice]?.id
        lisAlumnos[indice] = null
        for(i in 0..3) {
            lisnotas[i][indice] = 0f
        }
        eliminado = true
        numAlumnosMat--
        println("Se ha eleminado correctamente")
        return eliminado
    }

    fun listaNotas(evaluacion : String): ArrayList<Pair<String,Float>>{ //3. Obtener lista de alumnos, nota con las notas por evaluación, por defecto final.
        val lisNotas : ArrayList<Any> = ArrayList()
        val lisAlumnos = lisAlumnos
        val lisPairNotas = ArrayList<Pair<String,Float>>()
        when(evaluacion){
            EV_PRIMER -> lisnotas[0].forEach { lisNotas.add(it) }
            EV_SEGUN -> lisnotas[1].forEach { lisNotas.add(it) }
            EV_TERCER -> lisnotas[2].forEach { lisNotas.add(it) }
            EV_FINAl -> lisnotas[3].forEach { lisNotas.add(it) }
            "" -> lisnotas[3].forEach { lisNotas.add(it) }
            else -> println("Evaluación no válida")
        }
        var i = 0
        do {
            val pair = Pair(lisAlumnos[i]?.id,lisNotas[i])
            lisPairNotas.add(pair as Pair<String, Float>)
            i++
        }while (i != lisNotas.size)
        return lisPairNotas
    }

    fun numeroAprobados(evaluacion: String): Int { //4.Para un módulo, calcular cuantos alumnos han aprobado por evaluación, por defecto final
        val lisNotas = listaNotas(evaluacion)
        val lisNotasAprob = lisNotas.filter { it.second >= 5f }
        println("Han aprobado ${lisNotasAprob.size} alumnos")
        return lisNotasAprob.size
    }

    fun notaMasBaja(evaluacion: String): Float { //5. Para un módulo, calcular la nota más baja por evaluación, por defecto final
        val lisNotas = listaNotas(evaluacion)
        lisNotas.removeAll { (it.first != "") && (it.second == 0f) }
        lisNotas.sortBy { it.second }
        println("La Nota mas baja es:")
        return lisNotas[0].second
    }

    fun notaMasAlta(evaluacion: String): Float { //6. Para un módulo, calcular la nota más alta por evaluación, por defecto final
        val lisNotas = listaNotas(evaluacion)
        lisNotas.removeAll { (it.first != "") && (it.second == 0f) }
        lisNotas.sortByDescending { it.second }
        println("La Nota mas alta es:")
        return lisNotas[0].second
    }

    fun notaMedia(evaluacion: String): Float { //7. Para un módulo, calcular la nota media entre todos los alumnos por evaluación, por defecto final
        val lisPairNotas = listaNotas(evaluacion)
        var notas: Float = 0f
        lisPairNotas.forEach { notas += it.second }
        println("La Nota media de todos los alumnos de la evaluacion elegida es:")
        return notas / numAlumnosMat
    }

    fun hayAlumnosConDiez(evaluacion: String): Boolean { //8. Para un módulo, calcular si alguno tiene un 10 por evaluación, por defecto final
        val lisNotas = listaNotas(evaluacion)
        lisNotas.removeAll { it.second != 10f }
        println("La cantidad de alumnos que han sacado un 10 es:")
        return (lisNotas.size > 0)
    }

    fun hayAlumnosAprobados(evaluacion: String): Boolean { //9.Para un módulo, calcular si hay alumnos aprobados por evaluación, por defecto final
        val lisNotas = listaNotas(evaluacion)
        lisNotas.removeAll { it.second < 5 }
        println("Han aprobado un total de:")
        return (lisNotas.size > 0)
    }

    fun primeraNotaNoAprobada(evaluacion:String): Float{ //10. Para un módulo, calcular la primera nota que no ha superado el 5 por evaluación, por defecto final
        val lisNotas = listaNotas(evaluacion)
        lisNotas.removeAll { it.second >= 5 }
        // lisNotas.sortByDescending { it.second } Usar en caso de mayor nota suspensa
        println("La primera nota suspensa es un :")
        return lisNotas[0].second
    }

    fun listaNotasOrdenados(evaluacion:String): List<Pair<String,Float>>{ //11. Para un módulo, calcular si hay alumnos aprobados por evaluación, por defecto final
        val lisNotas = listaNotas(evaluacion)
        lisNotas.removeAll { (it.first != "") && (it.second == 0f) }
        lisNotas.sortBy { it.second }
        return lisNotas
    }

}

class Alumno(val id: String,val nombre: String, val apellido1: String, val apellido2: String, val nota1t: Float, val nota2t: Float, val nota3t: Float) {
    init {
        require (nota1t in (0f..10f) && (nota2t in (0f..10f) && (nota3t in (0f..10f)))) {"La nota que has escrito no es valida"}
    }
    var notas = arrayOf(nota1t, nota2t, nota3t, calculaEvaluacionFinal())
    private fun calculaEvaluacionFinal(): Float { //2. Calcula la nota final del alumno
        return ((nota1t + nota2t + nota3t) / 3)
    }
}

fun main() {
    val modulo = Modulo(15) //1. Crea el módulo con un límite de alumnos

    //1. Aquí se crean los alumnos
    val alumno1 = Alumno("1","Pedro","Pacheco","Díaz",6f,5.3f,9.3f)
    val alumno2 = Alumno("2","Lucas","Pacheco","Díaz",2.8f,6.5f,3.0f)
    val alumno3 = Alumno("3","Rafa","Pacheco","Díaz",4f,4.8f,7.69f)
    val alumno4 = Alumno("4","Pedro","Pacheco","Díaz",4.6f,4.8f,5.1f)
    val alumno5 = Alumno("5","Pedro","Pacheco","Díaz",6f,5.3f,9.3f)
    val alumno6 = Alumno("6","Lucas","Pacheco","Díaz",2.8f,6.5f,3.0f)
    val alumno7 = Alumno("7","Rafa","Pacheco","Díaz",4f,4.8f,7.69f)
    val alumno8 = Alumno("8","Pedro","Pacheco","Díaz",4.6f,4.8f,5.1f)
    val alumno9 = Alumno("9","Pedro","Pacheco","Díaz",6f,5.3f,9.3f)
    val alumno10 = Alumno("10","Lucas","Pacheco","Díaz",2.8f,6.5f,3.0f)
    //1. Aquí se adjudican los alumnos al módulo
    modulo.matricularAlumno(alumno1)
    modulo.matricularAlumno(alumno2)
    modulo.matricularAlumno(alumno3)
    modulo.matricularAlumno(alumno4)
    modulo.matricularAlumno(alumno5)
    modulo.matricularAlumno(alumno6)
    modulo.matricularAlumno(alumno7)
    modulo.matricularAlumno(alumno8)
    modulo.matricularAlumno(alumno9)
    modulo.matricularAlumno(alumno10)
    println(modulo.numeroAprobados("1"))

}