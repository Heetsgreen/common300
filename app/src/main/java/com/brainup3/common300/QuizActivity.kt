package com.brainup3.common300

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.brainup3.common300.databinding.ActivityQuizBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    lateinit var mAdView : AdView

    private var mCurrentPosition: Int = 1
    private var mCurrentLife: Int = 7
    private var mQuestion300List: ArrayList<Question300>? = null
    private var mRewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        loadData()

        loadLife()

        MobileAds.initialize(this)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        loadRewardedAd()

        binding.btnBack.setOnClickListener {
            finish()
        }

        val window = window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        var score: Int
        val str = binding.tvScore.text.toString()
        score = str.toInt()


        mQuestion300List = Constant.getQuestions()

        noLife()

        setFirstQuestions()

        setFirstLife()


        binding.tvChoice1.setOnClickListener {
            if (binding.tvCorrectChoice.text == "1") {
                when{
                    mCurrentPosition < mQuestion300List!!.size -> {
                        score += 200
                        binding.tvScore.setText("${score}")
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
                            val intent = Intent(this, ExplainActivity::class.java)
                            intent.putExtra("Explain", mCurrentPosition)
                            startActivity(intent)
                        }, 1500)

                        Handler().postDelayed({
                            binding.frameQuiz.startAnimation(animation2)
                            binding.tvChoice1.startAnimation(animation2)
                            binding.tvChoice2.startAnimation(animation2)
                            binding.tvChoice3.startAnimation(animation2)
                        }, 2000)


                        Handler().postDelayed({mCurrentPosition++
                            setQuestions()
                            binding.tvNow.setText("${mCurrentPosition}")
                            binding.tvNow2.text = binding.tvNow.text}, 3000)
                    } else -> {
                        score += 200
                        binding.tvScore.setText("${score}")
                        binding.tvChoice1.setBackgroundResource(R.drawable.shape_correctchoice)
                        binding.tvChoice1.isClickable = false
                        binding.tvChoice2.isClickable = false
                        binding.tvChoice3.isClickable = false
                        val intent = Intent(this, ResultActivity::class.java)

                        Handler().postDelayed({intent.putExtra("score", score)
                            startActivity(intent)
                            resetData()
                            finish()}, 2000)
                    }
                }
            } else {
                binding.tvChoice1.setBackgroundResource(R.drawable.shape_wrongchoice)
                mCurrentLife -=1
                binding.tvNowLife.setText("${mCurrentLife}")
                binding.tvChoice1.isClickable = false
                noLife()
                score -= 100
                binding.tvScore.setText("${score}")
            }
        }
        binding.tvChoice2.setOnClickListener {
            if (binding.tvCorrectChoice.text == "2") {
                when {
                    mCurrentPosition < mQuestion300List!!.size -> {
                        score += 200
                        binding.tvScore.setText("${score}")
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
                            val intent = Intent(this, ExplainActivity::class.java)
                            intent.putExtra("Explain", mCurrentPosition)
                            startActivity(intent)
                        }, 1500)

                        Handler().postDelayed({
                            binding.frameQuiz.startAnimation(animation2)
                            binding.tvChoice1.startAnimation(animation2)
                            binding.tvChoice2.startAnimation(animation2)
                            binding.tvChoice3.startAnimation(animation2)
                        }, 2000)

                        Handler().postDelayed({mCurrentPosition++
                            setQuestions()
                            binding.tvNow.setText("${mCurrentPosition}")
                            binding.tvNow2.text = binding.tvNow.text}, 3000)
                    }
                    else -> {
                        score += 200
                        binding.tvScore.setText("${score}")
                        binding.tvChoice2.setBackgroundResource(R.drawable.shape_correctchoice)
                        binding.tvChoice1.isClickable = false
                        binding.tvChoice2.isClickable = false
                        binding.tvChoice3.isClickable = false
                        val intent = Intent(this, ResultActivity::class.java)

                        Handler().postDelayed({intent.putExtra("score", score)
                            startActivity(intent)
                            resetData()
                            finish()}, 2000)
                    }
                }
            } else {
                binding.tvChoice2.setBackgroundResource(R.drawable.shape_wrongchoice)
                mCurrentLife -=1
                binding.tvNowLife.setText("${mCurrentLife}")
                binding.tvChoice2.isClickable = false
                noLife()
                score -= 100
                binding.tvScore.setText("${score}")
            }
        }
        binding.tvChoice3.setOnClickListener {
            if (binding.tvCorrectChoice.text == "3") {
                when {
                    mCurrentPosition < mQuestion300List!!.size -> {
                        score += 200
                        binding.tvScore.setText("${score}")
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
                            val intent = Intent(this, ExplainActivity::class.java)
                            intent.putExtra("Explain", mCurrentPosition)
                            startActivity(intent)
                        }, 1500)

                        Handler().postDelayed({
                            binding.frameQuiz.startAnimation(animation2)
                            binding.tvChoice1.startAnimation(animation2)
                            binding.tvChoice2.startAnimation(animation2)
                            binding.tvChoice3.startAnimation(animation2)
                        }, 2000)

                        Handler().postDelayed({mCurrentPosition++
                            setQuestions()
                            binding.tvNow.setText("${mCurrentPosition}")
                            binding.tvNow2.text = binding.tvNow.text}, 3000)
                    }
                    else -> {
                        score += 200
                        binding.tvScore.setText("${score}")
                        binding.tvChoice3.setBackgroundResource(R.drawable.shape_correctchoice)
                        binding.tvChoice1.isClickable = false
                        binding.tvChoice2.isClickable = false
                        binding.tvChoice3.isClickable = false
                        val intent = Intent(this, ResultActivity::class.java)

                        Handler().postDelayed({intent.putExtra("score", score)
                            startActivity(intent)
                            resetData()
                            finish()}, 2000)
                    }
                }
            } else {
                binding.tvChoice3.setBackgroundResource(R.drawable.shape_wrongchoice)
                mCurrentLife -=1
                binding.tvChoice3.isClickable = false
                binding.tvNowLife.setText("${mCurrentLife}")
                noLife()
                score -= 100
                binding.tvScore.setText("${score}")
            }
        }
    }

    private fun noLife() {
        if(mCurrentLife < 1) {
            saveData()
            val dialog = NoLifeDialog()
            dialog.setCancelable(false)
            dialog.setButtonClickListener(object: NoLifeDialog.onButtonClickListener{
                override fun onButtonGetLifeClicked() {
                    showRewardedAd()
                }

                override fun onButtonToHomeClicked() {
                    finish()
                }
            })
            dialog.show(supportFragmentManager, "NoLifeDialog")
        }
    }

    private fun showRewardedAd() {
        if (mRewardedAd != null) {
            if (mRewardedAd!!.isLoaded) {
                mRewardedAd?.show(this, object : RewardedAdCallback() {
                    override fun onUserEarnedReward(p0: RewardItem) {
                        val rewardAmount = p0.amount
                        mCurrentLife = rewardAmount
                        binding.tvNowLife.setText("$rewardAmount")
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

        mRewardedAd = RewardedAd(this, "ca-app-pub-9838372946627279/8493593761")
        mRewardedAd?.loadAd(adRequest, object : RewardedAdLoadCallback() {
            override fun onRewardedAdFailedToLoad(p0: LoadAdError?) {
                Log.d("TAG", p0!!.message)
                mRewardedAd = null
            }

            override fun onRewardedAdLoaded() {
            }
        })
    }

    private fun setFirstLife() {
        if (binding.tvNowLife.text.toString() == "7") {
            mCurrentLife = 7
        } else if (binding.tvNowLife.text.toString() == "6") {
            mCurrentLife -= 1
        } else if (binding.tvNowLife.text.toString() == "5") {
            mCurrentLife -= 2
        } else if (binding.tvNowLife.text.toString() == "4") {
            mCurrentLife -= 3
        } else if (binding.tvNowLife.text.toString() == "3") {
            mCurrentLife -= 4
        } else if (binding.tvNowLife.text.toString() == "2") {
            mCurrentLife -= 5
        } else if (binding.tvNowLife.text.toString() == "1") {
            mCurrentLife -= 6
        } else if (binding.tvNowLife.text.toString() == "0") {
            noLife()
        }
    }

    private fun setFirstQuestions() {

        if (binding.tvNow2.text.toString() == "1") {
            mCurrentPosition = 1
        } else if (binding.tvNow2.text.toString() == "2") {
            mCurrentPosition += 1
        } else if (binding.tvNow2.text.toString() == "3") {
            mCurrentPosition += 2
        } else if (binding.tvNow2.text.toString() == "4") {
            mCurrentPosition += 3
        } else if (binding.tvNow2.text.toString() == "5") {
            mCurrentPosition += 4
        } else if (binding.tvNow2.text.toString() == "6") {
            mCurrentPosition += 5
        } else if (binding.tvNow2.text.toString() == "7") {
            mCurrentPosition += 6
        } else if (binding.tvNow2.text.toString() == "8") {
            mCurrentPosition += 7
        } else if (binding.tvNow2.text.toString() == "9") {
            mCurrentPosition += 8
        } else if (binding.tvNow2.text.toString() == "10") {
            mCurrentPosition += 9
        } else if (binding.tvNow2.text.toString() == "11") {
            mCurrentPosition += 10
        } else if (binding.tvNow2.text.toString() == "12") {
            mCurrentPosition += 11
        } else if (binding.tvNow2.text.toString() == "13") {
            mCurrentPosition += 12
        } else if (binding.tvNow2.text.toString() == "14") {
            mCurrentPosition += 13
        } else if (binding.tvNow2.text.toString() == "15") {
            mCurrentPosition += 14
        } else if (binding.tvNow2.text.toString() == "16") {
            mCurrentPosition += 15
        } else if (binding.tvNow2.text.toString() == "17") {
            mCurrentPosition += 16
        } else if (binding.tvNow2.text.toString() == "18") {
            mCurrentPosition += 17
        } else if (binding.tvNow2.text.toString() == "19") {
            mCurrentPosition += 18
        } else if (binding.tvNow2.text.toString() == "20") {
            mCurrentPosition += 19
        } else if (binding.tvNow2.text.toString() == "21") {
            mCurrentPosition += 20
        } else if (binding.tvNow2.text.toString() == "22") {
            mCurrentPosition += 21
        } else if (binding.tvNow2.text.toString() == "23") {
            mCurrentPosition += 22
        } else if (binding.tvNow2.text.toString() == "24") {
            mCurrentPosition += 23
        } else if (binding.tvNow2.text.toString() == "25") {
            mCurrentPosition += 24
        } else if (binding.tvNow2.text.toString() == "26") {
            mCurrentPosition += 25
        } else if (binding.tvNow2.text.toString() == "27") {
            mCurrentPosition += 26
        } else if (binding.tvNow2.text.toString() == "28") {
            mCurrentPosition += 27
        } else if (binding.tvNow2.text.toString() == "29") {
            mCurrentPosition += 28
        } else if (binding.tvNow2.text.toString() == "30") {
            mCurrentPosition += 29
        } else if (binding.tvNow2.text.toString() == "31") {
            mCurrentPosition += 30
        } else if (binding.tvNow2.text.toString() == "32") {
            mCurrentPosition += 31
        } else if (binding.tvNow2.text.toString() == "33") {
            mCurrentPosition += 32
        } else if (binding.tvNow2.text.toString() == "34") {
            mCurrentPosition += 33
        } else if (binding.tvNow2.text.toString() == "35") {
            mCurrentPosition += 34
        } else if (binding.tvNow2.text.toString() == "36") {
            mCurrentPosition += 35
        } else if (binding.tvNow2.text.toString() == "37") {
            mCurrentPosition += 36
        } else if (binding.tvNow2.text.toString() == "38") {
            mCurrentPosition += 37
        } else if (binding.tvNow2.text.toString() == "39") {
            mCurrentPosition += 38
        } else if (binding.tvNow2.text.toString() == "40") {
            mCurrentPosition += 39
        } else if (binding.tvNow2.text.toString() == "41") {
            mCurrentPosition += 40
        } else if (binding.tvNow2.text.toString() == "42") {
            mCurrentPosition += 41
        } else if (binding.tvNow2.text.toString() == "43") {
            mCurrentPosition += 42
        } else if (binding.tvNow2.text.toString() == "44") {
            mCurrentPosition += 43
        } else if (binding.tvNow2.text.toString() == "45") {
            mCurrentPosition += 44
        } else if (binding.tvNow2.text.toString() == "46") {
            mCurrentPosition += 45
        } else if (binding.tvNow2.text.toString() == "47") {
            mCurrentPosition += 46
        } else if (binding.tvNow2.text.toString() == "48") {
            mCurrentPosition += 47
        } else if (binding.tvNow2.text.toString() == "49") {
            mCurrentPosition += 48
        } else if (binding.tvNow2.text.toString() == "50") {
            mCurrentPosition += 49
        } else if (binding.tvNow2.text.toString() == "51") {
            mCurrentPosition += 50
        } else if (binding.tvNow2.text.toString() == "52") {
            mCurrentPosition += 51
        } else if (binding.tvNow2.text.toString() == "53") {
            mCurrentPosition += 52
        } else if (binding.tvNow2.text.toString() == "54") {
            mCurrentPosition += 53
        } else if (binding.tvNow2.text.toString() == "55") {
            mCurrentPosition += 54
        } else if (binding.tvNow2.text.toString() == "56") {
            mCurrentPosition += 55
        } else if (binding.tvNow2.text.toString() == "57") {
            mCurrentPosition += 56
        } else if (binding.tvNow2.text.toString() == "58") {
            mCurrentPosition += 57
        } else if (binding.tvNow2.text.toString() == "59") {
            mCurrentPosition += 58
        } else if (binding.tvNow2.text.toString() == "60") {
            mCurrentPosition += 59
        } else if (binding.tvNow2.text.toString() == "61") {
            mCurrentPosition += 60
        } else if (binding.tvNow2.text.toString() == "62") {
            mCurrentPosition += 61
        } else if (binding.tvNow2.text.toString() == "63") {
            mCurrentPosition += 62
        } else if (binding.tvNow2.text.toString() == "64") {
            mCurrentPosition += 63
        } else if (binding.tvNow2.text.toString() == "65") {
            mCurrentPosition += 64
        } else if (binding.tvNow2.text.toString() == "66") {
            mCurrentPosition += 65
        } else if (binding.tvNow2.text.toString() == "67") {
            mCurrentPosition += 66
        } else if (binding.tvNow2.text.toString() == "68") {
            mCurrentPosition += 67
        } else if (binding.tvNow2.text.toString() == "69") {
            mCurrentPosition += 68
        } else if (binding.tvNow2.text.toString() == "70") {
            mCurrentPosition += 69
        } else if (binding.tvNow2.text.toString() == "71") {
            mCurrentPosition += 70
        } else if (binding.tvNow2.text.toString() == "72") {
            mCurrentPosition += 71
        } else if (binding.tvNow2.text.toString() == "73") {
            mCurrentPosition += 72
        } else if (binding.tvNow2.text.toString() == "74") {
            mCurrentPosition += 73
        } else if (binding.tvNow2.text.toString() == "75") {
            mCurrentPosition += 74
        } else if (binding.tvNow2.text.toString() == "76") {
            mCurrentPosition += 75
        } else if (binding.tvNow2.text.toString() == "77") {
            mCurrentPosition += 76
        } else if (binding.tvNow2.text.toString() == "78") {
            mCurrentPosition += 77
        } else if (binding.tvNow2.text.toString() == "79") {
            mCurrentPosition += 78
        } else if (binding.tvNow2.text.toString() == "80") {
            mCurrentPosition += 79
        } else if (binding.tvNow2.text.toString() == "81") {
            mCurrentPosition += 80
        } else if (binding.tvNow2.text.toString() == "82") {
            mCurrentPosition += 81
        } else if (binding.tvNow2.text.toString() == "83") {
            mCurrentPosition += 82
        } else if (binding.tvNow2.text.toString() == "84") {
            mCurrentPosition += 83
        } else if (binding.tvNow2.text.toString() == "85") {
            mCurrentPosition += 84
        } else if (binding.tvNow2.text.toString() == "86") {
            mCurrentPosition += 85
        } else if (binding.tvNow2.text.toString() == "87") {
            mCurrentPosition += 86
        } else if (binding.tvNow2.text.toString() == "88") {
            mCurrentPosition += 87
        } else if (binding.tvNow2.text.toString() == "89") {
            mCurrentPosition += 88
        } else if (binding.tvNow2.text.toString() == "90") {
            mCurrentPosition += 89
        } else if (binding.tvNow2.text.toString() == "91") {
            mCurrentPosition += 90
        } else if (binding.tvNow2.text.toString() == "92") {
            mCurrentPosition += 91
        } else if (binding.tvNow2.text.toString() == "93") {
            mCurrentPosition += 92
        } else if (binding.tvNow2.text.toString() == "94") {
            mCurrentPosition += 93
        } else if (binding.tvNow2.text.toString() == "95") {
            mCurrentPosition += 94
        } else if (binding.tvNow2.text.toString() == "96") {
            mCurrentPosition += 95
        } else if (binding.tvNow2.text.toString() == "97") {
            mCurrentPosition += 96
        } else if (binding.tvNow2.text.toString() == "98") {
            mCurrentPosition += 97
        } else if (binding.tvNow2.text.toString() == "99") {
            mCurrentPosition += 98
        } else if (binding.tvNow2.text.toString() == "100") {
            mCurrentPosition += 99
        } else {
            mCurrentPosition = 1
        }

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

        binding.tvStage.setText("${mCurrentPosition}/100")

        binding.tvChoice1.setBackgroundResource(R.drawable.shape_problem)
        binding.tvChoice2.setBackgroundResource(R.drawable.shape_problem)
        binding.tvChoice3.setBackgroundResource(R.drawable.shape_problem)
    }

    private fun setQuestions() {
        val question = mQuestion300List!![mCurrentPosition-1]

        binding.tvChoice1.isClickable = true
        binding.tvChoice2.isClickable = true
        binding.tvChoice3.isClickable = true

        binding.imgProblem.setImageResource(question.img)
        binding.tvChoice1.text = question.choice1
        binding.tvChoice2.text = question.choice2
        binding.tvChoice3.text = question.choice3
        binding.tvCorrectChoice.text = question.correctChoice

        binding.tvStage.setText("${mCurrentPosition}/100")

        binding.tvChoice1.setBackgroundResource(R.drawable.shape_problem)
        binding.tvChoice2.setBackgroundResource(R.drawable.shape_problem)
        binding.tvChoice3.setBackgroundResource(R.drawable.shape_problem)
    }

    private fun loadLife() {
        if (binding.tvNowLife.text.toString() == "7") {
            mCurrentLife -= 0
        } else if (binding.tvNowLife.text.toString() == "6") {
            mCurrentLife -= 1
        } else if (binding.tvNowLife.text.toString() == "5") {
            mCurrentLife -= 2
        } else if (binding.tvNowLife.text.toString() == "4") {
            mCurrentLife -= 3
        } else if (binding.tvNowLife.text.toString() == "3") {
            mCurrentLife -= 4
        } else if (binding.tvNowLife.text.toString() == "2") {
            mCurrentLife -= 5
        } else if (binding.tvNowLife.text.toString() == "1") {
            mCurrentLife -= 6
        } else if (binding.tvNowLife.text.toString() == "0") {
            mCurrentLife -= 7
        }
    }

    private fun saveData() {
        val pref = getSharedPreferences("now", 0)
        val pref_life = getSharedPreferences("life", 0)
        val pref_score = getSharedPreferences("score", 0)
        val edit = pref.edit()
        val edit_life = pref_life.edit()
        val edit_score = pref_score.edit()
        edit.putString("now", binding.tvNow2.text.toString())
        edit_life.putString("life", binding.tvNowLife.text.toString())
        edit_score.putString("score", binding.tvScore.text.toString())
        edit.commit()
        edit_life.commit()
        edit_score.commit()
    }

    private fun resetData() {
        binding.tvNow2.setText("1")
        binding.tvScore.setText("0")
    }

    private fun loadData() {
        val pref = getSharedPreferences("now", 0)
        val pref_life = getSharedPreferences("life", 0)
        val pref_score = getSharedPreferences("score", 0)
        binding.tvNow2.setText(pref.getString("now", "1"))
        binding.tvNowLife.setText(pref_life.getString("life", "7"))
        binding.tvScore.setText(pref_score.getString("score", "0"))
    }

    override fun onDestroy() {
        saveData()

        super.onDestroy()

    }
}