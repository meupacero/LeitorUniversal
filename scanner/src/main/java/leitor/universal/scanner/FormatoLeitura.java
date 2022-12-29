package leitor.universal.scanner;

import com.google.android.gms.vision.barcode.Barcode;

public enum FormatoLeitura {
    TODOS(Barcode.ALL_FORMATS),
    CODE_128(Barcode.CODE_128),
    CODE_39(Barcode.CODE_39),
    CODE_93(Barcode.CODE_93),
    CODIGO_BARRA(Barcode.CODABAR),
    DATA_MATRIX(Barcode.DATA_MATRIX),
    EAN_13(Barcode.EAN_13),
    EAN_8(Barcode.EAN_8),
    ITF(Barcode.ITF),
    QR_CODE(Barcode.QR_CODE),
    UPC_A(Barcode.UPC_A),
    UPC_E(Barcode.UPC_E),
    PDF417(Barcode.PDF417),
    AZTEC(Barcode.AZTEC),
    CONTACT_INFO(Barcode.CONTACT_INFO),
    EMAIL(Barcode.EMAIL),
    ISBN(Barcode.ISBN),
    PHONE(Barcode.PHONE),
    PRODUCT(Barcode.PRODUCT),
    SMS(Barcode.SMS),
    TEXT(Barcode.TEXT),
    URL(Barcode.URL),
    WIFI(Barcode.WIFI),
    GEO(Barcode.GEO),
    CALENDAR_EVENT(Barcode.CALENDAR_EVENT),
    DRIVER_LICENSE(Barcode.DRIVER_LICENSE);

    private final int formato;

    FormatoLeitura(int formato) {
        this.formato = formato;
    }

    public int getFormato() {
        return formato;
    }
}
