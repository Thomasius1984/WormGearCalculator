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

    private val _gamma_degrees = MutableStateFlow(15.6804)
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

    private val _d_a2f = MutableStateFlow(24.0)   // Kopfkreisdurchmesser Rad (Eingabe)
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

    fun getm_x(m_n: Double, gamma_degrees: Double): Double {
        return m_n / cos(gamma_degrees*PI/180)
    }

    fun getgamma_Rad(z1: Double, m_x: Double, d_m1: Double): Double {
        return atan(z1 * m_x / d_m1)
    }

    fun getgamma_Grad(gamma_Rad: Double): Double {
        return gamma_Rad * 180 / PI

    }

    fun getx_f(a: Double, d_m1: Double, m_x: Double, z2: Double): Double {
        return (2 * a - d_m1 - m_x * z2) / (2 * m_x)
    }



    fun getp_n(m_n: Double): Double {
        return m_n * PI
    }

    fun getp_x(m_x: Double): Double {
        return m_x * PI
    }

    fun getp_z(p_x: Double, z1: Double): Double {
        return p_x * z1
    }

    fun getp(p_z: Double): Double {
        return p_z/2/PI
    }

    // =============================
    // Schnecke
    // =============================

    fun getham_1f(gammaDeg: Double): Double {
        return  cos(gammaDeg*PI/180)
    }

    fun getham_1(ham_1f:Double, m_x:Double): Double {
        return ham_1f * m_x
    }

    fun getda_1(d_m1: Double, ham_1: Double): Double {
        return d_m1 + 2.0 * ham_1
    }

    fun getra_1(da_1: Double): Double {
        return da_1 / 2.0
    }

    fun gethfm_1(m_X: Double, hFf1f: Double, cf1f: Double): Double {
        return (hFf1f + cf1f) * m_X
    }

    fun getdf_1(d_m1: Double, hfm_1: Double): Double {
        return d_m1 - 2.0 * hfm_1
    }

    fun getrf_1(df_1: Double): Double {
        return df_1 / 2.0
    }

    fun geth_1(hfm_1: Double, ham_1: Double): Double {
        return hfm_1 + ham_1
    }

    fun getc_1(a: Double, da_1: Double, df_2: Double): Double {
        return a - (da_1/2 + df_2/2)
    }

    // =============================
    // Rad
    // =============================

    fun getd_2(m_x: Double, z2: Double): Double {
        return m_x * z2
    }

    fun getx_m(x_f: Double, m_x: Double): Double {
        return x_f * m_x
    }

    fun getdm_2(d_2: Double, x_m: Double): Double {
        return d_2 + (2.0 * x_m)
    }

    fun getham_2f(d_a2f: Double, d_2: Double, m_x: Double, x_f: Double): Double {
        return (d_a2f - d_2) / (2 * m_x) - x_f
    }

    fun getham_2(ham_2f: Double, m_x: Double): Double {
        return ham_2f * m_x
    }

    fun getda_2(dm_2: Double, ham_2: Double): Double {
        return dm_2 + 2 * ham_2
    }

    fun gethfm_2(hFf2f: Double, cf2f: Double, m_x: Double): Double {
        return (hFf2f + cf2f) * m_x
    }

    fun getdf_2(dm_2: Double, hfm_2: Double): Double {
        return dm_2 - 2.0 * hfm_2
    }

    fun geth_2(ham_2: Double, hfm_2: Double): Double {
        return ham_2 + hfm_2
    }

    fun getc_2(a: Double, da_2: Double, df_1: Double): Double {
        return a - (da_2/2 + df_1/2)
    }

    fun geta(d_m1: Double, dm_2: Double): Double {
        return (d_m1 + dm_2)/2
    }

    fun getu(z2: Double, z1: Double): Double {
        return z2 / z1
    }

    // =============================
    // Validierung (wahrscheinlich müssen noch andere variablen auf 0 überprüft werden
    // =============================

    private fun isValid(m_n: Double, z1: Double, z2: Double, a: Double, d_m1: Double, d_a2f: Double): Boolean {
        return m_n > 0 &&
                z1 > 0 &&
                z2 > 0 &&
                a > 0 &&
                d_m1 > 0
                d_a2f > 0
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
        gammaDeg: Double,
        d_a2f: Double
    ): List<ResultItem> {
        // Überprüfe Input Variable auf 0, wenn 0 dann keine Berechnungen
        if (!isValid(m_n, z1, z2, a, d_m1, d_a2f)) {
            return emptyList()
        }
        // Rufe alle get Funktionen zum berechnen auf und speichere Wert in eigener Variable
        // Berechne Grundfunktionen

        val resultm_x = getm_x(m_n, gammaDeg)
        val resultgamma_Rad = getgamma_Rad(z1, resultm_x, d_m1)
        val resultgamma_Grad = getgamma_Grad(resultgamma_Rad)
        val resultx_f = getx_f(a, d_m1, resultm_x, z2)
        val resultp_n = getp_n(m_n)
        val resultp_x = getp_x (resultm_x)
        val resultp_z = getp_z (resultp_x, z1)
        val resultp = getp (resultp_z)

        // Berechne Rad
        val resultd_2 = getd_2(resultm_x,z2)
        val resultx_m = getx_m(resultx_f,resultm_x)
        val resultdm_2 = getdm_2(resultd_2, resultx_m)
        val resultham_2f = getham_2f(d_a2f, resultd_2, resultm_x, resultx_f)
        val resultham_2 = getham_2(resultham_2f, resultm_x)
        val resultda_2 = getda_2(resultdm_2, resultham_2)
        val resulthfm_2 = gethfm_2(hFf2f, cf2f, resultm_x)
        val resultdf_2 = getdf_2(resultdm_2, resulthfm_2)
        val resulth_2 = geth_2(resultham_2, resulthfm_2)


        // Berechnung Schnecke
        val resultham_1f = getham_1f (gammaDeg)
        val resultham_1 = getham_1(resultham_1f, resultm_x)
        val resultda_1 = getda_1(d_m1, resultham_1)
        val resultra_1 = getra_1(resultda_1)
        val resulthfm_1 = gethfm_1(resultm_x, hFf1f, cf1f)
        val resultdf_1 = getdf_1(d_m1, resulthfm_1)
        val resultrf_1 = getrf_1(resultdf_1)
        val resulth_1 = geth_1(resulthfm_1, resultham_1)

        // Allgemeines
        val resultc_1 = getc_1(a, resultda_1, resultdf_2)
        val resultc_2 = getc_2(a, resultda_2, resultdf_1)
        val resultu = getu(z2, z1)
        val resulta = geta(d_m1, resultdm_2)





        return listOf(ResultItem("Axialmodul m_x",String.format("%.${NUMBER_PRECISION}f", resultm_x), ""),
            ResultItem("Mittensteigungswinkel in rad", String.format("%.${NUMBER_PRECISION}f", resultgamma_Rad), ""),
            ResultItem("Mittensteigungswinkel in Grad", String.format("%.${NUMBER_PRECISION}f", resultgamma_Grad), "°"),
            ResultItem("Normalteilung p_n", String.format("%.${NUMBER_PRECISION}f", resultp_n), "mm"),
            ResultItem("Axialteilung p_x", String.format("%.${NUMBER_PRECISION}f", resultp_x), "mm"),
            ResultItem("Schneckenganghöhe p_z", String.format("%.${NUMBER_PRECISION}f", resultp_z), "mm"),
            ResultItem("Schraubparameter p", String.format("%.${NUMBER_PRECISION}f", resultp), "mm"),
            ResultItem("Kopfhöhenfaktor Schnecke ham_1f", String.format("%.${NUMBER_PRECISION}f", resultham_1f), ""),
            ResultItem("Zahnkopfhöhe Schnecke ham_1", String.format("%.${NUMBER_PRECISION}f", resultham_1), "mm"),
            ResultItem("Kopfkreisdurchmesser Schnecke da_1",String.format("%.${NUMBER_PRECISION}f", resultda_1),"mm"),
            ResultItem("Kopfkreisradius Schnecke ra_1",String.format("%.${NUMBER_PRECISION}f", resultra_1),"mm"),
            ResultItem("Zahnfußhöhe Schnecke hfm_1",String.format("%.${NUMBER_PRECISION}f", resulthfm_1),"mm"),
            ResultItem("Fußkreisdurchmesser Schnecke df_1", String.format("%.${NUMBER_PRECISION}f", resultdf_1), "mm"),
            ResultItem("Fußkreisradius Schnecke rf_1", String.format("%.${NUMBER_PRECISION}f", resultrf_1), "mm"),
            ResultItem("Zahnhöhe Schnecke h_1", String.format("%.${NUMBER_PRECISION}f", resulth_1), "mm"),
            ResultItem("Teilkreisdurchmesser Rad d_2", String.format("%.${NUMBER_PRECISION}f", resultd_2), "mm"),
            ResultItem("Profilverschiebungsfaktor x_f",String.format("%.${NUMBER_PRECISION}f", resultx_f),""),
            ResultItem("Profilverschiebung x_m",String.format("%.${NUMBER_PRECISION}f", resultx_m), "mm"),
            ResultItem("Mittenkreisdurchmesser Rad dm_2",String.format("%.${NUMBER_PRECISION}f", resultdm_2), "mm"),
            ResultItem("Kopfhöhenfaktor Rad ham_2f",String.format("%.${NUMBER_PRECISION}f", resultham_2f), ""),
            ResultItem("Zahnkopfhöhe Rad ham_2",String.format("%.${NUMBER_PRECISION}f", resultham_2), "mm"),
            ResultItem("Kopfkreisdurchmesser Rad da_2",String.format("%.${NUMBER_PRECISION}f", resultda_2), "mm"),
            ResultItem("Zahnfußhöhe Rad hfm_2", String.format("%.${NUMBER_PRECISION}f", resulthfm_2),"mm"),
            ResultItem("Fußkreisdurchmesser Rad df_2", String.format("%.${NUMBER_PRECISION}f", resultdf_2),"mm"),
            ResultItem("Zahnhöhe Rad h_2", String.format("%.${NUMBER_PRECISION}f", resulth_2),"mm"),
            ResultItem("Kopfspiel Schnecke c_1", String.format("%.${NUMBER_PRECISION}f", resultc_1),"mm"),
            ResultItem("Kopfspiel Rad c_2", String.format("%.${NUMBER_PRECISION}f", resultc_2),"mm"),
            ResultItem("Zähnezahlverhältnis u", String.format("%.${NUMBER_PRECISION}f", resultu),"mm"),
            ResultItem("Achsabstand aus dm_1 und dm_2", String.format("%.${NUMBER_PRECISION}f", resulta),"mm")
        )
    }

}



data class ResultItem(val name: String, val value: String, val unit: String)