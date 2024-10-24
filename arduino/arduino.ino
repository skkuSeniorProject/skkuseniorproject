#include <NewPing.h>
#include <SoftwareSerial.h>

#define TRIGGER_CH1_PIN  5 //첫번째 Trigger pin
#define ECHO_CH1_PIN     4 //첫번째 Echo pin

#define TRIGGER_CH2_PIN  3 //두번째 Trigger pin
#define ECHO_CH2_PIN     2 //두번째 Echo Pin

#define MAX_DISTANCE 200   //최대 측정 거리 설정

//첫번째 NewPing 라이브러리 생성 (핀(TRIG, ECHO)과 최대 거리 설정)
NewPing sonar_ch1(TRIGGER_CH1_PIN, ECHO_CH1_PIN, MAX_DISTANCE); 
//두번째 NewPing 라이브러리 생성 (핀(TRIG, ECHO)과 최대 거리 설정)
NewPing sonar_ch2(TRIGGER_CH2_PIN, ECHO_CH2_PIN, MAX_DISTANCE);

SoftwareSerial HM10 (9, 8);

float default_distance = 86;
bool flag_front = 0, flag_rear = 0;

void setup() {
  //모니터 프로그램을 위한 시리얼 시작
  Serial.begin(9600);
  HM10.begin(9600);
  default_distance *= 0.7;
}

void loop() {
  //최소한 29ms이상은 대기를 해야한다고 함.
  //대기
  delay(120); 
  //첫번째 거리 읽기
  if(sonar_ch1.ping_cm() < default_distance) {
    if(flag_rear == 1) {
      flag_rear = 0;
      Serial.println("IN!!!!");
      HM10.write("up");
    }
    else
      flag_front = 1;
  }

  delay(120);

  //두번째 거리 읽기
  if(sonar_ch2.ping_cm() < default_distance) {
    if(flag_front == 1) {
      flag_front = 0;
      Serial.println("OUT!!!!");
      HM10.write("down");
    }
    else
      flag_rear = 1;
  }
  
}