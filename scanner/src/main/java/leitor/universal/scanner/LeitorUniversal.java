package leitor.universal.scanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import leitor.universal.scanner.databinding.LayoutBinding;

public class LeitorUniversal extends ConstraintLayout implements SurfaceHolder.Callback {
    private FormatoLeitura formatoLeitura = FormatoLeitura.TODOS;
    private SurfaceHolder surfaceHolder;
    private LayoutBinding layoutBinding;
    private CameraSource cameraSource;
    private Long lastTimeMillis;
    private boolean scaneando;
    private long segundos = 1;

    public LeitorUniversal(@NonNull Context context) {
        super(context);
        setupView();
    }

    public LeitorUniversal(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public LeitorUniversal(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    public LeitorUniversal(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView();
    }

    private void setupView() {
        layoutBinding = LayoutBinding.inflate(LayoutInflater.from(getContext()));
        layoutBinding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        surfaceHolder = layoutBinding.surfaceView.getHolder();
        addView(layoutBinding.getRoot());
    }

    @SuppressLint("MissingPermission")
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        try {
            getCamera().start(surfaceHolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private CameraSource getCamera() {
        if (cameraSource == null) {
            BarcodeDetector detector = getBarcodeDetector();

            detector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {
                }

                @Override
                public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                    if (barcodes.size() != 0) {
                        String codigo = barcodes.valueAt(0).displayValue;
                        if (codigo.isEmpty() || scaneando) return;

                        if (tempoMinimoAlcancado()) {
                            scaneando = true;
                            layoutBinding.titulo.post(() -> {
                                // Todo implementar resposta da leitura
                            });
                        }
                    }
                }
            });

            buildCamera(detector);
        }
        return cameraSource;
    }

    private boolean tempoMinimoAlcancado() {
        long currentTime = System.currentTimeMillis();

        if (lastTimeMillis != null) {
            long delay = ((currentTime - lastTimeMillis) / 1000);
            if (delay < segundos) return false;
        }

        lastTimeMillis = currentTime;
        return true;
    }

    private void buildCamera(BarcodeDetector detector) {
        cameraSource = new CameraSource.Builder(getContext(), detector)
                .setRequestedPreviewSize(800, 800)
                .setAutoFocusEnabled(true)
                .build();
    }

    @NonNull
    private BarcodeDetector getBarcodeDetector() {
        return new BarcodeDetector
                .Builder(getContext())
                .setBarcodeFormats(formatoLeitura.getFormato())
                .build();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        System.out.println("surfaceDestroyed");
        cameraSource.stop();
        cameraSource.release();
        cameraSource = null;
    }

    public void setFormatoLeitura(FormatoLeitura formatoLeitura) {
        this.formatoLeitura = formatoLeitura;
    }

    public void setIntervaloMinimoLeitura(long segundos) {
        this.segundos = segundos;
    }

    public void start() {
        surfaceHolder.addCallback(this);
    }

    public void setTitulo(String text) {
        // Todo implementar titulo
    }

    public void setDescricao(String text) {
        // Todo implementar descricao
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }
}
