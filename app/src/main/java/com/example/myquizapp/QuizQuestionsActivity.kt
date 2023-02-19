package com.example.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myquizapp.databinding.ActivityQuizQuestionsBinding
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {


    private var CurrentPosition: Int = 1
    private var QuestionsList: ArrayList<Question>? = null
    private var SelectedOptionsPosition: Int = 0
    private var uSerName:String?=null
    private var cOrrectAnswers:Int=0

    lateinit var binding: ActivityQuizQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uSerName=intent.getStringExtra(Constants.USER_NAME)
        QuestionsList = Constants.getQuestions()

        binding.apply {
            tvOptionOne?.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionTwo?.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionThree?.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionFour?.setOnClickListener(this@QuizQuestionsActivity)
            btnSubmit?.setOnClickListener(this@QuizQuestionsActivity)
        }

        setQuestion()
    }

    fun setQuestion() {

        val question: Question = QuestionsList!![CurrentPosition - 1]
        defultOptionsView()

        binding.apply {
            ivImage?.setImageResource(question.image)
            progressBar?.progress = CurrentPosition
            tvProgress?.text = "$CurrentPosition" + "/" + progressBar?.max
            tvQuestion?.text = question.question
            tvOptionOne?.text = question.optionOne
            tvOptionTwo?.text = question.optionTwo
            tvOptionThree?.text = question.optionThree
            tvOptionFour?.text = question.optionFour

            if (CurrentPosition == QuestionsList!!.size) {
                btnSubmit?.text = "FINISH"
            } else {
                btnSubmit?.text = "SUBMIT"
            }
        }
    }

    private fun defultOptionsView() {
        val options = ArrayList<TextView>()
        binding.apply {
            tv_option_one?.let {
                options.add(0, it)
            }

            tv_option_two?.let {
                options.add(1, it)
            }

            tv_option_three?.let {
                options.add(2, it)
            }

            tv_option_four?.let {
                options.add(3, it)
            }
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_borderbg
            )
        }
    }


    private fun selecteedOptionView(tv: TextView, selectedOptionNum: Int) {
        defultOptionsView()
        SelectedOptionsPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#7A8089"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )

    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.tv_option_one -> {
                tv_option_one?.let {
                    selecteedOptionView(it, 1)
                }

            }

            R.id.tv_option_two -> {
                tv_option_two?.let {
                    selecteedOptionView(it, 2)
                }
            }

            R.id.tv_option_three -> {
                tv_option_three?.let {
                    selecteedOptionView(it, 3)
                }
            }

            R.id.tv_option_four -> {
                tv_option_four?.let {
                    selecteedOptionView(it, 4)
                }
            }

            R.id.btn_submit -> {
                if (SelectedOptionsPosition == 0) {
                    CurrentPosition++
                    when {
                        CurrentPosition <= QuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                         val intent=Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,uSerName)
                            intent.putExtra(Constants.CORRECT_ANSWERS,cOrrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,QuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = QuestionsList?.get(CurrentPosition - 1)

                    if (question!!.correctAnswer != SelectedOptionsPosition) {
                        answerView(SelectedOptionsPosition, R.drawable.wrong_option_border)

                    }else{
                          cOrrectAnswers++
                        }
                    answerView(question.correctAnswer, R.drawable.correct_option_border)

                    if (CurrentPosition == QuestionsList!!.size) {
                        btn_submit?.text = "FINISH"
                    } else {
                        btn_submit?.text = "GO TO NEXT QUESTION"

                    }
                    SelectedOptionsPosition = 0
                }

            }
        }

    }

    private fun answerView(answer:Int, drawableView: Int) {

        when(answer) {
            1 -> {
                binding.apply {
                    tv_option_one?.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                    )
                }
            }
            2 -> {
                binding.apply {
                   tv_option_two?.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                    )
                }
            }
            3 -> {
                binding.apply {
                    tv_option_three?.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                    )
                }
            }
            4 -> {
                binding.apply {
                    tv_option_four?.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                    )
                }
            }
            else -> {
                Log.d("answer",answer.toString())
            }
        }
    }
}