# apiserver
Springboot 기반의 Backend Restapi 서버를 구현했다.
크게 아래와 같은 특징을 갖는다.

1. OAuth 2.0 기반의 구글 계정 로그인 가능.
 - 로그인을 한 뒤 서버에서 제공하는 Dashboard를 조회할 수 있다.
 
2. 시계열 Data를 연동하여, 실시간성 Dashboard를 보여준다.
 - InfluxDB를 서버에서 연동해서, 실시간으로 Data를 조회해 내려준다.
