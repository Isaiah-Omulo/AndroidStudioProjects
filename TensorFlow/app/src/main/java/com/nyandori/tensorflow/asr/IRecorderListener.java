package com.nyandori.tensorflow.asr;

public interface IRecorderListener {

    void onUpdateReceived(String message);

    void onDataReceived(float[] samples);
}
