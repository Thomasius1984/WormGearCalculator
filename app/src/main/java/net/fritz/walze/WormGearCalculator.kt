package net.fritz.walze

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.*

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

    fun gammaRad(gammaDeg: Double) = gammaDeg * PI / 180.0
    fun m_x(m_n: Double, gammaRad: Double) = m_n / cos(gammaRad)
    fun d_2(m_x: Double, z2: Double) = m_x * z2

    fun x_f(a: Double, d_m1: Double, m_x: Double, z2: Double) =
        (2 * a - d_m1 - m_x * z2) / (2 * m_x)

    fun x_m(x_f: Double, m_x: Double) = x_f * m_x

    // =============================
    // Rad
    // =============================

    fun hfm_2(hFf2f: Double, cf2f: Double, m_n: Double, x_m: Double) =
        (hFf2f + cf2f) * m_n - x_m

    fun df_2(d_2: Double, hfm_2: Double) =
        d_2 - 2.0 * hfm_2

    fun ha_2(hFf2f: Double, m_n: Double, x_m: Double) =
        hFf2f * m_n + x_m

    // =============================
    // Schnecke
    // =============================

    fun da_1(d_m1: Double, hFf1f: Double, m_n: Double) =
        d_m1 + 2.0 * hFf1f * m_n

    fun df_1(d_m1: Double, hFf1f: Double, cf1f: Double, m_n: Double) =
        d_m1 - 2.0 * (hFf1f + cf1f) * m_n

    // =============================
    // Validierung
    // =============================

    fun isValid(): Boolean =
        m_n.value > 0 &&
                z1.value > 0 &&
                z2.value > 0 &&
                a.value > 0 &&
                d_m1.value > 0
}
