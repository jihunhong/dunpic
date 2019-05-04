# 📜 네오플 오픈 API를 이용한 프로젝트 [![Build Status](https://travis-ci.org/jihunhong/dunpic.svg?branch=master)](https://travis-ci.org/jihunhong/dunpic)

## ❓ What is it?

힘 +100 짜리 무기가 있다고 치자,  이 무기의 능력치를 더해주는 시스템을 마법부여라고 한다.

마법부여를 하기 위해서는 **카드**가 필요한데 예시를 한번 살펴보자.

>+15 만큼의 성능을 내는 `1500만원`의 카드 A가 있다.

>+10 만큼의 성능을 내는 `50만원`의 카드 B가 있다.

초보자의 입장에서는 +5만큼의 성능을 위해 A를 골라 `1450만원`을 더 투자하기란 쉽지 않은 이야기다.


`그 이유는 +5만큼의 수치를 체감할수 있는 단계가 아니기 때문에 판단의 기준이 수치보다는 가격에 촛점이 맞춰져 있기 때문이다.`


결국, 합리적인 소비를 위해 각 아이템의 가격을 보며 가치를 따져보기 위해서는

엄청나게 많은 상품의 가격을 일일히 검색해보며 비교해봐야 하는 문제점이 존재했고,

![dunpic_cards](https://i.imgur.com/zdZSHjH.png)


이 점을 해결하기위해 모든 카드들의 부위별 옵션과 수치들을 담고있는 테이블을 만들어,

사용자의 선택을 통해 선택된 **특정 부위**에 **특정 옵션**을 가진

카드들을 보여주고 카드들의 가격도 한번에 볼수 있는 기능을 만들었다.

![enchant](https://i.imgur.com/u2adZ9y.gif)

<br>
## 📁 프로젝트에서 사용해본것들

![stack](https://i.imgur.com/1QnuNiV.png)

- Spring Boot
- Thymeleaf
- MariaDB
- Java Script
- AWS
- Neople Open API

## ⏳ 프로젝트 배포 FLOW
![그림1](https://i.imgur.com/JNb2Tsp.png)

사용중인 EC2서버에 몇가지 쉘스크립트를 작성하는데 처음하다보니 너무 어려웠다.

Nginx에 관한 학습도 필요하다고 생각된다.

`Travis CI`를 설정하는데 `AWS S3`와 `codedeploy` 를 연동?할수 있다는점이 편리했다.

## 🔗 Links

- [[DUNPIC.NET 개발기]](https://jihunhong.github.io/11/22/dunpic.net-%EA%B0%9C%EB%B0%9C%EA%B8%B0/)
- [[네오플 오픈 API]](https://developers.neople.co.kr/)
- [[개발자 블로그]](https://jihunhong.github.io/)
