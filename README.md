필요한 레포지토리를 만든 후 클론을 해서 사용하세요.

CONVENTION.md 파일을 꼭 읽어주세요.

# 모르는거 있으면 물어보세요 

~~모르면 제발 물어보세요 혼자해서 폴더구조 꼬이거나 하지 말고~~

> - 파일 위치
> - 특정 기능
> - 사용법

각 디렉토리 안에 있는 temp.md 파일은 삭제 후 코드를 작성하세요.

이 레포지토리는 muldum-back-v3 멀티 레포를 위한 템플릿 레포지토리입니다.

# 물듬(teamspace)

이 레포지토리는 팀 공간(Teamspace) 관리 기능을 담당합니다.  
팀 초대, 페이지 편집, 배너/아이콘 업데이트 등 팀스페이스에 필요한 관리 기능과 로그를 제공합니다.

## API 명세서
- GET /ara/teamspace
  - 설명: 팀스페이스에 노출될 팀 목록과 구성원을 조회합니다.
  - 응답: `TeamSpaceResponse`
- GET /sup/teamspace/logs?type={METHOD}
  - 설명: 슈퍼관리자가 팀스페이스 관련 로그를 확인합니다.
  - 응답: `TeamSpaceLogResponse[]`

## 프로젝트 개요
- Spring Boot 3.5, Java 21 기반의 Teamspace 서비스
- PostgreSQL을 메인 저장소로 사용하여 팀/멤버 정보를 관리
- RabbitMQ를 통해 다른 멀티레포 서비스와 비동기 통신 (팀 조회 요청/응답)
- Clean Architecture 기반으로 Adapter / Application / Domain 레이어 분리

## RabbitMQ 연동 흐름
| 구성 요소 | 설명 |
| --- | --- |
| Exchange | `team.query.exchange` (Direct) |
| 요청 큐 | `team.query.queue` (`team.query.routing.key`) |
| 응답 큐 | `team.query.queue.response` (`team.query.response.routing.key`) |
| Request DTO | `TeamQueryRequest(userId, correlationId)` |
| Response DTO | `TeamQueryResponse(userId, teamId, correlationId, success, errorMessage)` |

1. 다른 서비스에서 `TeamQueryRequest` 메시지를 발행하면 `TeamQueryConsumer`가 메시지를 소비합니다.
2. `MemberJpaRepository`로 userId에 해당하는 팀을 조회합니다.
3. 결과를 `TeamQueryResponse`로 변환해 응답 큐로 전달하면 `TeamQueryResponseConsumer`가 처리합니다.
4. `TeamQueryProducer`는 correlationId 기반으로 대기 중인 `CompletableFuture`를 완료하여 비동기 호출부에 결과를 전달합니다.

## 로컬 개발 절차
1. `.env` 파일을 원하는 값으로 수정합니다 (`RABBITMQ_*`, `DB_*`).
2. `docker compose up -d`로 PostgreSQL과 RabbitMQ를 기동합니다.
3. `./gradlew bootRun` 또는 IDE에서 `MuldumApplication`을 실행합니다.
4. Swagger UI는 `http://localhost:8080/swagger-ui.html`에서 확인할 수 있습니다.

## 환경 변수
| 키 | 설명 | 기본값 |
| --- | --- | --- |
| `RABBITMQ_HOST` | RabbitMQ 호스트 | `localhost` |
| `RABBITMQ_PORT` | AMQP 포트 | `5672` |
| `RABBITMQ_USERNAME` | RabbitMQ 사용자 | `guest` |
| `RABBITMQ_PASSWORD` | RabbitMQ 비밀번호 | `guest` |
| `RABBITMQ_MANAGEMENT_PORT` | 관리 콘솔 포트 | `15672` |
| `RABBITMQ_CONTAINER_NAME` | Docker 컨테이너 이름 | `muldum-rabbit` |

**리드미를 꼭 작성해주세요**
