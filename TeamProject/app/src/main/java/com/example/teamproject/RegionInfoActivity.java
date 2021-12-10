package com.example.teamproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class RegionInfoActivity extends AppCompatActivity {
    public TextView regionName;
    public TextView desc;
    public ImageView sampleImg01;
    public ImageView sampleImg02;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        setTitle(name);

        regionName = findViewById(R.id.Name);
        desc = findViewById(R.id.Description);
        sampleImg01 = findViewById(R.id.sampleImg01);
        sampleImg02 = findViewById(R.id.sampleImg02);

        TextView link = findViewById(R.id.Link);

        setTextRegion(name);

        link.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent urlIntent;
                Map<String, String> urls = new HashMap<String, String>();
                urlIntent = setUrls(urls, name);
                if(urlIntent.resolveActivity(getPackageManager()) != null) startActivity(urlIntent);
            }
        });

        TextView morePictures = findViewById(R.id.morePictures);
        morePictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gridLayoutIntent = new Intent(getApplicationContext() , GridLayoutActivity.class);
                startActivity(gridLayoutIntent);
            }
        });
    }

    public Intent setUrls(Map<String, String> urls, String name){
        urls.put("서울숲", "https://www.youtube.com/watch?v=fTrdYm_h-3Q");
        urls.put("하늘공원", "https://www.youtube.com/watch?v=r-yZuLO80xw");
        urls.put("낙산공원", "https://www.youtube.com/watch?v=t0z4dPgiiU8");

        urls.put("대부도 바다향기 수목원", "https://www.youtube.com/watch?v=75Kwscgnk08");
        urls.put("평강랜드", "https://www.youtube.com/watch?v=RHnZvMxx3i8");
        urls.put("물의정원", "https://www.youtube.com/watch?v=e0yoAjvV2mg");

        urls.put("구담봉", "https://www.youtube.com/watch?v=0XjdXSESy-w");
        urls.put("옥순봉", "https://www.youtube.com/watch?v=oJRjhgOZGtY");
        urls.put("단양강 잔도", "https://www.youtube.com/watch?v=oJF1mrIvPDQ");
        urls.put("제천 의림지와 제림", "https://www.youtube.com/watch?v=QlDCkdrK1GA");
        urls.put("예당호 출렁다리", "https://www.youtube.com/watch?v=ZatPegMr-xk");
        urls.put("태안 빛축제", "https://www.youtube.com/watch?v=Vsue18ClBGw");
        
        urls.put("원대리 자작나무 숲", "https://www.youtube.com/watch?v=VWoPSbVl-nA");
        urls.put("곰배령", "https://www.youtube.com/watch?v=-jOeYIvVHxs");
        urls.put("상도문 돌담마을", "https://www.youtube.com/watch?v=rekQ_XMCUxM");

        Intent urlIntent;
        urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(name)));
        return urlIntent;
    }

    @SuppressLint("SetTextI18n")
    public void setTextSeoul(String name){
        if(name.equals("서울숲")){
            desc.setText("'스스로 탑험하며 자연에 가까이 다가가는 시간'\n" +
                    "서울숲은 문화예술공원, 체험학습원, 생태숲, 습지생태원\n" +
                    "네 가지의 특색 있는 공간들로 구성되어 있으며\n" +
                    "한강과 맞닿아 있어 다양한 문화여가공간을 제공합니다.");

            sampleImg01.setImageResource(R.drawable.seoulforest01);
            sampleImg02.setImageResource(R.drawable.seoulforest02);
        }
        if(name.equals("낙산공원")){
            desc.setText("수도 서울을 구성하는 내사산의 하나이자\n" +
                    "주산인 북악산의 좌청룡에 해당하는\n" +
                    "낙산의 자연환경과 역사적 문화환경을 복원함으로써\n" +
                    "서울시민들에게 쾌적환 공원 경관을 제공합니다.");

            sampleImg01.setImageResource(R.drawable.naksan01);
            sampleImg02.setImageResource(R.drawable.naksan02);
        }
        if(name.equals("하늘공원")){
            desc.setText("도시의 생활폐기물로 오염된 난지도 쓰레기매립장을\n" +
                    "자연생태계로 복원하기 위해\n" +
                    "1999년 10월부터 사업에 들어가 2002년 5월 1일 개원하였다.\n" +
                    "평화공원·난지천공원·난지한강공원·노을공원과 함께\n" +
                    "월드컵경기장 주변의 5대 공원을 이룬다.");

            sampleImg01.setImageResource(R.drawable.skypark01);
            sampleImg02.setImageResource(R.drawable.skypark02);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setTextGyeongGi(String name){
        if(name.equals("대부도 바다향기 수목원")){
            desc.setText("면적은 총 30만평(101만㎡)이며\n" +
                    "수려한 서해안 경관을 전망할 수 있는\n" +
                    "'상상전망대'를 비롯한 바다너울원, 암석원, 장미원 등\n" +
                    "특색있는 여러 주제원은 물론\n" +
                    "백합쉼터와 소공연장 등 다양한 휴양공간이 마련되어 있다.");

            sampleImg01.setImageResource(R.drawable.oceanscent01);
            sampleImg02.setImageResource(R.drawable.oceanscent02);
        }
        if(name.equals("평강랜드")){
            desc.setText("평강랜드는 13개 테마 정원과\n" +
                    "국내 최초 암석원을 보유한 평강식물원과\n" +
                    "북유럽풍 아이들 놀이터 어드벤처 파크 등\n" +
                    "체험교육이 함께운영되고 있다.");

            sampleImg01.setImageResource(R.drawable.pyeongang01);
            sampleImg02.setImageResource(R.drawable.pyeongang02);
        }
        if(name.equals("물의정원")){
            desc.setText("남양주 물의 정원은 국토교통부가\n" +
                    "2012년 한강 살리기 사업으로 조성한\n" +
                    "484,188㎡의 광대한 면적의 수변생태공원으로 조성되었다.\n" +
                    "물의정원은 자연과 소통하여\n" +
                    "몸과 마음을 정화하고 치유하는 자연친화적 휴식공간이다.");

            sampleImg01.setImageResource(R.drawable.gardenofwater01);
            sampleImg02.setImageResource(R.drawable.gardenofwater02);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setTextChungcheong(String name){
        if(name.equals("구담봉")){
            desc.setText("단양팔경에 속하는 산으로 \n" +
                    "물 속에 비친 바위가 거북 무늬를 띠고 있어 붙여진 이름이다.\n" +
                    "높이는 330m이며 명승 제 46호로 지정되었다.\n" +
                    "충주시에서 단양읍을 향해 가다 보면\n" +
                    "거북 한 마리가 뭍으로 올라가는 듯한 형상의 이 산이 보인다.");

            sampleImg01.setImageResource(R.drawable.goodam01);
            sampleImg02.setImageResource(R.drawable.goodam02);
        }
        if(name.equals("옥순봉")){
            desc.setText("기암으로 이루어진 봉우리의 경관이 뛰어나\n" +
                    "소금강이라고도 하며 2008년 9월 9일에\n" +
                    "명승 제48호로 지정되었다.\n" +
                    "희고 푸른 여러 개의 봉우리가 마치\n" +
                    "대나무 싹과 같다고 하여 옥순봉이라고 이름붙였다.");

            sampleImg01.setImageResource(R.drawable.oksun01);
            sampleImg02.setImageResource(R.drawable.oksun02);
        }
        if(name.equals("단양강 잔도")){
            desc.setText("총 길이 1.2km의 단양강 잔도길에는\n" +
                    "그동안 접근하기 어려웠던 남한강 암벽을 따라\n" +
                    "잔도가 있어 트래킹의 낭만과 짜릿한 스릴을\n" +
                    "야간관광 100선에 선정되어\n" +
                    "단양군을 체류형 관광도시로 이끌고 있는 곳이다.");

            sampleImg01.setImageResource(R.drawable.jando01);
            sampleImg02.setImageResource(R.drawable.jando02);
        }
        if(name.equals("제천 의림지와 제림")){
            desc.setText("의림지는 우리나라의 대표적인 수리시설 중의 하나로\n" +
                    "삼한시대부터 있었던 것으로 알려져 있다.\n" +
                    "신라 진흥왕 때 악성 우륵이 개울물을 막아\n" +
                    "개울물을 막아 둑을 쌓았다는 이야기가 전하고\n" +
                    "그로부터 700년 뒤 이곳에 온 현감 박의림이\n" +
                    "좀더 견고하게 새로 쌓은 것이라고도 한다.");

            sampleImg01.setImageResource(R.drawable.uirimji01);
            sampleImg02.setImageResource(R.drawable.uirimji02);
        }
        if(name.equals("예당호 출렁다리")){
            desc.setText("우리나라에서 가장 큰 저수지인 예당호에 위치한\n" +
                    "우리나라에서 가장 긴 출렁다리로\n" +
                    "성인 3150을 동시에 수용할 수 있다.\n" +
                    "하늘로 곧게 솟은 64m 주탑을 중심으로\n" +
                    "양 옆으로 펼쳐진 케이블은 아름다운 자태의\n" +
                    "거대한 황사게 길고 흰 날개를 펼친 모습이다.");

            sampleImg01.setImageResource(R.drawable.yedang01);
            sampleImg02.setImageResource(R.drawable.yedang02);
        }
        if(name.equals("태안 빛축제")){
            desc.setText("오천평 넓은 대지에서 펼쳐지는 밤의 축제\n" +
                    "어린이들에겐 환상적인 동화의 나라를\n" +
                    "어른이들에겐 낭만적인 테마의 섬을 선보일 이곳은\n" +
                    "365일이 아름다운 빛의 나라이다.\n");

            sampleImg01.setImageResource(R.drawable.lightfestival01);
            sampleImg02.setImageResource(R.drawable.lightfestival02);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setTextGangwon(String name){
        if(name.equals("원대리 자작나무 숲")){
            desc.setText("인제읍 인근의 자연 생태관광지인 원대리 자작나무 숲은\n" +
                    "1974년부터 1995년까지 138ha에 자작나무 690,000본을\n" +
                    "조림하여 만들어졌으며 현재는 그중 25ha를\n" +
                    "유아 숲 체험원으로 운영하고 있다.\n" +
                    "등산로를 따라 오르면 수령이 20년 이상 되는 자작나무가\n" +
                    "빽빽하게 찬 숲이 펼쳐진다.");

            sampleImg01.setImageResource(R.drawable.birchtree01);
            sampleImg02.setImageResource(R.drawable.birchtree02);
        }
        if(name.equals("곰배령")){
            desc.setText("천상의 화원이라고도 불리는 곰배령은\n" +
                    "원시림 끝에 점봉산을 넘는 부드러운 고개이다.\n" +
                    "이 고개에서 봄부터 여름까지 들꽃이 어울려\n" +
                    "한바탕 축제를 벌인다.\n" +
                    "상의 원시림을 거닐어 만나는 꽃대궐\n" +
                    "여름날의 행복한 추억으로 부족함이 없다.");

            sampleImg01.setImageResource(R.drawable.gombae01);
            sampleImg02.setImageResource(R.drawable.gombae02);
        }
        if(name.equals("상도문 돌담마을")){
            desc.setText("상도문 돌담마을은\n" +
                    "속초에서 설악산으로 들어가는 길에 위치한 예쁜 마을이다.\n" +
                    "미로처럼 펼쳐진 돌담길을 따라 걷다보면\n" +
                    "돌로 만든 다양한 미술작품을 만날 수 있다.\n" +
                    "고양이와 참새, 달팽이 등 마을의 고즈넉함을\n" +
                    "표현한 작품들이 곳곳에 숨어 있다.\n");

            sampleImg01.setImageResource(R.drawable.sangdomun01);
            sampleImg02.setImageResource(R.drawable.sangdomun02);
        }
    }

    public void setTextRegion(String name){
        regionName.setText(name);
        setTextSeoul(name);
        setTextGyeongGi(name);
        setTextChungcheong(name);
        setTextGangwon(name);
    }
}