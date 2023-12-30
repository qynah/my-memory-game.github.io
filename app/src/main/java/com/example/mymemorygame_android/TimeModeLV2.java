package com.example.mymemorygame_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimeModeLV2 extends AppCompatActivity {
    TextView countdownText, tv_diemcaomoi, levelText, scoreText, tv_timeup, tv_playagain, tv_gohome, tv_playagainTimeup,tv_gohomeTimeup;
    EditText edt_tennguoimoi;
    FloatingActionButton fab_save, fab_return, fab_Pause, fab_Home, fab_PlayAgain, fab_PlayAgainTimeup,fab_HomeTimeup;
    CountDownTimer countDownTimer;
    long timeLeftInMiniseconds = 30000;
    boolean timerrunning;
//    public static int countLevel;
//    static int score;
    RelativeLayout relativeLayoutTimeUp,relativeLayoutNewRecord, Homebutton, PlayAgainbutton;

    //xử lý phần card
    ImageView iv11, iv12, iv13, iv14, iv21, iv22, iv23, iv24, iv31, iv32, iv33, iv34;
    Integer[] cardsArray = {104, 105, 106, 107, 108, 204, 205, 206, 207, 208, 1000, 2000};
    ArrayList<Integer> cardGift;
    int image104, image105, image106, image107, image108, image204, image205, image206, image207, image208, imageGift;
    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;
    int turn = 1;
    int playerPoints = 0, cpuPoints = 0;

    boolean checkHaveNewRecord = false;

    EditText newplayer;
    TextView newhighscore;

    ListView lvDiem;
    List<Item_Diemcaonhat> lsData = new ArrayList<Item_Diemcaonhat>();

    DiemCaoNhatAdapter adapter;
    DiemCaoNhatDB  db = new DiemCaoNhatDB(TimeModeLV2.this);
    int vitrixephang=0;

    int soLanLatThe=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_mode_lv2);



        timerrunning = false;
        getView();


        db.initData();
        lsData = db.getAllDiem();

        frontOfCardsResources();

        //shuffler the images
        Collections.shuffle(Arrays.asList(cardsArray));

        clickEvents();
        startStop();
        //xu ly card


//        Toast.makeText(this, lsData.get(lsData.size()-1).getTennguoichoi(), Toast.LENGTH_SHORT).show();


        //Câu 3: thay đổi backgr màn chơi khi khi chơi được 300 điểm
        scoreText.setText(TimeMode.score+"");
        if(Integer.parseInt(scoreText.getText().toString())>=300) {
            RelativeLayout layoutlv2 = findViewById(R.id.layoutlv2);
            layoutlv2.setBackgroundResource(R.color.backgrchange);
        }

    }

    public void getView() {
        countdownText = findViewById(R.id.countdownText);
        levelText  = findViewById(R.id.levelText);
        scoreText = findViewById(R.id.scoreText);

        tv_timeup = findViewById(R.id.tv_timeup);

        fab_return = findViewById(R.id.fab_return);
        fab_Pause = findViewById(R.id.fab_Pause);
        fab_save = findViewById(R.id.fab_save);

        relativeLayoutTimeUp = findViewById(R.id.layouttimeuplv2);
        relativeLayoutNewRecord = findViewById(R.id.layoutnewrecordlv2);


        Homebutton = findViewById(R.id.Homebutton);
        PlayAgainbutton = findViewById(R.id.PlayAgainbutton);
        fab_Home = findViewById(R.id.fab_Home);
        fab_PlayAgain = findViewById(R.id.fab_PlayAgain);
        tv_playagain = findViewById(R.id.tv_playagain);
        tv_gohome = findViewById(R.id.tv_gohome);


        fab_HomeTimeup = findViewById(R.id.fab_HomeTimeup);
        fab_PlayAgainTimeup = findViewById(R.id.fab_PlayAgainTimeup);
        tv_gohomeTimeup = findViewById(R.id.tv_gohomeTimeup);
        tv_playagainTimeup = findViewById(R.id.tv_playagainTimeup);

        newplayer = findViewById(R.id.edt_tennguoimoi);
        newhighscore = findViewById(R.id.tv_diemcaomoi);
        edt_tennguoimoi = findViewById(R.id.edt_tennguoimoi);
        tv_diemcaomoi = findViewById(R.id.tv_diemcaomoi);
        //gán giá trị điểm trên màn hình timemode lên màn hình good scores

        //gán giá trị được nhập trên ô tên và điểm vào biến static tenmoi và diemcaomoi
        TimeMode.tenmoi = newplayer.getText().toString();
        TimeMode.diemcaomoi = Integer.parseInt(newhighscore.getText().toString());


        //xu ly card

        iv11 = findViewById(R.id.iv11);
        iv12 = findViewById(R.id.iv12);
        iv13 = findViewById(R.id.iv13);
        iv14 = findViewById(R.id.iv14);

        iv21 = findViewById(R.id.iv21);
        iv22 = findViewById(R.id.iv22);
        iv23 = findViewById(R.id.iv23);
        iv24 = findViewById(R.id.iv24);

        iv31 = findViewById(R.id.iv31);
        iv32 = findViewById(R.id.iv32);
        iv33 = findViewById(R.id.iv33);
        iv34 = findViewById(R.id.iv34);

        iv11.setTag("0");
        iv12.setTag("1");
        iv13.setTag("2");
        iv14.setTag("3");
        iv21.setTag("4");
        iv22.setTag("5");
        iv23.setTag("6");
        iv24.setTag("7");
        iv31.setTag("8");
        iv32.setTag("9");
        iv33.setTag("10");
        iv34.setTag("11");
    }
    private void frontOfCardsResources()
    {
        image104 = R.drawable.ic_d104;
        image105 = R.drawable.ic_e105;
        image106 = R.drawable.ic_f106;
        image107 = R.drawable.ic_g107;
        image108 = R.drawable.ic_h108;
        imageGift = R.drawable.ic_gift_thuong1000;


        image204 = R.drawable.ic_d204;
        image205 = R.drawable.ic_e205;
        image206 = R.drawable.ic_f206;
        image207 = R.drawable.ic_g207;
        image208 = R.drawable.ic_h208;

    }
    // xử lý thời gian tiếp tục được giảm xuống sau khi nhấn btnContinue
    @Override
    protected void onRestart() {
        super.onRestart();
        timerrunning = false;
        scoreText.setText(TimeMode.score+"");
        if(Integer.parseInt(scoreText.getText().toString())>=300) {
            RelativeLayout layoutlv2 = findViewById(R.id.layoutlv2);
            layoutlv2.setBackgroundResource(R.color.backgrchange);
        }
        startStop();
    }


    //Chức năng đếm ngược thời gian - countdowntimer + hiện modal Time's up (hết giờ)
    public void startStop() {
        if(timerrunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }
    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMiniseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMiniseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                iv11.setEnabled(false);
                iv12.setEnabled(false);
                iv13.setEnabled(false);
                iv14.setEnabled(false);

                iv21.setEnabled(false);
                iv22.setEnabled(false);
                iv23.setEnabled(false);
                iv24.setEnabled(false);

                iv31.setEnabled(false);
                iv32.setEnabled(false);
                iv33.setEnabled(false);
                iv34.setEnabled(false);

                //duyet de so sanh diem moi cao hon diem trong bang lich su
                int dem=0;
//                Toast.makeText(getApplicationContext(), "giatridiemcaomoi la:   "+ diemcaomoi, Toast.LENGTH_SHORT).show();

                if(checkHaveNewRecord == false) {
                    for(int i=0; i<lsData.size(); i++)
                    {
                        if(lsData.get(i).getDiemso() <= Integer.parseInt(scoreText.getText().toString())) {


                            relativeLayoutNewRecord.setVisibility(View.VISIBLE);
                            newplayer.setText(lsData.get(i).getTennguoichoi());
                            newhighscore.setText(TimeMode.score + "");
                            vitrixephang = i;
                            checkHaveNewRecord = true;
                        }

                        if(checkHaveNewRecord ==  true) break;
                        dem++;
                    }
                }

                if(dem == lsData.size())
                    relativeLayoutTimeUp.setVisibility(View.VISIBLE);

            }
        }.start();
        timerrunning = true;
    }
    public void stopTimer() {
        countDownTimer.cancel();
        timerrunning = false;
    }
    public void updateTimer() {
        int minutes = (int) timeLeftInMiniseconds / 60000; // 1min = 60 sec = 60 * 1000 = 60000 milisec
        int seconds = (int) timeLeftInMiniseconds % 60000 / 1000; //1 sec = 1000 milisec => 60 sec = 60000 milisec

        String timeLeftText;
        if(minutes < 10) {
            timeLeftText = "0" + minutes;
        } else {
            timeLeftText = "" + minutes;
        }
        timeLeftText += ":";
        if(seconds<10) {
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        countdownText.setText(timeLeftText);
        //set level và score
        levelText.setText((TimeMode.countLevel+1)+"");
        scoreText.setText(TimeMode.score+"");
    }

    //xử lý các events chuyển trang
    public void clickEvents() {
        fab_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                TimeMode.score=0;
                TimeMode.countLevel=1;
                Intent intent = new Intent(TimeModeLV2.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        tv_playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeMode.score=0;
                TimeMode.countLevel=2;
                Intent intent = new Intent(getApplicationContext(), TimeModeLV2.class);
                startActivity(intent);
                finish();
            }
        });
        tv_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeMode.score=0;
                TimeMode.countLevel=1;
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        fab_PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeMode.score=0;
                TimeMode.countLevel=2;
                Intent intent = new Intent(getApplicationContext(), TimeModeLV2.class);
                startActivity(intent);
                finish();
            }
        });

        //event activity bad score
        fab_PlayAgainTimeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeMode.score=0;
                TimeMode.countLevel=2;
                Intent intent = new Intent(getApplicationContext(), TimeModeLV2.class);
                startActivity(intent);
                finish();
            }
        });
        fab_HomeTimeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                TimeMode.score=0;
                TimeMode.countLevel=1;
                Intent intent = new Intent(TimeModeLV2.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        tv_playagainTimeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeMode.score=0;
                TimeMode.countLevel=2;
                Intent intent = new Intent(getApplicationContext(), TimeModeLV2.class);
                startActivity(intent);
                finish();
            }
        });
        tv_gohomeTimeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeMode.score=0;
                TimeMode.countLevel=1;
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item_Diemcaonhat item_diemcaonhat = new Item_Diemcaonhat();
                item_diemcaonhat.xephang = lsData.get(vitrixephang).getXephang();
                item_diemcaonhat.tennguoichoi = edt_tennguoimoi.getText().toString();
                item_diemcaonhat.diemso = Integer.parseInt(tv_diemcaomoi.getText().toString());
                db.updateDiem(item_diemcaonhat);
                Toast.makeText(TimeModeLV2.this, "Cập nhật bảng điểm cao thành công!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(TimeModeLV2.this, DiemCaoNhat.class);
                startActivity(intent);
                TimeMode.score=0;
                TimeMode.countLevel=1;
                finish();
            }
        });

        //
        fab_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeMode.score=0;
                TimeMode.countLevel=1;
                Intent intent = new Intent(TimeModeLV2.this, ChooseMode.class);
                startActivity(intent);
            }
        });
        //
        fab_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TimeModeLV2.this, PauseActivity.class);
                startStop(); //dừng chạy thời gian
                startActivity(intent);
            }
        });



        //xu ly card


        //Row 1
        iv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv11, theCard);
            }
        });


        iv12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv12, theCard);
            }
        });

        iv13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv13, theCard);
            }
        });

        iv14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv14, theCard);
            }
        });
        //Row 2
        iv21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv21, theCard);
            }
        });

        iv22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv22, theCard);
            }
        });

        iv23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv23, theCard);
            }
        });

        iv24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv24, theCard);
            }
        });

        //Row 3
        iv31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv31, theCard);
            }
        });

        iv32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv32, theCard);
            }
        });
        iv33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv33, theCard);
            }
        });

        iv34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv34, theCard);
            }
        });
    }
    private void doStuff(ImageView iv, int card) {
        //set the correct image to the imageview
        if(cardsArray[card] == 104) {
            iv.setImageResource(image104);
        } else if(cardsArray[card] == 105) {
            iv.setImageResource(image105);
        } else if(cardsArray[card] == 106) {
            iv.setImageResource(image106);
        } else if(cardsArray[card] == 107) {
            iv.setImageResource(image107);
        } else if(cardsArray[card] == 108) {
            iv.setImageResource(image108);
        } else if(cardsArray[card] == 204) {
            iv.setImageResource(image204);
        } else if(cardsArray[card] == 205) {
            iv.setImageResource(image205);
        } else if(cardsArray[card] == 206) {
            iv.setImageResource(image206);
        } else if(cardsArray[card] == 207) {
            iv.setImageResource(image207);
        } else if(cardsArray[card] == 208) {
            iv.setImageResource(image208);
        } else if(cardsArray[card] == 1000) {
            iv.setImageResource(imageGift);
        } else if(cardsArray[card] == 2000) {
            iv.setImageResource(imageGift);
        }
        //check which image is selected and save it to temporary
        if(cardNumber == 1)
        {
            firstCard = cardsArray[card];
            if(firstCard > 1999)
            {
                firstCard = firstCard - 1000;
//                Toast.makeText(TimeModeLV2.this, "gia tri card: " + firstCard, Toast.LENGTH_LONG).show();

            }
            if (firstCard > 200 && firstCard< 1000)
            {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            iv.setEnabled(false);
        } else if (cardNumber == 2) {
            secondCard = cardsArray[card];
            if(secondCard > 1999)
            {
                secondCard = secondCard - 1000;
//                Toast.makeText(TimeModeLV2.this, "gia tri card: " + secondCard, Toast.LENGTH_LONG).show();

            }
            if (secondCard > 200 && secondCard < 1000)
            {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;


            iv11.setEnabled(false);
            iv12.setEnabled(false);
            iv13.setEnabled(false);
            iv14.setEnabled(false);

            iv21.setEnabled(false);
            iv22.setEnabled(false);
            iv23.setEnabled(false);
            iv24.setEnabled(false);

            iv31.setEnabled(false);
            iv32.setEnabled(false);
            iv33.setEnabled(false);
            iv34.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //check if the selected images are equal
                    calculate();
                }
            }, 1000);

        }
    }
    private void calculate() {
        //if images are equal, remove them and add points
        if(firstCard == secondCard)
        {
            if(clickedFirst == 0) {
                iv11.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 1) {
                iv12.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 2) {
                iv13.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3) {
                iv14.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 4) {
                iv21.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5) {
                iv22.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 6) {
                iv23.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 7) {
                iv24.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 8) {
                iv31.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 9) {
                iv32.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 10) {
                iv33.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 11) {
                iv34.setVisibility(View.INVISIBLE);
            }

            if(clickedSecond == 0) {
                iv11.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1) {
                iv12.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2) {
                iv13.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3) {
                iv14.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 4) {
                iv21.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5) {
                iv22.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 6) {
                iv23.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 7) {
                iv24.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 8) {
                iv31.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 9) {
                iv32.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 10) {
                iv33.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 11) {
                iv34.setVisibility(View.INVISIBLE);
            }


            //xu ly su kien cộng thêm điểm vào score
            if(firstCard == 1000 && secondCard == 1000)
            {
                TimeMode.score +=200;
            } else {
                TimeMode.score+=100;
            }
//            Toast.makeText(TimeModeLV2.this, "gia tri card: " + firstCard, Toast.LENGTH_LONG).show();
            scoreText.setText(TimeMode.score+"");


            if(Integer.parseInt(scoreText.getText().toString())>=300) {
               RelativeLayout layoutlv2 = findViewById(R.id.layoutlv2);
                layoutlv2.setBackgroundResource(R.color.backgrchange);
            }

//            if(turn == 1)
//            {
//                playerPoints++;
//                tv_p1.setText("P1: " + playerPoints);
//            } else if (turn == 2)
//            {
//                cpuPoints++;
//                tv_p2.setText("P2: " + cpuPoints);
//            }
        } else {
            iv11.setImageResource(R.drawable.ic_unknowed_img);
            iv12.setImageResource(R.drawable.ic_unknowed_img);
            iv13.setImageResource(R.drawable.ic_unknowed_img);
            iv14.setImageResource(R.drawable.ic_unknowed_img);

            iv21.setImageResource(R.drawable.ic_unknowed_img);
            iv22.setImageResource(R.drawable.ic_unknowed_img);
            iv23.setImageResource(R.drawable.ic_unknowed_img);
            iv24.setImageResource(R.drawable.ic_unknowed_img);

            iv31.setImageResource(R.drawable.ic_unknowed_img);
            iv32.setImageResource(R.drawable.ic_unknowed_img);
            iv33.setImageResource(R.drawable.ic_unknowed_img);
            iv34.setImageResource(R.drawable.ic_unknowed_img);

            //===============Câu 2: chọn sai 2 lần thì thua
            soLanLatThe++;
            if(soLanLatThe==2) {
                stopTimer();
                TextView tv_timeupLV2 = findViewById(R.id.tv_timeupLV2);
                Toast.makeText(TimeModeLV2.this, checkHaveNewRecord+"", Toast.LENGTH_SHORT).show();
                tv_timeupLV2.setText("Thua cuộc!");
                int dem=0;
//                Toast.makeText(getApplicationContext(), "giatridiemcaomoi la:   "+ diemcaomoi, Toast.LENGTH_SHORT).show();

                if(checkHaveNewRecord == false) {
                    for(int i=0; i<lsData.size(); i++)
                    {
                        if(lsData.get(i).getDiemso() <= Integer.parseInt(scoreText.getText().toString())) {
                            relativeLayoutNewRecord.setVisibility(View.VISIBLE);
                            relativeLayoutTimeUp.setVisibility(View.INVISIBLE);
                            newplayer.setText(lsData.get(i).getTennguoichoi());
                            newhighscore.setText(TimeMode.score + "");
                            vitrixephang = i;
                            checkHaveNewRecord = true;
                        }

                        if(checkHaveNewRecord ==  true) break;
                        dem++;
                    }
                }

                if(dem == lsData.size())
                    relativeLayoutTimeUp.setVisibility(View.VISIBLE);
            }

        }




        iv11.setEnabled(true);
        iv12.setEnabled(true);
        iv13.setEnabled(true);
        iv14.setEnabled(true);

        iv21.setEnabled(true);
        iv22.setEnabled(true);
        iv23.setEnabled(true);
        iv24.setEnabled(true);

        iv31.setEnabled(true);
        iv32.setEnabled(true);
        iv33.setEnabled(true);
        iv34.setEnabled(true);

        //check if the game is over
        checkEnd();
    }
    private void checkEnd() {
        if(iv11.getVisibility() == View.INVISIBLE &&
                iv12.getVisibility() == View.INVISIBLE &&
                iv13.getVisibility() == View.INVISIBLE &&
                iv14.getVisibility() == View.INVISIBLE &&
                iv21.getVisibility() == View.INVISIBLE &&
                iv22.getVisibility() == View.INVISIBLE &&
                iv23.getVisibility() == View.INVISIBLE &&
                iv24.getVisibility() == View.INVISIBLE &&
                iv31.getVisibility() == View.INVISIBLE &&
                iv32.getVisibility() == View.INVISIBLE &&
                iv33.getVisibility() == View.INVISIBLE &&
                iv34.getVisibility() == View.INVISIBLE) {

            //chuyển sang level 3
//            countLevel++;
            Toast.makeText(TimeModeLV2.this, "Chuyển sang LEVEL 3!", Toast.LENGTH_LONG).show();
        }
    }

}
