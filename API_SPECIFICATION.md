# API 명세서

## 기본 URL
- 로컬 서버:`http://localhost:8080/api`
- 운영 서버: `https://companycolor.site/api2`

## 인증
- 로그인/회원가입을 제외한 엔드포인트는 JWT 인증이 필요합니다.
- 헤더에 Bearer 토큰 값을 포함해 요청을 보내시면 됩니다.
```
Authorization: Bearer <token>
```

## 엔드포인트

### 인증

#### 로그인
- **URL**: `/auth/login`
- **메소드**: POST
- **요청 본문**:
  ```json
  {
    "username": "문자열",
    "password": "문자열"
  }
  ```
- **응답**:
  ```json
  {
    "status": "success",
    "data": {
      "token": "문자열"
    },
    "message": null
  }
  ```
  
#### 회원가입
- **URL**: `/auth/signup`
- **메소드**: POST
- **요청 본문**:
  ```json
  {
    "userId": "문자열",
    "name": "문자열",
    "password": "문자열",
    "authority": "문자열"
  }
  ```
- **응답**:
  ```json
  {
    "status": "success",
    "data": {
      "id": "숫자",
      "userId": "문자열",
      "name": "문자열",
      "authority": "문자열"
    },
    "message": null
  }
  ```

#### 현재 사용자 정보 조회
- **URL**: `/auth/me`
- **메소드**: GET
- **인증**: 필요
- **응답**:
  ```json
  {
    "status": "success",
    "data": {
      "userId": "문자열",
      "name": "문자열",
      "authority": "문자열"
    },
    "message": null
  }
  ```

### 사용자 관리 (관리자 전용)

#### 모든 사용자 조회
- **URL**: `/admin/users`
- **메소드**: GET
- **인증**: 필요 (SYSTEM_ADMIN 역할)
- **쿼리 파라미터**:
    - `userId` (선택): 문자열
    - `name` (선택): 문자열
- **응답**:
  ```json
  {
    "status": "success",
    "data": [
      {
        "id": "숫자",
        "userId": "문자열",
        "name": "문자열",
        "authority": "문자열"
      }
    ],
    "message": null
  }
  ```

#### ID로 사용자 조회
- **URL**: `/admin/users/{id}`
- **메소드**: GET
- **인증**: 필요 (SYSTEM_ADMIN 역할)
- **응답**:
  ```json
  {
    "status": "success",
    "data": {
      "id": "숫자",
      "userId": "문자열",
      "name": "문자열",
      "authority": "문자열"
    },
    "message": null
  }
  ```

#### 사용자 생성
- **URL**: `/admin/users`
- **메소드**: POST
- **인증**: 필요 (SYSTEM_ADMIN 역할)
- **요청 본문**:
  ```json
  {
    "userId": "문자열",
    "name": "문자열",
    "password": "문자열",
    "authority": "문자열"
  }
  ```
- **응답**:
  ```json
  {
    "status": "success",
    "data": {
      "id": "숫자",
      "userId": "문자열",
      "name": "문자열",
      "authority": "문자열"
    },
    "message": null
  }
  ```

#### 사용자 정보 수정
- **URL**: `/admin/users/{id}`
- **메소드**: PUT
- **인증**: 필요 (SYSTEM_ADMIN 역할)
- **요청 본문**:
  ```json
  {
    "name": "문자열"
  }
  ```
- **응답**:
  ```json
  {
    "status": "success",
    "data": {
      "id": "숫자",
      "userId": "문자열",
      "name": "문자열",
      "authority": "문자열"
    },
    "message": null
  }
  ```

#### 사용자 삭제
- **URL**: `/admin/users/{id}`
- **메소드**: DELETE
- **인증**: 필요 (SYSTEM_ADMIN 역할)
- **응답**:
  ```json
  {
    "status": "success",
    "data": "숫자", // 삭제된 사용자의 ID
    "message": null
  }
  ```

### 오류 응답
모든 API 요청에서 오류가 발생할 경우, 다음과 같은 형식의 응답을 받게 됩니다:
```json
{
  "status": "error",
  "data": null,
  "message": "오류 메시지"
}
```

## 참고사항
- 인증이 필요한 엔드포인트에 인증 없이 접근할 경우 401 Unauthorized 오류가 발생합니다.
- 관리자 권한이 필요한 엔드포인트에 일반 사용자가 접근할 경우 403 Forbidden 오류가 발생합니다.