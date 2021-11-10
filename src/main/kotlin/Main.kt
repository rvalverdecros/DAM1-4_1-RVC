class Modulo(val maxAlumnos: Int = 20) {
    var numAlumnosMat = 0
    var lisAlumnos= arrayOfNulls<Alumno>(maxAlumnos)
    var lisnotas = Array(4){FloatArray(maxAlumnos) }
    fun matricularAlumno(alumno: Alumno) : Boolean{
        var matriculado = false
        try {
            lisAlumnos[lisAlumnos.indexOf(lisAlumnos.first { it == null })] = alumno
            lisnotas[0][lisAlumnos.indexOf(alumno)] = alumno.notas[0]
            lisnotas[1][lisAlumnos.indexOf(alumno)] = alumno.notas[1]
            lisnotas[2][lisAlumnos.indexOf(alumno)] = alumno.notas[2]
            lisnotas[3][lisAlumnos.indexOf(alumno)] = alumno.notas[3]
            matriculado = true
        }catch (e:NoSuchElementException){println("No puedes meter más")}
        return true
    }
}

class Alumno(val id: Int,val nombre: String, val apellido1: String, val apellido2: String, val nota1t: Float, val nota2t: Float, val nota3t: Float) {
    var notas = arrayOf(nota1t, nota2t, nota3t, calculaEvaluacionFinal())
    private fun calculaEvaluacionFinal(): Float {
        return ((nota1t + nota2t + nota3t) / 3)
    }
}

fun main() {
    val modulo = Modulo(3)

    val alumno1 = Alumno(1,"Pedro","Pacheco","Díaz",6f,5f,9f)
    val alumno2 = Alumno(1,"Pedro","Pacheco","Díaz",6f,5f,9f)
    val alumno3 = Alumno(1,"Pedro","Pacheco","Díaz",6f,5f,9f)
    val alumno4 = Alumno(10,"Pedro","Pacheco","Díaz",6f,5f,9f)
    modulo.matricularAlumno(alumno1)
    modulo.matricularAlumno(alumno2)
    modulo.matricularAlumno(alumno3)
    modulo.matricularAlumno(alumno4)
}