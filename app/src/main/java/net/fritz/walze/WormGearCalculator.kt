package net.fritz.walze

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.*

/**
 * ViewModel for Worm Gear (Schneckengetriebe) calculations
 */
class WormGearCalculator : ViewModel() {
    // MARK: - Input Parameters (Eingabe)
    private val _m_n = MutableStateFlow(1.25)  // Normalmodul (mm)
    val m_n: StateFlow<Double> = _m_n

    private val _gamma_degrees = MutableStateFlow(15.68)  // Mittensteigungswinkel (°)
    val gamma_degrees: StateFlow<Double> = _gamma_degrees

    private val _z1 = MutableStateFlow(4.0)  // Gangzahl Schnecke
    val z1: StateFlow<Double> = _z1

    private val _z2 = MutableStateFlow(16.0)  // Zähnezahl Rad
    val z2: StateFlow<Double> = _z2

    private val _a = MutableStateFlow(20.0)  // Achsabstand (mm)
    val a: StateFlow<Double> = _a

    private val _d_m1 = MutableStateFlow(18.5)  // Mittenkreisdurchmesser Schneckenwelle (mm)
    val d_m1: StateFlow<Double> = _d_m1

    private val _alf_nz = MutableStateFlow(20.0)  // Normaleingriffswinkel (°)
    val alf_nz: StateFlow<Double> = _alf_nz

    private val _ha_sternP = MutableStateFlow(1.0)  // Kopfhöhenfaktor
    val ha_sternP: StateFlow<Double> = _ha_sternP

    private val _c_sternP = MutableStateFlow(0.25)  // Fußfreiheitsfaktor
    val c_sternP: StateFlow<Double> = _c_sternP

    // Update functions
    fun setM_n(value: Double) = _m_n.tryEmit(value)
    fun setGamma_degrees(value: Double) = _gamma_degrees.tryEmit(value)
    fun setZ1(value: Double) = _z1.tryEmit(value)
    fun setZ2(value: Double) = _z2.tryEmit(value)
    fun setA(value: Double) = _a.tryEmit(value)
    fun setD_m1(value: Double) = _d_m1.tryEmit(value)
    fun setAlf_nz(value: Double) = _alf_nz.tryEmit(value)
    fun setHa_sternP(value: Double) = _ha_sternP.tryEmit(value)
    fun setC_sternP(value: Double) = _c_sternP.tryEmit(value)

    // MARK: - Computed Results (Ergebnis)

    /**
     * Mittensteigungswinkel in Radiant
     */
    fun getGamma_rad(gammaDegrees: Double): Double {
        return gammaDegrees * PI / 180.0
    }

    /**
     * Axialmodul
     */
    fun getM_x(m_n: Double, gamma_rad: Double): Double {
        return m_n / cos(gamma_rad)
    }

    /**
     * Teilkreisdurchmesser Schneckenrad
     */
    fun getD_2(m_n: Double, z2: Double): Double {
        return m_n * z2
    }

    /**
     * Profilverschiebungsfaktor
     * (2a-d_m1-Axialmodul*Z)/(2Axialmodul)
     */
    fun getX_f(a: Double, d_m1: Double, m_n: Double, gamma_rad: Double, z2: Double): Double {
        //return (a - (m_n * (z1 + z2) / 2.0)) / m_n
        return (2 * a - d_m1 - getM_x(m_n, gamma_rad) * z2)/(2 * getM_x(m_n, gamma_rad))
    }

    /**
     * Profilverschiebung
     */
    fun getX_m(x_f: Double, m_n: Double, gamma_rad: Double): Double {
        return x_f * getM_x(m_n,gamma_rad)
    }

    /**
     * Normale Teilung
     */
    fun getP_n(m_n: Double): Double {
        return PI * m_n
    }

    /**
     * Axialteilung
     */
    fun getP_x(m_x: Double): Double {
        return PI * m_x
    }

    /**
     * Schneckenganghöhe
     */
    fun getP_z(p_x: Double, z1: Double): Double {
        return p_x * z1
    }

    /**
     * Kopfkreisdurchmesser Rad
     */
    fun getDa_2(d_2: Double, ha_sternP: Double, m_n: Double, x_m: Double): Double {
        return d_2 + 2.0 * ha_sternP * m_n + 2.0 * x_m
    }

    /**
     * Zahnfußhöhe Rad
     */
    fun getHfm_2(ha_sternP: Double, c_sternP: Double, m_n: Double, x_m: Double): Double {
        return (ha_sternP + c_sternP) * m_n - x_m
    }

    /**
     * Fußkreisdurchmesser Rad
     */
    fun getDf_2(d_2: Double, hfm_2: Double): Double {
        return d_2 - 2.0 * hfm_2
    }

    /**
     * Kopfhöhe Rad
     */
    fun getHa_2(ha_sternP: Double, m_n: Double, x_m: Double): Double {
        return ha_sternP * m_n + x_m
    }

    /**
     * Zahnhöhe Rad
     */
    fun getH_2(ha_2: Double, hfm_2: Double): Double {
        return ha_2 + hfm_2
    }

    /**
     * Kopfkreisdurchmesser Schnecke
     */
    fun getDa_1(d_m1: Double, ha_sternP: Double, m_n: Double): Double {
        return d_m1 + 2.0 * ha_sternP * m_n
    }

    /**
     * Fußkreisdurchmesser Schnecke
     */
    fun getDf_1(d_m1: Double, ha_sternP: Double, c_sternP: Double, m_n: Double): Double {
        return d_m1 - 2.0 * (ha_sternP + c_sternP) * m_n
    }

    /**
     * Wirkdurchmesser
     */
    fun getD_w1(a: Double, d_2: Double): Double {
        return 2.0 * a - d_2
    }

    /**
     * Steigungshöhe am Wirkdurchmesser
     */
    fun getP_zw(p_z: Double, d_w1: Double, d_m1: Double): Double {
        return p_z * d_w1 / d_m1
    }

    /**
     * Steigungswinkel am Wirkdurchmesser
     */
    fun getGamma_w_rad(p_zw: Double, d_w1: Double): Double {
        return atan(p_zw / (PI * d_w1))
    }

    fun getGamma_w_degrees(gamma_w_rad: Double): Double {
        return gamma_w_rad * 180.0 / PI
    }

    /**
     * Eingriffsteilung
     */
    fun getP_et(p_n: Double, alf_nz: Double): Double {
        return p_n * cos(alf_nz * PI / 180.0)
    }

    /**
     * Überdeckungsgrad
     */
    fun getEpsilon_alpha(da_2: Double, d_2: Double, alf_nz: Double, p_et: Double): Double {
        val term1 = sqrt((da_2 / 2.0).pow(2) - (d_2 / 2.0 * cos(alf_nz * PI / 180.0)).pow(2))
        val term2 = d_2 / 2.0 * sin(alf_nz * PI / 180.0)
        return (term1 - term2) / p_et
    }

    /**
     * Calculate all results based on current input values
     */
    fun calculateResults(
        m_n: Double,
        gamma_degrees: Double,
        z1: Double,
        z2: Double,
        a: Double,
        d_m1: Double,
        alf_nz: Double,
        ha_sternP: Double,
        c_sternP: Double
    ): List<ResultItem> {
        if (!isValid(m_n, z1, z2, a, d_m1)) {
            return emptyList()
        }

        val gamma_rad = getGamma_rad(gamma_degrees)
        val m_x = getM_x(m_n, gamma_rad)
        val d_2 = getD_2(m_n, z2)
        val x_f = getX_f(a, d_m1, m_n, gamma_rad, z2)
        val x_m = getX_m(x_f, m_n, gamma_rad)
        val p_n = getP_n(m_n)
        val p_x = getP_x(m_x)
        val p_z = getP_z(p_x, z1)
        val da_2 = getDa_2(d_2, ha_sternP, m_n, x_m)
        val hfm_2 = getHfm_2(ha_sternP, c_sternP, m_n, x_m)
        val df_2 = getDf_2(d_2, hfm_2)
        val ha_2 = getHa_2(ha_sternP, m_n, x_m)
        val h_2 = getH_2(ha_2, hfm_2)
        val da_1 = getDa_1(d_m1, ha_sternP, m_n)
        val df_1 = getDf_1(d_m1, ha_sternP, c_sternP, m_n)
        val d_w1 = getD_w1(a, d_2)
        val p_zw = getP_zw(p_z, d_w1, d_m1)
        val gamma_w_rad = getGamma_w_rad(p_zw, d_w1)
        val gamma_w_degrees = getGamma_w_degrees(gamma_w_rad)
        val p_et = getP_et(p_n, alf_nz)
        val epsilon_alpha = getEpsilon_alpha(da_2, d_2, alf_nz, p_et)

        return listOf(
            ResultItem("Axialmodul m_x", String.format("%.4f", m_x), "mm"),
            ResultItem("Teilkreisdurchmesser Rad d_2", String.format("%.2f", d_2), "mm"),
            ResultItem("Profilverschiebungsfaktor x_f", String.format("%.4f", x_f), ""),
            ResultItem("Profilverschiebung x_m", String.format("%.4f", x_m), "mm"),
            ResultItem("Mittensteigungswinkel γ", String.format("%.4f", gamma_rad), "rad"),
            ResultItem("Mittensteigungswinkel γ", String.format("%.2f", gamma_degrees), "°"),
            ResultItem("Normale Teilung p_n", String.format("%.3f", p_n), "mm"),
            ResultItem("Axialteilung p_x", String.format("%.3f", p_x), "mm"),
            ResultItem("Schneckenganghöhe p_z", String.format("%.3f", p_z), "mm"),
            ResultItem("Kopfkreisdurchmesser Rad da_2", String.format("%.2f", da_2), "mm"),
            ResultItem("Zahnfußhöhe Rad hfm_2", String.format("%.3f", hfm_2), "mm"),
            ResultItem("Fußkreisdurchmesser Rad df_2", String.format("%.2f", df_2), "mm"),
            ResultItem("Kopfhöhe Rad ha_2", String.format("%.3f", ha_2), "mm"),
            ResultItem("Zahnhöhe Rad h_2", String.format("%.3f", h_2), "mm"),
            ResultItem("Kopfkreisdurchmesser Schnecke da_1", String.format("%.2f", da_1), "mm"),
            ResultItem("Fußkreisdurchmesser Schnecke df_1", String.format("%.2f", df_1), "mm"),
            ResultItem("Wirkdurchmesser d_w1", String.format("%.2f", d_w1), "mm"),
            ResultItem("Steigungswinkel Wirkdurchmesser γ_w", String.format("%.2f", gamma_w_degrees), "°"),
            ResultItem("Eingriffsteilung p_et", String.format("%.3f", p_et), "mm"),
            ResultItem("Überdeckungsgrad ε_α", String.format("%.3f", epsilon_alpha), ""),
        )
    }

    private fun isValid(m_n: Double, z1: Double, z2: Double, a: Double, d_m1: Double): Boolean {
        return m_n > 0 && z1 > 0 && z2 > 0 && a > 0 && d_m1 > 0
    }
}

data class ResultItem(val name: String, val value: String, val unit: String)
