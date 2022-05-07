package com.example.workout_app.ui.exercise

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.workout_app.R
import com.example.workout_app.data.Exercise
import com.example.workout_app.databinding.ActivityExerciseBinding
import com.example.workout_app.databinding.ConfirmationDialogBinding
import com.example.workout_app.service.AudioPlayerService
import com.example.workout_app.ui.finish.FinishActivity
import com.example.workout_app.utils.InjectorUtils
import com.example.workout_app.viewmodel.exercise.ExerciseViewModel
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var exerciseActivity: ActivityExerciseBinding? = null

    private var countdownTimer: CountDownTimer? = null
    private var countdownProgress: Int = 0

    private var viewModel: ExerciseViewModel? = null

    private var currentExercise: Exercise? = null

    private var exerciseCountdownTimer: CountDownTimer? = null
    private var exerciseCountdownProgress: Int = 0

    private var textToSpeech: TextToSpeech? = null
    private var audioPlayerService: AudioPlayerService = AudioPlayerService()

    private var exerciseAdapter: ExerciseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseActivity = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(exerciseActivity?.root)
        init()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Text to speech not supported", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Text to speech initialization failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter(viewModel?.getExercises()?.value!!)
        exerciseActivity?.exerciseIndicatorRecyclerView?.adapter = exerciseAdapter
        exerciseActivity?.exerciseIndicatorRecyclerView?.layoutManager =
            exerciseAdapter?.getLayoutManager(this)
        exerciseAdapter?.setDividerDecoration(exerciseActivity?.exerciseIndicatorRecyclerView!!)
    }

    private fun speakOutCurrentExercise() {
        currentExercise?.let {
            val textToBeSpoken = it.name
            textToSpeech?.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this, InjectorUtils.provideExerciseViewModelFactory(this)).get(
            ExerciseViewModel::class.java
        )
        viewModel?.fillUpExercises()
        viewModel?.getCurrentExercise()?.observe(this) {
            currentExercise = viewModel?.getExercises()?.value?.get(it)
        }
        textToSpeech = TextToSpeech(this, this)
        buildRestView()
        setupRecyclerView()
    }

    private fun buildRestView() {
        buildToolbar()
        setupRestView()
    }

    private fun buildToolbar() {
        val toolbar: Toolbar? = exerciseActivity?.exerciseToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener {
            reset()
            showCustomDialogForBackButton()
        }
    }

    private fun reset() {
        viewModel?.resetExercise()
    }

    private fun setupRestView() {
        playRestSound()
        exerciseActivity?.title?.visibility = View.VISIBLE
        exerciseActivity?.customProgress?.visibility = View.VISIBLE

        exerciseActivity?.exerciseProgress?.visibility = View.INVISIBLE
        exerciseActivity?.exerciseTitle?.visibility = View.INVISIBLE
        exerciseActivity?.exerciseImage?.visibility = View.INVISIBLE

        countdownTimer?.let { it ->
            it.cancel()
            countdownProgress = 0
        }
        setupRestCountdown()
    }

    private fun playRestSound() {
        audioPlayerService.parseSound(applicationContext, R.raw.press_start)
        audioPlayerService.playSound()
    }

    private fun setupRestCountdown() {
        val progressBar: ProgressBar? = exerciseActivity?.progressBarCore
        val progressText: TextView? = exerciseActivity?.timer
        countdownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(timerValue: Long) {
                countdownProgress += 1
                progressBar?.progress = 5 - countdownProgress
                progressText?.text = (5 - countdownProgress).toString()
            }

            override fun onFinish() {
                startExercise()
            }
        }.start()
    }

    override fun onDestroy() {
        countdownTimer?.let { it ->
            it.cancel()
            countdownProgress = 0
        }
        exerciseCountdownTimer?.let { it ->
            it.cancel()
            exerciseCountdownProgress = 0
        }
        textToSpeech?.let { it ->
            it.stop()
            textToSpeech = null
        }
        audioPlayerService.dispose()
        exerciseActivity = null
        super.onDestroy()
    }

    private fun startExercise() {
        setupExerciseView()
    }

    private fun setupExerciseView() {
        countdownTimer?.cancel()
        exerciseActivity?.title?.visibility = View.INVISIBLE
        exerciseActivity?.customProgress?.visibility = View.INVISIBLE

        exerciseActivity?.exerciseProgress?.visibility = View.VISIBLE
        exerciseActivity?.exerciseTitle?.visibility = View.VISIBLE
        exerciseActivity?.exerciseImage?.visibility = View.VISIBLE

        exerciseActivity?.exerciseImage?.setImageResource(currentExercise!!.image)
        exerciseActivity?.exerciseTitle?.text = currentExercise!!.name
        speakOutCurrentExercise()

        exerciseCountdownTimer?.let { it ->
            it.cancel()
            exerciseCountdownProgress = 0
        }
        setupExerciseCountdown()
    }

    private fun setupExerciseCountdown() {
        val progressBar: ProgressBar? = exerciseActivity?.exerciseProgressCore
        val progressText: TextView? = exerciseActivity?.exerciseTimer
        exerciseCountdownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(timerValue: Long) {
                exerciseCountdownProgress += 1
                progressBar?.progress = 30 - exerciseCountdownProgress
                progressText?.text = (30 - exerciseCountdownProgress).toString()
            }

            override fun onFinish() {
                viewModel!!.numberOfFinishedExercises += 1
                viewModel?.setExerciseAsCompleted(viewModel?.getCurrentExercise()?.value!!)
                exerciseAdapter?.notifyItemChanged(viewModel?.getCurrentExercise()?.value!!)
                if (viewModel?.getCurrentExercise()?.value!! >= viewModel?.getExercises()?.value?.size!!) {
                    moveToFinishScreen()
                    return
                }
                viewModel?.moveToNextExercise()
                setupRestView()
            }
        }.start()
    }

    private fun moveToFinishScreen() {
        val finishIntent: Intent = Intent(this, FinishActivity::class.java)
        startActivity(finishIntent)
        finish()
    }

    private fun showCustomDialogForBackButton() {
        val customDialog: Dialog = Dialog(this)
        val dialogBinding = ConfirmationDialogBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        customDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogBinding.confirmationYes.setOnClickListener {
            customDialog.dismiss()
            viewModel?.addHistory()
            finish()
        }
        dialogBinding.confirmationNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }
}