package com.brainup3.common300

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.brainup3.common300.databinding.ActivityChallengeBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.text.DecimalFormat

class ChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChallengeBinding

    lateinit var mAdView : AdView

    private var mCurrentPosition: Int = 1
    private var score : Int = 0
    private var stage : Int = 1
    private var hint : Int = 3
    private var life : Int = 1
    private var mQuestion300List: ArrayList<Question300>? = null
    private var mRewardedAd: RewardedAd? = null
    private var scoreChallengeHigh: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChallengeBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        MobileAds.initialize(this)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        loadData()

        loadRewardedAd()

        binding.btnBack.setOnClickListener {
            finish()
        }

        val window = window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        mQuestion300List = Constant_challenge.getQuestions()


        setFirstQuestions()


        binding.tvChoice1.setOnClickListener {
            if (binding.tvCorrectChoice.text == "1") {
                    scorePlus()
                    scoreMoney()
                    binding.tvChoice1.setBackgroundResource(R.drawable.shape_correctchoice)
                    binding.tvChoice1.isClickable = false
                    binding.tvChoice2.isClickable = false
                    binding.tvChoice3.isClickable = false

                    val animation = AnimationUtils.loadAnimation(this, R.anim.animation_alpha)
                    val animation2 = AnimationUtils.loadAnimation(this, R.anim.animation_alpha_cycle)
                    val animation3 = AnimationUtils.loadAnimation(this, R.anim.animation_right)
                    val animation4 = AnimationUtils.loadAnimation(this, R.anim.animation_right2)
                    val animation5 = AnimationUtils.loadAnimation(this, R.anim.animation_right3)

                    Handler().postDelayed({
                        binding.frameQuiz.startAnimation(animation2)
                        binding.tvChoice1.startAnimation(animation2)
                        binding.tvChoice2.startAnimation(animation2)
                        binding.tvChoice3.startAnimation(animation2)
                    }, 2000)


                    Handler().postDelayed({
                        stage +=1
                        setQuestions()
                        binding.tvNow.setText("${stage}")
                        binding.tvNow2.text = binding.tvNow.text}, 3000)
            } else if (hint > 1 && binding.tvCorrectChoice.text == "2"){
                hint -= 1
                binding.tvNowLife.setText("${hint}")
                binding.tvChoice1.setBackgroundResource(R.drawable.shape_wrongchoice)
                binding.tvChoice1.isClickable = false
            }  else if (hint > 1 && binding.tvCorrectChoice.text == "3"){
                hint -= 1
                binding.tvNowLife.setText("${hint}")
                binding.tvChoice1.setBackgroundResource(R.drawable.shape_wrongchoice)
                binding.tvChoice1.isClickable = false
            } else {
                binding.tvChoice1.setBackgroundResource(R.drawable.shape_wrongchoice)
                binding.tvChoice1.isClickable = false
                binding.tvChoice2.isClickable = false
                binding.tvChoice3.isClickable = false
                hint -= 1
                binding.tvNowLife.setText("${hint}")
                life -= 1

                Handler().postDelayed({
                    challengeQuit()}, 2000)
            }
        }
        binding.tvChoice2.setOnClickListener {
            if (binding.tvCorrectChoice.text == "2") {
                scorePlus()
                scoreMoney()
                binding.tvChoice2.setBackgroundResource(R.drawable.shape_correctchoice)
                binding.tvChoice1.isClickable = false
                binding.tvChoice2.isClickable = false
                binding.tvChoice3.isClickable = false

                val animation = AnimationUtils.loadAnimation(this, R.anim.animation_alpha)
                val animation2 = AnimationUtils.loadAnimation(this, R.anim.animation_alpha_cycle)
                val animation3 = AnimationUtils.loadAnimation(this, R.anim.animation_right)
                val animation4 = AnimationUtils.loadAnimation(this, R.anim.animation_right2)
                val animation5 = AnimationUtils.loadAnimation(this, R.anim.animation_right3)


                Handler().postDelayed({
                    binding.frameQuiz.startAnimation(animation2)
                    binding.tvChoice1.startAnimation(animation2)
                    binding.tvChoice2.startAnimation(animation2)
                    binding.tvChoice3.startAnimation(animation2)
                }, 2000)

                Handler().postDelayed({
                    stage += 1
                    setQuestions()
                    binding.tvNow.setText("${stage}")
                    binding.tvNow2.text = binding.tvNow.text}, 3000)
            }  else if (hint > 1 && binding.tvCorrectChoice.text == "1"){
                hint -= 1
                binding.tvNowLife.setText("${hint}")
                binding.tvChoice2.setBackgroundResource(R.drawable.shape_wrongchoice)
                binding.tvChoice2.isClickable = false
            }  else if (hint > 1 && binding.tvCorrectChoice.text == "3"){
                hint -= 1
                binding.tvNowLife.setText("${hint}")
                binding.tvChoice2.setBackgroundResource(R.drawable.shape_wrongchoice)
                binding.tvChoice2.isClickable = false
            } else {
                binding.tvChoice2.setBackgroundResource(R.drawable.shape_wrongchoice)
                binding.tvChoice1.isClickable = false
                binding.tvChoice2.isClickable = false
                binding.tvChoice3.isClickable = false
                hint -= 1
                binding.tvNowLife.setText("${hint}")
                life -= 1

                Handler().postDelayed({
                    challengeQuit()}, 2000)

            }
        }
        binding.tvChoice3.setOnClickListener {
            if (binding.tvCorrectChoice.text == "3") {
                scorePlus()
                scoreMoney()
                binding.tvChoice3.setBackgroundResource(R.drawable.shape_correctchoice)
                binding.tvChoice1.isClickable = false
                binding.tvChoice2.isClickable = false
                binding.tvChoice3.isClickable = false

                val animation = AnimationUtils.loadAnimation(this, R.anim.animation_alpha)
                val animation2 = AnimationUtils.loadAnimation(this, R.anim.animation_alpha_cycle)
                val animation3 = AnimationUtils.loadAnimation(this, R.anim.animation_right)
                val animation4 = AnimationUtils.loadAnimation(this, R.anim.animation_right2)
                val animation5 = AnimationUtils.loadAnimation(this, R.anim.animation_right3)

                Handler().postDelayed({
                    binding.frameQuiz.startAnimation(animation2)
                    binding.tvChoice1.startAnimation(animation2)
                    binding.tvChoice2.startAnimation(animation2)
                    binding.tvChoice3.startAnimation(animation2)
                }, 2000)

                Handler().postDelayed({
                    stage += 1
                    setQuestions()
                    binding.tvNow.setText("${stage}")
                    binding.tvNow2.text = binding.tvNow.text}, 3000)
            }  else if (hint > 1 && binding.tvCorrectChoice.text == "1"){
                hint -= 1
                binding.tvNowLife.setText("${hint}")
                binding.tvChoice3.setBackgroundResource(R.drawable.shape_wrongchoice)
                binding.tvChoice3.isClickable = false
            }  else if (hint > 1 && binding.tvCorrectChoice.text == "2"){
                hint -= 1
                binding.tvNowLife.setText("${hint}")
                binding.tvChoice3.setBackgroundResource(R.drawable.shape_wrongchoice)
                binding.tvChoice3.isClickable = false
            } else {
                binding.tvChoice3.setBackgroundResource(R.drawable.shape_wrongchoice)
                binding.tvChoice1.isClickable = false
                binding.tvChoice2.isClickable = false
                binding.tvChoice3.isClickable = false
                hint -= 1
                binding.tvNowLife.setText("${hint}")
                life -= 1

                Handler().postDelayed({
                    challengeQuit()}, 2000)
            }
        }
    }

    private fun setFirstQuestions() {

        val randNum = (0..99).random()
        mCurrentPosition += randNum

        binding.tvChoice1.isClickable = true
        binding.tvChoice2.isClickable = true
        binding.tvChoice3.isClickable = true

        val question = mQuestion300List!![mCurrentPosition-1]

        binding.imgProblem.setImageResource(question.img)
        binding.tvChoice1.text = question.choice1
        binding.tvChoice2.text = question.choice2
        binding.tvChoice3.text = question.choice3
        binding.tvCorrectChoice.text = question.correctChoice
        binding.tvNow.setText("${mCurrentPosition}")

        binding.tvStage.setText("${stage}단계")

        binding.tvChoice1.setBackgroundResource(R.drawable.shape_problem)
        binding.tvChoice2.setBackgroundResource(R.drawable.shape_problem)
        binding.tvChoice3.setBackgroundResource(R.drawable.shape_problem)
    }

    private fun setQuestions() {
        if (mCurrentPosition <= 100) {
            val randNum = (100..300).random()
            mCurrentPosition += randNum

            val question = mQuestion300List!![mCurrentPosition-1]

            binding.tvChoice1.isClickable = true
            binding.tvChoice2.isClickable = true
            binding.tvChoice3.isClickable = true

            binding.imgProblem.setImageResource(question.img)
            binding.tvChoice1.text = question.choice1
            binding.tvChoice2.text = question.choice2
            binding.tvChoice3.text = question.choice3
            binding.tvCorrectChoice.text = question.correctChoice

            binding.tvStage.setText("${stage}단계")

            binding.tvChoice1.setBackgroundResource(R.drawable.shape_problem)
            binding.tvChoice2.setBackgroundResource(R.drawable.shape_problem)
            binding.tvChoice3.setBackgroundResource(R.drawable.shape_problem)
        } else if (101 <= mCurrentPosition && mCurrentPosition <= 500) {
            val randNum = (1..350).random()
            mCurrentPosition += randNum

            val question = mQuestion300List!![mCurrentPosition-1]

            binding.tvChoice1.isClickable = true
            binding.tvChoice2.isClickable = true
            binding.tvChoice3.isClickable = true

            binding.imgProblem.setImageResource(question.img)
            binding.tvChoice1.text = question.choice1
            binding.tvChoice2.text = question.choice2
            binding.tvChoice3.text = question.choice3
            binding.tvCorrectChoice.text = question.correctChoice

            binding.tvStage.setText("${stage}단계")

            binding.tvChoice1.setBackgroundResource(R.drawable.shape_problem)
            binding.tvChoice2.setBackgroundResource(R.drawable.shape_problem)
            binding.tvChoice3.setBackgroundResource(R.drawable.shape_problem)
        } else if (501 <= mCurrentPosition && mCurrentPosition <= 850) {
            val randNum = (1..850).random()
            mCurrentPosition = randNum

            val question = mQuestion300List!![mCurrentPosition-1]

            binding.tvChoice1.isClickable = true
            binding.tvChoice2.isClickable = true
            binding.tvChoice3.isClickable = true

            binding.imgProblem.setImageResource(question.img)
            binding.tvChoice1.text = question.choice1
            binding.tvChoice2.text = question.choice2
            binding.tvChoice3.text = question.choice3
            binding.tvCorrectChoice.text = question.correctChoice

            binding.tvStage.setText("${stage}단계")

            binding.tvChoice1.setBackgroundResource(R.drawable.shape_problem)
            binding.tvChoice2.setBackgroundResource(R.drawable.shape_problem)
            binding.tvChoice3.setBackgroundResource(R.drawable.shape_problem)
        }

    }

    private fun scorePlus() {
        if (mCurrentPosition <= 100) {
            score += 300
        } else if (101 <= mCurrentPosition && mCurrentPosition <= 500) {
            score += 700
        } else if (501 <= mCurrentPosition && mCurrentPosition <= 850) {
            score += 1000
        }
    }

    private fun scoreMoney() {
        val dec_up = DecimalFormat("#,###")
        var str_score = dec_up.format(score)
        binding.tvScore.setText("${str_score}")
        if(score >= scoreChallengeHigh) {
            scoreChallengeHigh = score
        }
    }

    private fun hint2(a: Int): Int {
        return a*2-1
    }

    private fun showRewardedAd() {
        if (mRewardedAd != null) {
            if (mRewardedAd!!.isLoaded) {
                mRewardedAd?.show(this, object : RewardedAdCallback() {
                    override fun onUserEarnedReward(p0: RewardItem) {
                        val rewardAmount = p0.amount
                        hint = rewardAmount
                        binding.tvNowLife.setText("$rewardAmount")
                        binding.tvChoice1.isClickable = true
                        binding.tvChoice2.isClickable = true
                        binding.tvChoice3.isClickable = true

                    }

                    override fun onRewardedAdClosed() {
                        loadRewardedAd()
                    }

                    override fun onRewardedAdFailedToShow(p0: AdError?) {
                        Log.d("TAG", p0!!.message)
                        mRewardedAd = null
                    }

                    override fun onRewardedAdOpened() {
                        Log.d("TAG", "onRewardedAdOpened")
                    }
                })
            }
        } else {
            Log.d("TAG", "The rewarded Ad was not loaded yet")
        }
    }

    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()

        mRewardedAd = RewardedAd(this, "ca-app-pub-9838372946627279/3637171067")
        mRewardedAd?.loadAd(adRequest, object : RewardedAdLoadCallback() {
            override fun onRewardedAdFailedToLoad(p0: LoadAdError?) {
                Log.d("TAG", p0!!.message)
                mRewardedAd = null
            }

            override fun onRewardedAdLoaded() {
            }
        })
    }

    private fun challengeQuit() {
        val dialog = ChallengeDialog()
        val dialog2 = ChallengeDialog2()
        val bundle = Bundle()
        if (life >= 0) {
            bundle.putInt("money", score)
            bundle.putInt("HighScore", scoreChallengeHigh)
            dialog.arguments = bundle
            dialog.setCancelable(false)
            dialog.setButtonClickListener(object: ChallengeDialog.onButtonClickListener{
                override fun onButtonGetLifeClicked() {
                    showRewardedAd()
                    setQuestions()
                }

                override fun onButtonToHomeClicked() {
                    finish()
                    saveData()
                }
            })
            dialog.show(supportFragmentManager, "NoLifeDialog")
        } else {
            bundle.putInt("money", score)
            bundle.putInt("HighScore", scoreChallengeHigh)
            dialog2.arguments = bundle
            dialog2.setCancelable(false)
            dialog2.setButtonClickListener(object: ChallengeDialog2.onButtonClickListener{
                override fun onButtonToHomeClicked() {
                    finish()
                    saveData()
                }
            })
            dialog2.show(supportFragmentManager, "NoLifeDialog")
        }


    }

    private fun saveData() {
        val pref = getSharedPreferences("HighScore", 0)
        val edit = pref.edit()
        edit.putInt("HighScore", scoreChallengeHigh)
        edit.apply()
    }

    private fun loadData() {
        val pref = getSharedPreferences("HighScore", 0)
        scoreChallengeHigh = pref.getInt("HighScore", 0)
    }

}