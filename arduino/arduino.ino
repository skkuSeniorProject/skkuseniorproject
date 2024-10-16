#include <NewPing.h>

#define TRIGGER_CH1_PIN  5 //첫번째 Trigger pin
#define ECHO_CH1_PIN     4 //첫번째 Echo pin

#define TRIGGER_CH2_PIN  3 //두번째 Trigger pin
#define ECHO_CH2_PIN     2 //두번째 Echo Pin

#define MAX_DISTANCE 200   //최대 측정 거리 설정

//첫번째 NewPing 라이브러리 생성 (핀(TRIG, ECHO)과 최대 거리 설정)
NewPing sonar_ch1(TRIGGER_CH1_PIN, ECHO_CH1_PIN, MAX_DISTANCE); 
//두번째 NewPing 라이브러리 생성 (핀(TRIG, ECHO)과 최대 거리 설정)
NewPing sonar_ch2(TRIGGER_CH2_PIN, ECHO_CH2_PIN, MAX_DISTANCE); 

float default_distance = 16;
bool flag_front = 0, flag_rear = 0;

void setup() {
  //모니터 프로그램을 위한 시리얼 시작
  Serial.begin(9600);
  default_distance *= 0.7;
}

void loop() {
  //최소한 29ms이상은 대기를 해야한다고 함.
  //대기
  delay(50); 
  //첫번째 거리 읽기
  //Serial.print("Ch1: ");
  //Serial.print(sonar_ch1.ping_cm()); // Send ping, get distance in cm and print result (0 = outside set distance range)
  //Serial.println("cm");

  if(sonar_ch1.ping_cm() < default_distance) {
    if(flag_rear == 1) {
      flag_rear = 0;
      Serial.println("IN!!!!");
    }
    else
      flag_front = 1;
  }

  //두번째 거리 읽기
  //Serial.print("Ch2: ");
  //Serial.print(sonar_ch2.ping_cm()); // Send ping, get distance in cm and print result (0 = outside set distance range)
  //Serial.println("cm");

  if(sonar_ch2.ping_cm() < default_distance) {
    if(flag_front == 1) {
      flag_front = 0;
      Serial.println("OUT!!!!");
    }
    else
      flag_rear = 1;
  }
  
}