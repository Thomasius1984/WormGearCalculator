package net.fritz.walze

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.*

// =============================
// Constants
// =============================
const val NUMBER_PRECISION = 4

class WormGearCalculator : ViewModel() {

    // =============================
    // Eingaben
    // =============================

    private val _m_n = MutableStateFlow(1.25)
    val m_n: StateFlow<Double> = _m_n

    private val _gamma_degrees = MutableStateFlow(15.68)
    val gamma_degrees: StateFlow<Double> = _gamma_degrees

    private val _z1 = MutableStateFlow(4.0)
    val z1: StateFlow<Double> = _z1

    private val _z2 = MutableStateFlow(16.0)
    val z2: StateFlow<Double> = _z2

    private val _a = MutableStateFlow(20.0)
    val a: StateFlow<Double> = _a

    private val _d_m1 = MutableStateFlow(18.5)
    val d_m1: StateFlow<Double> = _d_m1

    private val _alf_nz = MutableStateFlow(20.0)
    val alf_nz: StateFlow<Double> = _alf_nz

    // === NEU: Fuß- und Formfaktoren ===

    private val _hFf1f = MutableStateFlow(1.0)   // Schnecke
    val hFf1f: StateFlow<Double> = _hFf1f

    private val _hFf2f = MutableStateFlow(1.0)   // Rad
    val hFf2f: StateFlow<Double> = _hFf2f

    private val _cf1f = MutableStateFlow(0.2)    // Schnecke
    val cf1f: StateFlow<Double> = _cf1f

    private val _cf2f = MutableStateFlow(0.2)    // Rad
    val cf2f: StateFlow<Double> = _cf2f

    private val _d_a2f = MutableStateFlow(0.0)   // Kopfkreisdurchmesser Rad (Eingabe)
    val d_a2f: StateFlow<Double> = _d_a2f


    // =============================
    // Setter
    // =============================

    fun setM_n(v: Double) = _m_n.tryEmit(v)
    fun setGamma_degrees(v: Double) = _gamma_degrees.tryEmit(v)
    fun setZ1(v: Double) = _z1.tryEmit(v)
    fun setZ2(v: Double) = _z2.tryEmit(v)
    fun setA(v: Double) = _a.tryEmit(v)
    fun setD_m1(v: Double) = _d_m1.tryEmit(v)
    fun setAlf_nz(v: Double) = _alf_nz.tryEmit(v)
    fun setHFf1f(v: Double) = _hFf1f.tryEmit(v)
    fun setHFf2f(v: Double) = _hFf2f.tryEmit(v)
    fun setCf1f(v: Double) = _cf1f.tryEmit(v)
    fun setCf2f(v: Double) = _cf2f.tryEmit(v)
    fun setDA2f(v: Double) = _d_a2f.tryEmit(v)

    // =============================
    // Grundfunktionen
    // =============================

    fun getGammaRad(gammaDeg: Double): Double {
        return gammaDeg * PI / 180.0
    }

    fun getm_x(m_n: Double, gammaRad: Double): Double {
        return m_n / cos(gammaRad)
    }

    fun getd_2(m_x: Double, z2: Double): Double {
        return m_x * z2
    }

    fun getx_f(a: Double, d_m1: Double, m_x: Double, z2: Double): Double {
        return (2 * a - d_m1 - m_x * z2) / (2 * m_x)
    }

    fun getx_m(x_f: Double, m_x: Double): Double {
        return x_f * m_x
    }

    // =============================
    // Rad
    // =============================

    fun gethfm_2(hFf2f: Double, cf2f: Double, m_n: Double, x_m: Double): Double {
        return (hFf2f + cf2f) * m_n - x_m
    }

    fun getdf_2(d_2: Double, hfm_2: Double): Double {
        return d_2 - 2.0 * hfm_2
    }

    fun geth_2(hFf2f: Double, m_n: Double, x_m: Double): Double {
        return hFf2f * m_n + x_m
    }

    // =============================
    // Schnecke
    // =============================

    fun getda_1(d_m1: Double, hFf1f: Double, m_n: Double): Double {
        return d_m1 + 2.0 * hFf1f * m_n
    }

    fun getdf_1(d_m1: Double, hFf1f: Double, cf1f: Double, m_n: Double): Double {
        return d_m1 - 2.0 * (hFf1f + cf1f) * m_n
    }

    // =============================
    // Validierung (wahrscheinlich müssen noch andere variablen auf 0 überprüft werden
    // =============================

    private fun isValid(m_n: Double, z1: Double, z2: Double, a: Double, d_m1: Double): Boolean {
        return m_n > 0 &&
                z1 > 0 &&
                z2 > 0 &&
                a > 0 &&
                d_m1 > 0
    }

    /**
     * Calculate all results based on current input values
     */

    fun calculateResults(
        m_n: Double,
        z1: Double,
        z2: Double,
        a: Double,
        d_m1: Double,
        cf1f: Double,
        cf2f: Double,
        hFf1f: Double,
        hFf2f: Double,
        gammaDeg: Double
    ): List<ResultItem> {
        // Überprüfe Input Variable auf 0, wenn 0 dann keine Berechnungen
        if (!isValid(m_n, z1, z2, a, d_m1)) {
            return emptyList()
        }
        // Rufe alle get Funktionen zum berechnen auf und speichere Wert in eigener Variable
        // Berechne Grundfunktionen
        val resultGammaRad = getGammaRad(gammaDeg)
        val resultm_x = getm_x(m_n, resultGammaRad)
        val resultd_2 = getd_2(resultm_x,z2)
        val resultx_f = getx_f(a, d_m1, resultm_x, z2)
        val resultx_m = getx_m(resultx_f,resultm_x)
        // Berechne Rad
        val resulthfm_2 = gethfm_2(hFf2f, cf2f, m_n, resultx_m)
        val resultdf_2 = getdf_2(resultd_2, resulthfm_2)
        val resulth_2 = geth_2(hFf2f, m_n, resultx_m)
        // Berechnung Schnecke
        val resultda_1 = getda_1(d_m1, hFf1f, m_n)
        val resultdf_1 = getdf_1(d_m1, hFf1f, cf1f, m_n)
        return listOf(ResultItem("Axialmodul m_x",String.format("%.${NUMBER_PRECISION}f", resultm_x), "mm"),
            ResultItem("Teilkreisdurchmesser Rad d_2", String.format("%.${NUMBER_PRECISION}f", resultd_2), "mm"),
            ResultItem("Profilverschiebungsfaktor x_f",String.format("%.${NUMBER_PRECISION}f", resultx_f),""),
            ResultItem("Profilverschiebung x_m",String.format("%.${NUMBER_PRECISION}f", resultx_m), "mm"),
            ResultItem("Zahnfußhöhe Rad hfm_2", String.format("%.${NUMBER_PRECISION}f", resulthfm_2),"mm"),
            ResultItem("Fußkreisdurchmesser Rad df_2", String.format("%.${NUMBER_PRECISION}f", resultdf_2),"mm"),
            ResultItem("Zahnhöhe Rad h_2", String.format("%.${NUMBER_PRECISION}f", resulth_2),"mm"),
            ResultItem("Kopfkreisdurchmesser Schnecke da_1",String.format("%.${NUMBER_PRECISION}f", resultda_1),"mm"),
            ResultItem("Fußkreisdurchmesser Schnecke df_1", String.format("%.${NUMBER_PRECISION}f", resultdf_1), "mm")
        )
    }

}



data class ResultItem(val name: String, val value: String, val unit: String)