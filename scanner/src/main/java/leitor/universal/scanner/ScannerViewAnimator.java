package leitor.universal.scanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

public class ScannerViewAnimator extends RelativeLayout {
    private LinearLayout linearLayout;
    private float translationY;
    private long time = 3000L;
    private int altura_scanner_anim;

    public ScannerViewAnimator(Context context) {
        super(context);
        setupView();
    }

    public ScannerViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public ScannerViewAnimator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    public ScannerViewAnimator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView();
    }

    private void setupView() {
        linearLayout = new LinearLayout(getContext());
        linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.scanner));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        altura_scanner_anim = getResources().getDimensionPixelSize(R.dimen.altura_scanner);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                altura_scanner_anim
        );
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(linearLayout, layoutParams);

        translationY = linearLayout.getTranslationY();
        iniciarAnimacao();
    }

    private void iniciarAnimacao() {
        linearLayout.setAlpha(1f);
        linearLayout.setTranslationY(translationY);
        linearLayout.animate().withEndAction(this::iniciarAnimacao)
                .alpha(0f)
                .translationY(-getMeasuredHeight()-altura_scanner_anim)
                .setDuration(time)
                .start();
    }
}
