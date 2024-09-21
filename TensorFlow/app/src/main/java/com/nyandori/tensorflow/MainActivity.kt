package com.nyandori.tensorflow

import android.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nyandori.tensorflow.asr.IRecorderListener
import com.nyandori.tensorflow.asr.IWhisperListener
import com.nyandori.tensorflow.asr.Recorder
import com.nyandori.tensorflow.asr.Recorder.TAG
import com.nyandori.tensorflow.asr.Whisper
import com.nyandori.tensorflow.databinding.ActivityMainBinding
import com.nyandori.tensorflow.utils.WaveUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    var spinner: Spinner? = null
    private var mWhisper: Whisper? = null
    private var mRecorder: Recorder? = null
    private var tvStatus: TextView? = null
    private var tvResult: TextView? = null
    private var fabCopy: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val waveFileName = arrayOf<String?>(null)
        val handler = Handler(Looper.getMainLooper())
        tvStatus = binding.tvStatus;

        tvResult = binding.tvResult;
        fabCopy = binding.fabCopy;
        fabCopy!!.setOnClickListener {
            // Get the text from tvResult
            val textToCopy = tvResult!!.text.toString()

            // Copy the text to the clipboard
            val clipboard: ClipboardManager =
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", textToCopy)
            clipboard.setPrimaryClip(clip)
        }
        binding.btnRecord.setOnClickListener {
            if (mRecorder != null && mRecorder!!.isInProgress) {
                Log.d(TAG, "Recording is in progress... stopping...")
                stopRecording()
            } else {
                Log.d(TAG, "Start recording...")
                startRecording()
            }
        }


        val btnMicRec = binding.btnRecord
        val btnTranscb = binding.btnTranscb
        btnTranscb.setOnClickListener { v: View? ->
            if (mRecorder != null && mRecorder!!.isInProgress) {
                Log.d(TAG, "Recording is in progress... stopping...")
                stopRecording()
            }
            if (mWhisper != null && mWhisper!!.isInProgress) {
                Log.d(TAG, "Whisper is already in progress...!")
                stopTranscription()
            } else {
                Log.d(TAG, "Start transcription...")
                val waveFilePath: String? = waveFileName[0]?.let { getFilePath(it) }
                if (waveFilePath != null) {
                    startTranscription(waveFilePath)
                }
            }
        }

        // Implementation of file spinner functionality

        // Implementation of file spinner functionality
        val files = ArrayList<String>()
        // files.add(WaveUtil.RECORDING_FILE);
        // files.add(WaveUtil.RECORDING_FILE);
        try {
            val assetFiles = assets.list("")
            for (file in assetFiles!!) {
                if (file.endsWith(".wav")) files.add(file)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val fileArray = arrayOfNulls<String>(files.size)
        files.toArray(fileArray)
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, fileArray)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner = binding.spnrFiles
        spinner!!.adapter = adapter
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                waveFileName[0] = fileArray[position]

                if (waveFileName[0] == WaveUtil.RECORDING_FILE)
                    btnMicRec.visibility = View.VISIBLE
                else
                    btnMicRec.visibility = View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Optional: Handle case when nothing is selected
            }
        }
        val extensionsToCopy = arrayOf("pcm", "bin", "wav", "tflite")
        copyAssetsWithExtensionsToDataFolder(this, extensionsToCopy)


        val modelPath: String
        val vocabPath: String
        val useMultilingual = false // TODO: change multilingual flag as per model used

        if (useMultilingual) {
            // Multilingual model and vocab
            modelPath = getFilePath("whisper-tiny.tflite")
            vocabPath = getFilePath("filters_vocab_multilingual.bin")
        } else {
            // English-only model and vocab
            modelPath = getFilePath("whisper-tiny-en.tflite")
            vocabPath = getFilePath("filters_vocab_en.bin")
        }

        mWhisper = Whisper(this)
        mWhisper!!.loadModel(modelPath, vocabPath, useMultilingual)
        mWhisper!!.setListener(object : IWhisperListener {
            override fun onUpdateReceived(message: String) {
                Log.d(TAG, "Update is received, Message: $message")
                handler.post { tvStatus!!.text = message }
                if (message == Whisper.MSG_PROCESSING) {
                    handler.post { tvResult!!.text = "" }
                } else if (message == Whisper.MSG_FILE_NOT_FOUND) {
                    // write code as per need to handled this error
                    Log.d(TAG, "File not found error...!")
                }
            }

            override fun onResultReceived(result: String) {
                Log.d(TAG, "Result: $result")
                handler.post { tvResult!!.append(result) }
            }
        })


        mRecorder = Recorder(this)
        mRecorder!!.setListener(object : IRecorderListener {
            override fun onUpdateReceived(message: String) {
                Log.d(TAG, "Update is received, Message: $message")
                handler.post { tvStatus!!.text = message }

                if (message == Recorder.MSG_RECORDING) {
                    handler.post {
                        tvResult!!.text = ""
                        btnMicRec.text = Recorder.ACTION_STOP
                    }
                } else if (message == Recorder.MSG_RECORDING_DONE) {
                    handler.post { btnMicRec.text = Recorder.ACTION_RECORD }
                }
            }

            override fun onDataReceived(samples: FloatArray) {
                //mWhisper.writeBuffer(samples)
            }
        })

// Assume this Activity is the current activity, check record permission
        checkRecordPermission()

// for debugging
testParallelProcessing()
    }

    private fun checkRecordPermission() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Record permission is granted")
        } else {
            Log.d(TAG, "Requesting record permission")
            requestPermissions(arrayOf<String>(android.Manifest.permission.RECORD_AUDIO), 0)
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Log.d(
            TAG,
            "Record permission is granted"
        ) else Log.d(TAG, "Record permission is not granted")
    }
    private fun startRecording() {
        checkRecordPermission()
        val waveFilePath: String = getFilePath(WaveUtil.RECORDING_FILE)
        mRecorder!!.setFilePath(waveFilePath)
        mRecorder!!.start()
    }
    private fun stopRecording() {
        mRecorder!!.stop()
    }
    private fun startTranscription(waveFilePath: String) {
        mWhisper!!.setFilePath(waveFilePath)
        mWhisper!!.setAction(Whisper.ACTION_TRANSCRIBE)
        mWhisper!!.start()
    }
    private fun stopTranscription() {
        mWhisper!!.stop()
    }
    private fun copyAssetsWithExtensionsToDataFolder(context: Context, extensions: Array<String>) {
        val assetManager = context.assets
        try {
            // Specify the destination directory in the app's data folder
            val destFolder = context.filesDir.absolutePath
            for (extension in extensions) {
                // List all files in the assets folder with the specified extension
                val assetFiles = assetManager.list("")
                for (assetFileName in assetFiles!!) {
                    if (assetFileName.endsWith(".$extension")) {
                        val outFile = File(destFolder, assetFileName)
                        if (outFile.exists()) continue
                        val inputStream = assetManager.open(assetFileName)
                        val outputStream: OutputStream = FileOutputStream(outFile)

                        // Copy the file from assets to the data folder
                        val buffer = ByteArray(1024)
                        var read: Int
                        while (inputStream.read(buffer).also { read = it } != -1) {
                            outputStream.write(buffer, 0, read)
                        }
                        inputStream.close()
                        outputStream.flush()
                        outputStream.close()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun getFilePath(assetName: String): String {
        val outfile = File(filesDir, assetName)
        if (!outfile.exists()) {
            Log.d(TAG, "File not found - " + outfile.absolutePath)
        }
        Log.d(TAG, "Returned asset path: " + outfile.absolutePath)
        return outfile.absolutePath
    }
    private fun testParallelProcessing() {

        // Define the file names in an array
        val fileNames = arrayOf(
            "english_test1.wav",
            "english_test2.wav",
            "english_test_3_bili.wav"
        )

        // Multilingual model and vocab
        val modelMultilingual = getFilePath("whisper-tiny-en.tflite")
        val vocabMultilingual = getFilePath("filters_vocab_multilingual.bin")

        // Perform task for multiple audio files using multilingual model
        for (fileName in fileNames) {
            val whisper = Whisper(this)
            whisper.setAction(Whisper.ACTION_TRANSCRIBE)
            whisper.loadModel(modelMultilingual, vocabMultilingual, true)
            //whisper.setListener((msgID, message) -> Log.d(TAG, message));
            val waveFilePath = getFilePath(fileName)
            whisper.setFilePath(waveFilePath)
            whisper.start()
        }

        // English-only model and vocab
        val modelEnglish = getFilePath("whisper-tiny-en.tflite")
        val vocabEnglish = getFilePath("filters_vocab_en.bin")

        // Perform task for multiple audio files using english only model
        for (fileName in fileNames) {
            val whisper = Whisper(this)
            whisper.setAction(Whisper.ACTION_TRANSCRIBE)
            whisper.loadModel(modelEnglish, vocabEnglish, false)
            //whisper.setListener((msgID, message) -> Log.d(TAG, message));
            val waveFilePath = getFilePath(fileName)
            whisper.setFilePath(waveFilePath)
            whisper.start()
        }
    }

}
