package com.example.mymemorygame_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// điều còn chưa làm được
// 1. chưa thay được điểm cao mới vào bảng lịch sử điểm cao
// 2. chưa làm tương tự đối với lv2
// 3. xử lý sự kiến nhấn vào button lv 3,4 thì hiện toast
// 4. làm bài tập đề thi cũ




public class TimeMode extends AppCompatActivity {
    TextView countdownText, levelText, scoreText, tv_timeup, tv_score,tv_diemcaomoi, tv_gohome, tv_playagain, tv_playagainTimeup, tv_gohomeTimeup;
    EditText edtTen, edt_tennguoimoi;


    FloatingActionButton fab_return, fab_Pause, fab_Home, fab_PlayAgain, fab_save, fab_HomeTimeup, fab_PlayAgainTimeup;
    CountDownTimer countDownTimer;
    long timeLeftInMiniseconds = 10000;
    boolean timerrunning;
    public static int countLevel = 1;
    public static int score = 0;
    String action="";
    int id=0;
    int soLanLatThe=0;
    RelativeLayout relativeLayoutTimeUp,relativeLayoutNewRecord, Homebutton, PlayAgainbutton;

    //xử lý phần card
    ImageView iv11, iv12, iv21, iv22, iv31, iv32;
    Integer[] cardsArray = {101, 102, 201, 202, 1000, 2000};
    int image101, image102, image201, image202, imagethuong1000, imagethuong2000;
    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;
    static String tenmoi;
    static int diemcaomoi;
    boolean checkHaveNewRecord = false;

    EditText newplayer;
    TextView newhighscore;

    ListView lvDiem;
    List<Item_Diemcaonhat> lsData = new ArrayList<Item_Diemcaonhat>();

    DiemCaoNhatAdapter adapter;
    DiemCaoNhatDB  db = new DiemCaoNhatDB(TimeMode.this);
    int vitrixephang=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_mode);



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

//        Toast.makeText(TimeMode.this,"So diem lich su choi "+lsData.size(), Toast.LENGTH_SHORT).show();

//        Toast.makeText(this, lsData.get(lsData.size()-1).getTennguoichoi(), Toast.LENGTH_SHORT).show();

//        lsData.get(1);
    }

    public void getView() {
        fab_save = findViewById(R.id.fab_save);
        tv_score = findViewById(R.id.tv_score);
        countdownText = findViewById(R.id.countdownText);
        levelText  = findViewById(R.id.levelText);
        scoreText = findViewById(R.id.scoreText);
        newplayer = findViewById(R.id.edt_tennguoimoi);
        newhighscore = findViewById(R.id.tv_diemcaomoi);
        edt_tennguoimoi = findViewById(R.id.edt_tennguoimoi);
        tv_diemcaomoi = findViewById(R.id.tv_diemcaomoi);
        //gán giá trị điểm trên màn hình timemode lên màn hình good scores

        //gán giá trị được nhập trên ô tên và điểm vào biến static tenmoi và diemcaomoi
        tenmoi = newplayer.getText().toString();
        diemcaomoi = Integer.parseInt(newhighscore.getText().toString());



//        edtTen = findViewById(R.id.edt_ten);
        tv_timeup = findViewById(R.id.tv_timeup);

        fab_return = findViewById(R.id.fab_return);
        fab_Pause = findViewById(R.id.fab_Pause);

        relativeLayoutTimeUp = findViewById(R.id.layouttimeup);
        relativeLayoutNewRecord = findViewById(R.id.layoutnewrecord);

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


        //xu ly card
        iv11 = findViewById(R.id.iv11);
        iv12 = findViewById(R.id.iv12);

        iv21 = findViewById(R.id.iv21);
        iv22 = findViewById(R.id.iv22);

        iv31 = findViewById(R.id.iv31);
        iv32 = findViewById(R.id.iv32);

        iv11.setTag("0");
        iv12.setTag("1");
        iv21.setTag("2");
        iv22.setTag("3");
        iv31.setTag("4");
        iv32.setTag("5");
    }
    private void frontOfCardsResources()
    {
        image101 = R.drawable.ic_a101;
        image102 = R.drawable.ic_b102;
        imagethuong1000 = R.drawable.ic_gift_thuong1000;


        image201 = R.drawable.ic_a201;
        image202 = R.drawable.ic_b202;
        imagethuong2000 = R.drawable.ic_gift_thuong1000;

    }
    // xử lý thời gian tiếp tục được giảm xuống sau khi nhấn btnContinue
    // tạo biến time
    @Override
    protected void onRestart() {
        super.onRestart();
        timerrunning = false;
        score=0;
        scoreText.setText(score+"");
        countLevel=1;
        startStop();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        TimeMode.score=0;
//        scoreText.setText(TimeMode.score+"");
//        TimeMode.countLevel=2;
//        startStop();
//    }



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
                Collections.shuffle(Arrays.asList(cardsArray));
                //iv11.setImageResource(R.drawable.ic_a101);
                  //      iv12.setImageResource(R.drawable.ic_a201);
                    //    iv21.setImageResource(R.drawable.ic_b102);
                      //  iv22.setImageResource(R.drawable.ic_b202);
                        //iv31.setImageResource(R.drawable.ic_c103);
                       // iv32.setImageResource(R.drawable.ic_c203);


                iv11.setEnabled(false);
                iv12.setEnabled(false);

                iv21.setEnabled(false);
                iv22.setEnabled(false);

                iv31.setEnabled(false);
                iv32.setEnabled(false);

                //duyet de so sanh diem moi cao hon diem trong bang lich su
                int dem=0;
//                Toast.makeText(getApplicationContext(), "giatridiemcaomoi la:   "+ diemcaomoi, Toast.LENGTH_SHORT).show();

                if(checkHaveNewRecord == false) {
                    for(int i=0; i<lsData.size(); i++)
                    {
                        if(lsData.get(i).getDiemso() <= Integer.parseInt(scoreText.getText().toString())) {
                            relativeLayoutNewRecord.setVisibility(View.VISIBLE);
                            newplayer.setText(lsData.get(i).getTennguoichoi());
                            newhighscore.setText(score + "");
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
        levelText.setText(countLevel+"");
        scoreText.setText(score+"");
    }

    public void clickEvents() {
        //event activity good score
        fab_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                score=0;
                countLevel=1;
                Intent intent = new Intent(TimeMode.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        tv_playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                countLevel=1;

                Intent intent = new Intent(getApplicationContext(), TimeMode.class);
                startActivity(intent);
                finish();
            }
        });
        tv_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                countLevel=1;
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        fab_PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                countLevel=1;
                Intent intent = new Intent(getApplicationContext(), TimeMode.class);
                startActivity(intent);
                finish();
            }
        });

        //event activity bad score
        fab_PlayAgainTimeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                countLevel=1;
                Intent intent = new Intent(getApplicationContext(), TimeMode.class);
                startActivity(intent);
                finish();
            }
        });
        fab_HomeTimeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                score=0;
                countLevel=1;
                Intent intent = new Intent(TimeMode.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        tv_playagainTimeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                countLevel=1;
                Intent intent = new Intent(getApplicationContext(), TimeMode.class);
                startActivity(intent);
                finish();
            }
        });
        tv_gohomeTimeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                countLevel=1;
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
                Toast.makeText(TimeMode.this, "Cập nhật bảng điểm cao thành công!", Toast.LENGTH_LONG).show();
                score=0;
                countLevel=1;
                Intent intent = new Intent(TimeMode.this, DiemCaoNhat.class);
                startActivity(intent);

                finish();
            }
        });

        //event button in timemode lv1
        fab_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
                countLevel=1;
                Intent intent = new Intent(TimeMode.this, ChooseMode.class);
                startActivity(intent);
                finish();
            }
        });
        fab_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startStop();
                Intent intent = new Intent(TimeMode.this, PauseActivity.class);

                 //dừng chạy thời gian
                startActivity(intent);
            }
        });

        
        //======================XU LY CARD=======================

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

    }
    private void doStuff(ImageView iv, int card) {
        //set the correct image to the imageview
        if(cardsArray[card] == 101) {
            iv.setImageResource(image101);
        } else if(cardsArray[card] == 102) {
            iv.setImageResource(image102);
        } else if(cardsArray[card] == 201) {
            iv.setImageResource(image201);
        } else if(cardsArray[card] == 202) {
            iv.setImageResource(image202);
        } else if(cardsArray[card] == 1000) {
            iv.setImageResource(imagethuong1000);
        } else if(cardsArray[card] == 2000) {
            iv.setImageResource(imagethuong2000);
        }
        //check which image is selected and save it to temporary
        if(cardNumber == 1)
        {
            firstCard = cardsArray[card];
            if(firstCard > 1999)
            {
                firstCard = firstCard - 1000;
//                Toast.makeText(TimeMode.this, "gia tri card: " + firstCard, Toast.LENGTH_LONG).show();

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
//                Toast.makeText(TimeMode.this, "gia tri card: " + secondCard, Toast.LENGTH_LONG).show();

            }
            if (secondCard > 200 && secondCard < 1000)
            {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            iv11.setEnabled(false);
            iv12.setEnabled(false);

            iv21.setEnabled(false);
            iv22.setEnabled(false);

            iv31.setEnabled(false);
            iv32.setEnabled(false);

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
                iv21.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3) {
                iv22.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 4) {
                iv31.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5) {
                iv32.setVisibility(View.INVISIBLE);
            }

            if(clickedSecond == 0) {
                iv11.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1) {
                iv12.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2) {
                iv21.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3) {
                iv22.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 4) {
                iv31.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5) {
                iv32.setVisibility(View.INVISIBLE);
            }


            //xu ly su kien cộng thêm điểm vào score
            if(firstCard == 1000 && secondCard == 1000)
            {
                score+=200;
            }
            else {
                score+=100;
            }
            //Toast.makeText(TimeMode.this, "gia tri card: " + firstCard, Toast.LENGTH_LONG).show();
            scoreText.setText(score+"");

            //============Câu 3: thay đổi backgr màn chơi khi khi chơi được 300 điểm
            if(score>=300) {
                RelativeLayout layoutlv1 = findViewById(R.id.layoutlv1);
                layoutlv1.setBackgroundResource(R.color.backgrchange);
            }

        } else {
            iv11.setImageResource(R.drawable.ic_unknowed_img);
            iv12.setImageResource(R.drawable.ic_unknowed_img);

            iv21.setImageResource(R.drawable.ic_unknowed_img);
            iv22.setImageResource(R.drawable.ic_unknowed_img);

            iv31.setImageResource(R.drawable.ic_unknowed_img);
            iv32.setImageResource(R.drawable.ic_unknowed_img);

            //=============Câu 2: chọn sai 2 lần thì thua
            soLanLatThe++;
            if(soLanLatThe==2) {
                stopTimer();
                Toast.makeText(TimeMode.this, checkHaveNewRecord+"", Toast.LENGTH_SHORT).show();
                tv_timeup.setText("Thua cuộc!");
                int dem=0;
//                Toast.makeText(getApplicationContext(), "giatridiemcaomoi la:   "+ diemcaomoi, Toast.LENGTH_SHORT).show();

                if(checkHaveNewRecord == false) {
                    for(int i=0; i<lsData.size(); i++)
                    {
                        if(lsData.get(i).getDiemso() <= Integer.parseInt(scoreText.getText().toString())) {
                            relativeLayoutNewRecord.setVisibility(View.VISIBLE);
                            relativeLayoutTimeUp.setVisibility(View.INVISIBLE);
                            newplayer.setText(lsData.get(i).getTennguoichoi());
                            newhighscore.setText(score + "");
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

            //change the player turn
//            if(turn == 1) {
//                turn = 2;
//                tv_p1.setTextColor(Color.GRAY);
//                tv_p2.setTextColor(Color.BLACK);
//            }else if(turn == 2) {
//                turn = 1;
//                tv_p2.setTextColor(Color.GRAY);
//                tv_p1.setTextColor(Color.BLACK);
//            }

        }

        iv11.setEnabled(true);
        iv12.setEnabled(true);

        iv21.setEnabled(true);
        iv22.setEnabled(true);

        iv31.setEnabled(true);
        iv32.setEnabled(true);

        //check if the game is over
        checkEnd();
    }
    private void checkEnd() {
        if(iv11.getVisibility() == View.INVISIBLE &&
                iv12.getVisibility() == View.INVISIBLE &&
                iv21.getVisibility() == View.INVISIBLE &&
                iv22.getVisibility() == View.INVISIBLE &&
                iv31.getVisibility() == View.INVISIBLE &&
                iv32.getVisibility() == View.INVISIBLE) {

            //chuyển sang level 2
//            countLevel++;
//                setContentView(R.layout.time_mode_lv2);
                countLevel++;
                Intent intent = new Intent(TimeMode.this, TimeModeLV2.class);
                startActivity(intent);
        }
    }

}
