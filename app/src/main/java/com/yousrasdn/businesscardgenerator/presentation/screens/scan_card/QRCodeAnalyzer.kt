package com.yousrasdn.businesscardgenerator.presentation.screens.scan_card

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

/**
 * This class analyzes each camera frame to detect QR codes
 * 
 * How it works:
 * 1. Camera captures frames continuously
 * 2. Each frame is passed to this analyzer
 * 3. ML Kit scans the frame for QR codes
 * 4. If QR found, extract the text and call callback
 */
class QRCodeAnalyzer(
    private val onQRCodeDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {
    
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()
    
    private val scanner = BarcodeScanning.getClient(options)
    
    private var isScanning = false
    
    private var hasDetectedQR = false
    
    /**
     * This method is called for EVERY camera frame
     * @param imageProxy - The camera frame to analyze
     */
    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        
        if (hasDetectedQR) {
            imageProxy.close()
            return
        }
        
        if (mediaImage != null && !isScanning) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )
            
            isScanning = true
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty() && !hasDetectedQR) {
                        for (barcode in barcodes) {
                            barcode.rawValue?.let { qrText ->
                                hasDetectedQR = true
                                onQRCodeDetected(qrText)
                                return@addOnSuccessListener
                            }
                        }
                    }
                }
                .addOnFailureListener {
                }
                .addOnCompleteListener {
                    imageProxy.close()
                    isScanning = false
                }
        } else {
            imageProxy.close()
        }
    }
}
