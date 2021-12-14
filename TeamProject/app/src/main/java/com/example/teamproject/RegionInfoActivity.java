package com.example.teamproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegionInfoActivity extends AppCompatActivity {
    public static String[] ratings;
    TextView regionName;
    TextView desc;
    TextView ratingValue;
    ImageView sampleImg01;
    ImageView sampleImg02;
    Button ratingButton;
    RatingBar ratingBar;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        setTitle(name);
        float rating = ReadRatingFile(name + " rating");

        regionName = findViewById(R.id.Name);
        desc = findViewById(R.id.Description);
        ratingValue = findViewById(R.id.ratingValue);
        sampleImg01 = findViewById(R.id.sampleImg01);
        sampleImg02 = findViewById(R.id.sampleImg02);
        ratingButton = findViewById(R.id.ratingButton);
        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setRating(rating);
        ratingValue.setText(rating + "/5.0");

        TextView link = findViewById(R.id.Link);

        setTextRegion(name);

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    Intent reviewIntent = new Intent(getApplicationContext(), ReviewActivity.class);
                    reviewIntent.putExtra("destination", name);
                    startActivity(reviewIntent);
                }
                return true;
            }
        });

        Map<String, String> urls = new HashMap<String, String>();
        setUrls(urls);
        link.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("QueryPermissionsNeeded")
            public void onClick(View view){
                Intent urlIntent;
                Map<String, String> urls = new HashMap<String, String>();
                urlIntent = getUrls(urls, name);
                if(urlIntent.resolveActivity(getPackageManager()) != null) startActivity(urlIntent);
            }
        });

        TextView morePictures = findViewById(R.id.morePictures);
        morePictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gridLayoutIntent = new Intent(getApplicationContext(), GridLayoutActivity.class);
                startActivity(gridLayoutIntent);
            }
        });

        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ratingIntent = new Intent(getApplicationContext(), RatingActivity.class);
                ratingIntent.putExtra("destination", name);
                startActivity(ratingIntent);
            }
        });
    }

    @SuppressLint("SdCardPath")
    float ReadRatingFile(String fn){
        File fp = new File("/data/data/com.example.teamproject/files" + "/" + fn);
        if(!fp.exists()) return 0.0f;

        String txt = "";

        try{
            FileInputStream fis = openFileInput(fn);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            txt = new String(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ratings = txt.split("\n");
        return GetAvg(ratings);
    }

    float GetAvg(String[] ratings){
        float sum = 0.0f;
        float avg;

        for (String rating : ratings) {
            sum += Float.parseFloat(rating);
        }
        avg = sum/ratings.length;
        return avg;
    }

    Intent getUrls(Map<String, String> urls, String name){
        setUrls(urls);
        Intent urlIntent;
        urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(name)));
        urls.clear();
        return urlIntent;
    }

    void setUrls(Map<String, String> urls){
        String txt = "";
        String[] urlText;
        int line;
        InputStream inputStream = getResources().openRawResource(R.raw.urls);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try{
            line = inputStream.read();
            while(line != -1){
                byteArrayOutputStream.write(line);
                line = inputStream.read();
            }
            txt = byteArrayOutputStream.toString("UTF-8");
            inputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        urlText = txt.split("\n");
        for (String s : urlText) {
            urls.put(s.split("-")[0], s.split("-")[1]);
        }
    }

    @SuppressLint("SetTextI18n")
    void setTextSeoul(String name){
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
    void setTextGyeongGi(String name){
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
    void setTextChungcheong(String name){
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
    void setTextGangwon(String name){
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
                    "표현한 작품들이 곳곳에 숨어 있다.");

            sampleImg01.setImageResource(R.drawable.sangdomun01);
            sampleImg02.setImageResource(R.drawable.sangdomun02);
        }
    }

    @SuppressLint("SetTextI18n")
    void setTextJeolla(String name){
        if(name.equals("강천산 단월야행")){
            desc.setText("강천산 단월야행은 단풍이 아름답기로 소문난\n" +
                    "강천산에서 2018년 8월에 시작했다.\n" +
                    "한글 소설 <설공찬전>의 내용을 기본으로\n" +
                    "야행 테마를 부여한 이 야행은 단월문광장을 지나\n" +
                    "구름계곡, 신비의강, 빛의정원, 달빛궁궐 등\n" +
                    "소설 속 주제 장면을 영상과 조명으로 구현한다.");

            sampleImg01.setImageResource(R.drawable.gangcheon01);
            sampleImg02.setImageResource(R.drawable.gangcheon02);
        }
        if(name.equals("선유도")){
            desc.setText("전북 군산에 위치한 섬으로\n" +
                    "선유팔경이라는 유명한 관광자원이 있다.\n" +
                    "1경 망주 폭포, 2경 명사 십리 해수욕장, 3경 평사 낙안 등\n" +
                    "8개의 아름다운 경치를 나타낸다. 특히 백사장 건너편의\n" +
                    "망주봉은 옛날 유배온 충신이 매일 산봉우리에 올라\n" +
                    "한양땅을 바라보며 임금을 그리워했다하여 붙여진 이름이다.");

            sampleImg01.setImageResource(R.drawable.seonyu01);
            sampleImg02.setImageResource(R.drawable.seonyu02);
        }
        if(name.equals("채석강")){
            desc.setText("전북 부안 변산반도 맨 서쪽에 있는 바닷가로\n" +
                    "지형은 선캄브리아대의 화강암, 편마암을 기저층으로\n" +
                    "중생대 백악기의 지층이다. 바닷물에 침식되어\n" +
                    "퇴적한 절벽이 마치 수만 권의 책을 쌓아놓은 듯하다.\n" +
                    "여름철에는 해수욕을 즐기기 좋고 빼어난 경관 때문에\n" +
                    "사진 촬영이나 영화 촬영도 자주 이루어진다.");

            sampleImg01.setImageResource(R.drawable.chaesuk01);
            sampleImg02.setImageResource(R.drawable.chaesuk02);
        }
        if(name.equals("황룡강 생태공원")){
            desc.setText("장성 여행에서 꼭 가봐야 할 관광지인 황룡강 생태공원은\n" +
                    "황룡강 줄기를 따라 꽃밭과 산책로가 잘 조성되어 있다.\n" +
                    "강과 어우러진 꽃 군락지 감상을 위해 많은 관광객이 찾는다.\n" +
                    "우리나라에서 가장 길고 아름다운 꽃 정원이 조성되어\n" +
                    "편안한 휴식을 즐기며 화려한 꽃을 감상할 수 있다.");

            sampleImg01.setImageResource(R.drawable.hwangrong01);
            sampleImg02.setImageResource(R.drawable.hwangrong02);
        }
        if(name.equals("순천만 습지")){
            desc.setText("순천만은 우리나라 남해안 중서부에 위치한 만으로서,\n" +
                    "간조시에 드러나는 갯벌의 면적만해도 12㎢에 달하며,\n" +
                    "전체 갯벌의 면적은 22.6㎢ 나 된다.\n" +
                    "또한 천의 동천과 이사천의 합류 지점으로부터\n" +
                    "순천만의 갯벌 앞부분까지에는 총면적 5.4㎢에 달하는\n" +
                    "거대한 갈대 군락이 펼쳐져 있다.");

            sampleImg01.setImageResource(R.drawable.suncheon01);
            sampleImg02.setImageResource(R.drawable.suncheon02);
        }
        if(name.equals("화순 세량지")){
            desc.setText("전남 화순에 위치한 저수지로 세량제라고도 한다.\n" +
                    "봄이면 연분홍빛으로 피어나는 산벚꽃과 초록의 나무들이\n" +
                    "수면 위에 그대로 투영되는데,\n" +
                    "햇살이 비칠 무렵 피어오르는 물안개와 어우러져\n" +
                    "이국적 풍광을 빚어낸다. 또 가을이면 단풍으로 물든 산과\n" +
                    "어울려 경관이 아름답다.");

            sampleImg01.setImageResource(R.drawable.hwasun01);
            sampleImg02.setImageResource(R.drawable.hwasun02);
        }
    }

    @SuppressLint("SetTextI18n")
    void setTextJeju(String name) {
        if (name.equals("사려니숲길")) {
            desc.setText("사려니오름까지 이어지는 숲길\n" +
                    "울창한 자연림이 넓게 펼쳐져 있고,\n" +
                    "다양한 포유류와 조류 파충류 등\n" +
                    "다양한 동식물이 서식중이다.\n" +
                    "청정한 공기를 마시며 이 숲길을 걸으면\n" +
                    "스트레스 해소에 좋고 장과 심폐 기능이 향상된다고 한다.");

            sampleImg01.setImageResource(R.drawable.saryeoni01);
            sampleImg02.setImageResource(R.drawable.saryeoni02);
        }
        if (name.equals("성산일출봉")) {
            desc.setText("과거 해저 화산폭발로 인해 만들어진 성산일출봉은\n" +
                    "푸른 바다 사이에 우뚝 솟은 성채와 같은 모양\n" +
                    "봉우리 정상에 있는 거대한 사발 모양의 분화구\n" +
                    "그리고 그 위에서 맞이하는 일출의 장관 때문에\n" +
                    "성산일출봉은 많은 사람들의 감흥과 탄성을 자아낸다.");

            sampleImg01.setImageResource(R.drawable.seongsan01);
            sampleImg02.setImageResource(R.drawable.seongsan02);
        }
        if (name.equals("섭지코지")) {
            desc.setText("코의 끄트리 모양 비죽 튀어나온 지형이다.\n" +
                    "유채꽃이 한 4월 제철을 맞은 여기 넓은 들한복판에 서면\n" +
                    "자신도 모르는 사이, 어느새 영화속의 주인공이\n" +
                    "된듯한 착각에 빠질 수도 있을 것이다. 유채밭 사이를\n" +
                    "거닐면서 섭지코지의 해안 절경과 눈앞에 보이는 거대한\n" +
                    "코끼리 모양의 성산 일출봉의 장관을 함께 마주하는 것이다.");

            sampleImg01.setImageResource(R.drawable.cogee01);
            sampleImg02.setImageResource(R.drawable.cogee02);
        }
    }

    @SuppressLint("SetTextI18n")
    void setTextGyeongsang(String name) {
        if (name.equals("호미반도 해안둘레길")) {
            desc.setText("우리나라 육지 가장 동쪽 지역으로 영일만을 끼고\n" +
                    "동쪽으로 이어진 트래킹코스이다.\n" +
                    "해병대상륙훈련장을 시작으로\n" +
                    "호미곶까지 이어진 총 4개 코스이며 총 길이는 25km이다.\n" +
                    "각 코스는 1코스 연오랑 세오녀길, 2코스 선바우길\n" +
                    "3코스 구룡소길, 마지막 4코스 호미길이다.");

            sampleImg01.setImageResource(R.drawable.homi01);
            sampleImg02.setImageResource(R.drawable.homi02);
        }
        if (name.equals("국제 밤하늘 보호공원")) {
            desc.setText("영양군 수비면 수하계곡 왕피천 유역\n" +
                    "자연경관보존지구 일부 지역을 포함한\n" +
                    "반딧불이 생태공원 일대이다.\n" +
                    "밤하늘협회가 아시아 최초로 세계에서\n" +
                    "별빛이 밝은 밤하늘을 갖고 있는 지역을\n" +
                    "국제밤하늘보호공원으로 지었다.\n");

            sampleImg01.setImageResource(R.drawable.nightsky01);
            sampleImg02.setImageResource(R.drawable.nightsky02);
        }
        if (name.equals("곤륜산 활공장")) {
            desc.setText("넓은 평지에 인조잔디가 깔려 있는 패러글라이딩 활공장으로\n" +
                    "정상까지는 약 20분 정도 소요가 된다.\n" +
                    "굔륜산 활공장은 포항의 핫플레이스로\n" +
                    "활공장 정상에서 보이는 탁 트인 경치를 배경으로\n" +
                    "인생 샷을 남기기 위해 많은 관광객들이 찾고있다");

            sampleImg01.setImageResource(R.drawable.gliding01);
            sampleImg02.setImageResource(R.drawable.gliding02);
        }
        if (name.equals("보물섬 전망대")) {
            desc.setText("대한민국 최남단 '남해'를 아름답게 비추는\n" +
                    "등대의 모습을 형상화한 건축물이다.\n" +
                    "내부에서 바라보는 바다의 모습은\n" +
                    "초호화 고급 크루즈를 탄 듯한 국내에서는 찾아보기 힘든\n" +
                    "360° 파노라마 바다 조망을 자랑하고 있다.");

            sampleImg01.setImageResource(R.drawable.skywalk01);
            sampleImg02.setImageResource(R.drawable.skywalk02);
        }
        if (name.equals("바람의 언덕")) {
            desc.setText("잔디로 이루어진 민둥산이며\n" +
                    "바다가 시원스레 바라다 보이는 전망이 좋은 곳이다.\n" +
                    "TV드라마 이브의 화원, 회전목마가 방영되면서\n" +
                    "많은 관광객이 찾게 되었고, '바람의언덕'이란 지명도\n" +
                    "최근에 이 지역을 사랑하는 이들에게서\n" +
                    "생겨난 것으로 여겨진다.");

            sampleImg01.setImageResource(R.drawable.hillofwind01);
            sampleImg02.setImageResource(R.drawable.hillofwind02);
        }
    }

    @SuppressLint("SetTextI18n")
    void setTextETC(String name) {
        if (name.equals("인천 굴업도")) {
            desc.setText("민어 파시가 열려 불야성을 이루던 곳\n" +
                    "땅콩농사와 목축으로 근근이 살아가던 외딴 섬\n" +
                    "우리나라 유인도 가운데 원형이 가장 잘 보존된\n" +
                    "섬으로 꼽히는 굴업도는 최근 섬의 일부가\n" +
                    "천연기념물로 지정이 예고되면서 거센 조류와 파도, 바람이\n" +
                    "빚어낸 독특한 해안지형이 주목받고 있다.");

            sampleImg01.setImageResource(R.drawable.gureop01);
            sampleImg02.setImageResource(R.drawable.gureop02);
        }
        if (name.equals("대전 상소동 산림욕장")) {
            desc.setText("만인산과 식장산 자락 중간지점에 위치해 있으며\n" +
                    "가는 길에는 버즘나무 가로수 터널이\n" +
                    "아름다움을 더해주고 있다. 자연체험과 휴양을\n" +
                    "할 수 있는 각종 시설이 조성되어 있고,\n" +
                    "특히 수많은 돌탑이 조성되어 있어 볼거리를 제공하고 있다.");

            sampleImg01.setImageResource(R.drawable.sangsodong01);
            sampleImg02.setImageResource(R.drawable.sangsodong02);
        }
        if (name.equals("대구 달성공원")) {
            desc.setText("대구의 여러 공원 가운데 가장 오래되고 시민과 친근한 공원\n" +
                    "달성공원 안에는 지방문화재 자료인 관풍루가 있다.\n" +
                    "달성공원은 삼한시대의 부족국가였던\n" +
                    "달구벌의 성지 토성이었다. 또한 추가적으로\n" +
                    "잔디광장, 종합문화관 외에\n" +
                    "이상화 시비 등과 같은 기념물도 있다.");

            sampleImg01.setImageResource(R.drawable.dalseong01);
            sampleImg02.setImageResource(R.drawable.dalseong02);
        }
        if (name.equals("광주 지산유원지")) {
            desc.setText("무등산 향로봉 기슭에 자리한 총 34만 5천여 평의 유원지\n" +
                    "향로봉에는 전망대인 팔각정이 있어\n" +
                    "시내 전체를 내려다볼 수 있으며\n" +
                    "전망대와 유원지에는 리프트카가 운행된다.\n" +
                    "오락시설과 온천욕, 어린이 놀이시설도 많아\n" +
                    "가족 단위의 관광객이 많이 찾는다");

            sampleImg01.setImageResource(R.drawable.jisan01);
            sampleImg02.setImageResource(R.drawable.jisan02);
        }
        if (name.equals("부산 용소 웰빙공원")) {
            desc.setText("중앙에 호수가 있고 주변은 산으로 둘러싸인 공원\n" +
                    "호안부에는 수생 식물을 식재하고, 어린이를 위한\n" +
                    "생태 학습장을 조성하였으며, 친환경 소재인\n" +
                    "목재를 이용한 데크 로드를 설치하는 등\n" +
                    "자연 친화적인 생태 공원으로 만들어졌다.");

            sampleImg01.setImageResource(R.drawable.wellbeing01);
            sampleImg02.setImageResource(R.drawable.wellbeing02);
        }
        if (name.equals("울산 슬도")) {
            desc.setText("방어진항의 끝 어촌 마을 동진포구 바다에 위치한 섬\n" +
                    "섬의 대부분이 사암으로 이루어져 있다.\n" +
                    "바위에 촘촘히 나 있는 구멍들은 모래가 굳어진 바위에\n" +
                    "조개류 등이 파고 들어가 살면서 생긴 것으로 보이며\n" +
                    "슬도 주변으로 바다의 수심이 낮고 작은 암초들이 산재해 있다.");

            sampleImg01.setImageResource(R.drawable.seuldo01);
            sampleImg02.setImageResource(R.drawable.seuldo02);
        }
    }

    void setTextRegion(String name){
        regionName.setText(name);
        setTextSeoul(name);
        setTextGyeongGi(name);
        setTextChungcheong(name);
        setTextGangwon(name);
        setTextJeolla(name);
        setTextJeju(name);
        setTextGyeongsang(name);
        setTextETC(name);
    }
}