int trigPin = 5;
int echoPin = 4;

void setup()
{
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT); // 센서 Trig 핀
  pinMode(echoPin, INPUT);  // 센서 Echo 핀
}

void loop()
{
  digitalWrite(trigPin, HIGH);  // 센서에 Trig 신호 입력
  delayMicroseconds(10);  // 10us 정도 유지
  digitalWrite(trigPin, LOW);   // Trig 신호 off

  long duration = pulseIn(echoPin, HIGH);    // Echo pin: HIGH->Low 간격을 측정
  long distance = duration / 29 / 2;  // 거리(cm)로 변환

  Serial.print(distance);
  Serial.println(" cm");

  delay(200);
}