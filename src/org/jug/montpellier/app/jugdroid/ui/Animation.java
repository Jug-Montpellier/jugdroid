/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Static methods for animation purpose
 * 
 * @author Eric Taix
 */
public class Animation {

	static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

	/**
	 * Animate a view with an alpha transition
	 */
	public static void alpha(View view, float fromAlpha, float toAlpha, long duration, int repeatCount) {
		if (IS_HONEYCOMB) {
			ObjectAnimatorAlpha.invoke(view, fromAlpha, toAlpha, duration, repeatCount);
		}
		else {
			AlphaAnimation alpha = new AlphaAnimation(fromAlpha, toAlpha);
			alpha.setRepeatCount(repeatCount);
			alpha.setDuration(duration);
			view.startAnimation(alpha);
		}
	}

	/**
	 * Wrap the ObjectAnimator call by a class ! This allows coding new API 11
	 * code, without a VerifyError exception: this "invoke" method won't be called
	 * until the current API version is equal or higher than HONEY_COMB and
	 * because this verification is done before the class access
	 * 
	 * @author Eric Taix
	 */
	private static final class ObjectAnimatorAlpha {
		static void invoke(View view, float fromAlpha, float toAlpha, long duration, int repeatCount) {
			ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAlpha);
			alpha.setRepeatMode(ObjectAnimator.REVERSE);
			alpha.setRepeatCount(repeatCount);
			alpha.setDuration(duration);
			alpha.start();
		}
	}
}
