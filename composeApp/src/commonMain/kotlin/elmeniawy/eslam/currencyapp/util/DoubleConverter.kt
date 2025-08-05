package elmeniawy.eslam.currencyapp.util

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter

/**
 * DoubleConverter
 *
 * Created by Eslam El-Meniawy on 05-Aug-2025 at 1:28 PM.
 */
class DoubleConverter : TwoWayConverter<Double, AnimationVector1D> {
    override val convertFromVector: (AnimationVector1D) -> Double = { vector ->
        vector.value.toDouble()
    }

    override val convertToVector: (Double) -> AnimationVector1D = { value ->
        AnimationVector1D(value.toFloat())
    }
}