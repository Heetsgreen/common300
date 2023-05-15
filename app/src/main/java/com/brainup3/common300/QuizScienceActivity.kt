package com.brainup3.common300

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.brainup3.common300.databinding.ActivityQuizScienceBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class QuizScienceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizScienceBinding

    lateinit var mAdView : AdView

    private var hCurrentPosition: Int = 1
    private var mCurrentLife: Int = 7
    private var hQuestion300List: ArrayList<Question300>? = null
    private var mRewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizScienceBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        loadData()

        loadLife()

        MobileAds.initialize(this) {}

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
        val str = binding.tvScoreScience.text.toString()
        score = str.toInt()

        hQuestion300List = Constant_science.getQuestions()

        noLife()

        setFirstQuestions()

        setFirstLife()


        binding.tvChoice1.setOnClickListener {
            if (binding.tvCorrectChoice.text == "1") {
                when{
                    hCurrentPosition < hQuestion300List!!.size -> {
                        score += 200
                        binding.tvScoreScience.setText("${score}")
                        binding.tvChoice1.setBackgroundResource(R.drawable.shape_correctchoice)
                        binding.tvChoice1.isClickable = false
                        binding.tvChoice2.isClickable = false
                        binding.tvChoice3.isClickable = false

                        Handler().postDelayed({hCurrentPosition++
                            setQuestions()
                            binding.tvNowScience.setText("${hCurrentPosition}")
                            binding.tvNow2Science.text = binding.tvNowScience.text}, 2000)
                    } else -> {
                    score += 200
                    binding.tvScoreScience.setText("${score}")
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
                binding.tvScoreScience.setText("${score}")
            }
        }
        binding.tvChoice2.setOnClickListener {
            if (binding.tvCorrectChoice.text == "2") {
                when {
                    hCurrentPosition < hQuestion300List!!.size -> {
                        score += 200
                        binding.tvScoreScience.setText("${score}")
                        binding.tvChoice2.setBackgroundResource(R.drawable.shape_correctchoice)
                        binding.tvChoice1.isClickable = false
                        binding.tvChoice2.isClickable = false
                        binding.tvChoice3.isClickable = false

                        Handler().postDelayed({hCurrentPosition++
                            setQuestions()
                            binding.tvNowScience.setText("${hCurrentPosition}")
                            binding.tvNow2Science.text = binding.tvNowScience.text}, 2000)
                    }
                    else -> {
                        score += 200
                        binding.tvScoreScience.setText("${score}")
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
                binding.tvScoreScience.setText("${score}")
            }
        }
        binding.tvChoice3.setOnClickListener {
            if (binding.tvCorrectChoice.text == "3") {
                when {
                    hCurrentPosition < hQuestion300List!!.size -> {
                        score += 200
                        binding.tvScoreScience.setText("${score}")
                        binding.tvChoice3.setBackgroundResource(R.drawable.shape_correctchoice)
                        binding.tvChoice1.isClickable = false
                        binding.tvChoice2.isClickable = false
                        binding.tvChoice3.isClickable = false

                        Handler().postDelayed({hCurrentPosition++
                            setQuestions()
                            binding.tvNowScience.setText("${hCurrentPosition}")
                            binding.tvNow2Science.text = binding.tvNowScience.text}, 2000)
                    }
                    else -> {
                        score += 200
                        binding.tvScoreScience.setText("${score}")
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
                binding.tvScoreScience.setText("${score}")
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

        if (binding.tvNow2Science.text.toString() == "1") {
            hCurrentPosition = 1
        } else if (binding.tvNow2Science.text.toString() == "2") {
            hCurrentPosition += 1
        } else if (binding.tvNow2Science.text.toString() == "3") {
            hCurrentPosition += 2
        } else if (binding.tvNow2Science.text.toString() == "4") {
            hCurrentPosition += 3
        } else if (binding.tvNow2Science.text.toString() == "5") {
            hCurrentPosition += 4
        } else if (binding.tvNow2Science.text.toString() == "6") {
            hCurrentPosition += 5
        } else if (binding.tvNow2Science.text.toString() == "7") {
            hCurrentPosition += 6
        } else if (binding.tvNow2Science.text.toString() == "8") {
            hCurrentPosition += 7
        } else if (binding.tvNow2Science.text.toString() == "9") {
            hCurrentPosition += 8
        } else if (binding.tvNow2Science.text.toString() == "10") {
            hCurrentPosition += 9
        } else if (binding.tvNow2Science.text.toString() == "11") {
            hCurrentPosition += 10
        } else if (binding.tvNow2Science.text.toString() == "12") {
            hCurrentPosition += 11
        } else if (binding.tvNow2Science.text.toString() == "13") {
            hCurrentPosition += 12
        } else if (binding.tvNow2Science.text.toString() == "14") {
            hCurrentPosition += 13
        } else if (binding.tvNow2Science.text.toString() == "15") {
            hCurrentPosition += 14
        } else if (binding.tvNow2Science.text.toString() == "16") {
            hCurrentPosition += 15
        } else if (binding.tvNow2Science.text.toString() == "17") {
            hCurrentPosition += 16
        } else if (binding.tvNow2Science.text.toString() == "18") {
            hCurrentPosition += 17
        } else if (binding.tvNow2Science.text.toString() == "19") {
            hCurrentPosition += 18
        } else if (binding.tvNow2Science.text.toString() == "20") {
            hCurrentPosition += 19
        } else if (binding.tvNow2Science.text.toString() == "21") {
            hCurrentPosition += 20
        } else if (binding.tvNow2Science.text.toString() == "22") {
            hCurrentPosition += 21
        } else if (binding.tvNow2Science.text.toString() == "23") {
            hCurrentPosition += 22
        } else if (binding.tvNow2Science.text.toString() == "24") {
            hCurrentPosition += 23
        } else if (binding.tvNow2Science.text.toString() == "25") {
            hCurrentPosition += 24
        } else if (binding.tvNow2Science.text.toString() == "26") {
            hCurrentPosition += 25
        } else if (binding.tvNow2Science.text.toString() == "27") {
            hCurrentPosition += 26
        } else if (binding.tvNow2Science.text.toString() == "28") {
            hCurrentPosition += 27
        } else if (binding.tvNow2Science.text.toString() == "29") {
            hCurrentPosition += 28
        } else if (binding.tvNow2Science.text.toString() == "30") {
            hCurrentPosition += 29
        } else if (binding.tvNow2Science.text.toString() == "31") {
            hCurrentPosition += 30
        } else if (binding.tvNow2Science.text.toString() == "32") {
            hCurrentPosition += 31
        } else if (binding.tvNow2Science.text.toString() == "33") {
            hCurrentPosition += 32
        } else if (binding.tvNow2Science.text.toString() == "34") {
            hCurrentPosition += 33
        } else if (binding.tvNow2Science.text.toString() == "35") {
            hCurrentPosition += 34
        } else if (binding.tvNow2Science.text.toString() == "36") {
            hCurrentPosition += 35
        } else if (binding.tvNow2Science.text.toString() == "37") {
            hCurrentPosition += 36
        } else if (binding.tvNow2Science.text.toString() == "38") {
            hCurrentPosition += 37
        } else if (binding.tvNow2Science.text.toString() == "39") {
            hCurrentPosition += 38
        } else if (binding.tvNow2Science.text.toString() == "40") {
            hCurrentPosition += 39
        } else if (binding.tvNow2Science.text.toString() == "41") {
            hCurrentPosition += 40
        } else if (binding.tvNow2Science.text.toString() == "42") {
            hCurrentPosition += 41
        } else if (binding.tvNow2Science.text.toString() == "43") {
            hCurrentPosition += 42
        } else if (binding.tvNow2Science.text.toString() == "44") {
            hCurrentPosition += 43
        } else if (binding.tvNow2Science.text.toString() == "45") {
            hCurrentPosition += 44
        } else if (binding.tvNow2Science.text.toString() == "46") {
            hCurrentPosition += 45
        } else if (binding.tvNow2Science.text.toString() == "47") {
            hCurrentPosition += 46
        } else if (binding.tvNow2Science.text.toString() == "48") {
            hCurrentPosition += 47
        } else if (binding.tvNow2Science.text.toString() == "49") {
            hCurrentPosition += 48
        } else if (binding.tvNow2Science.text.toString() == "50") {
            hCurrentPosition += 49
        } else if (binding.tvNow2Science.text.toString() == "51") {
            hCurrentPosition += 50
        } else if (binding.tvNow2Science.text.toString() == "52") {
            hCurrentPosition += 51
        } else if (binding.tvNow2Science.text.toString() == "53") {
            hCurrentPosition += 52
        } else if (binding.tvNow2Science.text.toString() == "54") {
            hCurrentPosition += 53
        } else if (binding.tvNow2Science.text.toString() == "55") {
            hCurrentPosition += 54
        } else if (binding.tvNow2Science.text.toString() == "56") {
            hCurrentPosition += 55
        } else if (binding.tvNow2Science.text.toString() == "57") {
            hCurrentPosition += 56
        } else if (binding.tvNow2Science.text.toString() == "58") {
            hCurrentPosition += 57
        } else if (binding.tvNow2Science.text.toString() == "59") {
            hCurrentPosition += 58
        } else if (binding.tvNow2Science.text.toString() == "60") {
            hCurrentPosition += 59
        } else if (binding.tvNow2Science.text.toString() == "61") {
            hCurrentPosition += 60
        } else if (binding.tvNow2Science.text.toString() == "62") {
            hCurrentPosition += 61
        } else if (binding.tvNow2Science.text.toString() == "63") {
            hCurrentPosition += 62
        } else if (binding.tvNow2Science.text.toString() == "64") {
            hCurrentPosition += 63
        } else if (binding.tvNow2Science.text.toString() == "65") {
            hCurrentPosition += 64
        } else if (binding.tvNow2Science.text.toString() == "66") {
            hCurrentPosition += 65
        } else if (binding.tvNow2Science.text.toString() == "67") {
            hCurrentPosition += 66
        } else if (binding.tvNow2Science.text.toString() == "68") {
            hCurrentPosition += 67
        } else if (binding.tvNow2Science.text.toString() == "69") {
            hCurrentPosition += 68
        } else if (binding.tvNow2Science.text.toString() == "70") {
            hCurrentPosition += 69
        } else if (binding.tvNow2Science.text.toString() == "71") {
            hCurrentPosition += 70
        } else if (binding.tvNow2Science.text.toString() == "72") {
            hCurrentPosition += 71
        } else if (binding.tvNow2Science.text.toString() == "73") {
            hCurrentPosition += 72
        } else if (binding.tvNow2Science.text.toString() == "74") {
            hCurrentPosition += 73
        } else if (binding.tvNow2Science.text.toString() == "75") {
            hCurrentPosition += 74
        } else if (binding.tvNow2Science.text.toString() == "76") {
            hCurrentPosition += 75
        } else if (binding.tvNow2Science.text.toString() == "77") {
            hCurrentPosition += 76
        } else if (binding.tvNow2Science.text.toString() == "78") {
            hCurrentPosition += 77
        } else if (binding.tvNow2Science.text.toString() == "79") {
            hCurrentPosition += 78
        } else if (binding.tvNow2Science.text.toString() == "80") {
            hCurrentPosition += 79
        } else if (binding.tvNow2Science.text.toString() == "81") {
            hCurrentPosition += 80
        } else if (binding.tvNow2Science.text.toString() == "82") {
            hCurrentPosition += 81
        } else if (binding.tvNow2Science.text.toString() == "83") {
            hCurrentPosition += 82
        } else if (binding.tvNow2Science.text.toString() == "84") {
            hCurrentPosition += 83
        } else if (binding.tvNow2Science.text.toString() == "85") {
            hCurrentPosition += 84
        } else if (binding.tvNow2Science.text.toString() == "86") {
            hCurrentPosition += 85
        } else if (binding.tvNow2Science.text.toString() == "87") {
            hCurrentPosition += 86
        } else if (binding.tvNow2Science.text.toString() == "88") {
            hCurrentPosition += 87
        } else if (binding.tvNow2Science.text.toString() == "89") {
            hCurrentPosition += 88
        } else if (binding.tvNow2Science.text.toString() == "90") {
            hCurrentPosition += 89
        } else if (binding.tvNow2Science.text.toString() == "91") {
            hCurrentPosition += 90
        } else if (binding.tvNow2Science.text.toString() == "92") {
            hCurrentPosition += 91
        } else if (binding.tvNow2Science.text.toString() == "93") {
            hCurrentPosition += 92
        } else if (binding.tvNow2Science.text.toString() == "94") {
            hCurrentPosition += 93
        } else if (binding.tvNow2Science.text.toString() == "95") {
            hCurrentPosition += 94
        } else if (binding.tvNow2Science.text.toString() == "96") {
            hCurrentPosition += 95
        } else if (binding.tvNow2Science.text.toString() == "97") {
            hCurrentPosition += 96
        } else if (binding.tvNow2Science.text.toString() == "98") {
            hCurrentPosition += 97
        } else if (binding.tvNow2Science.text.toString() == "99") {
            hCurrentPosition += 98
        } else if (binding.tvNow2Science.text.toString() == "100") {
            hCurrentPosition += 99
        } else {
            hCurrentPosition = 1
        }

        binding.tvChoice1.isClickable = true
        binding.tvChoice2.isClickable = true
        binding.tvChoice3.isClickable = true

        val question = hQuestion300List!![hCurrentPosition-1]

        binding.imgProblem.setImageResource(question.img)
        binding.tvChoice1.text = question.choice1
        binding.tvChoice2.text = question.choice2
        binding.tvChoice3.text = question.choice3
        binding.tvCorrectChoice.text = question.correctChoice
        binding.tvNowScience.setText("${hCurrentPosition}")

        binding.tvStage.setText("${hCurrentPosition}/100")

        binding.tvChoice1.setBackgroundResource(R.drawable.shape_problem)
        binding.tvChoice2.setBackgroundResource(R.drawable.shape_problem)
        binding.tvChoice3.setBackgroundResource(R.drawable.shape_problem)
    }

    private fun setQuestions() {
        val question = hQuestion300List!![hCurrentPosition-1]

        binding.tvChoice1.isClickable = true
        binding.tvChoice2.isClickable = true
        binding.tvChoice3.isClickable = true

        binding.imgProblem.setImageResource(question.img)
        binding.tvChoice1.text = question.choice1
        binding.tvChoice2.text = question.choice2
        binding.tvChoice3.text = question.choice3
        binding.tvCorrectChoice.text = question.correctChoice

        binding.tvStage.setText("${hCurrentPosition}/100")

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
        val pref = getSharedPreferences("now_Science", 0)
        val pref_life = getSharedPreferences("life", 0)
        val pref_score = getSharedPreferences("score_Science", 0)
        val edit = pref.edit()
        val edit_life = pref_life.edit()
        val edit_score = pref_score.edit()
        edit.putString("now_Science", binding.tvNow2Science.text.toString())
        edit_life.putString("life", binding.tvNowLife.text.toString())
        edit_score.putString("score_Science", binding.tvScoreScience.text.toString())
        edit.commit()
        edit_life.commit()
        edit_score.commit()
    }

    private fun resetData() {
        binding.tvNow2Science.setText("1")
        binding.tvScoreScience.setText("0")
    }

    private fun loadData() {
        val pref = getSharedPreferences("now_Science", 0)
        val pref_life = getSharedPreferences("life", 0)
        val pref_score = getSharedPreferences("score_Science", 0)
        binding.tvNow2Science.setText(pref.getString("now_Science", "1"))
        binding.tvNowLife.setText(pref_life.getString("life", "7"))
        binding.tvScoreScience.setText(pref_score.getString("score_Science", "0"))
    }

    override fun onDestroy() {
        saveData()

        super.onDestroy()

    }
}