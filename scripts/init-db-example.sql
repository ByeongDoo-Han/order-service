-- ==================== 사용자 생성 ====================
CREATE USER IF NOT EXISTS 'username'@'%' IDENTIFIED BY 'password';

CREATE DATABASE IF NOT EXISTS sajeon;

-- ==================== 권한 부여 ====================
GRANT ALL PRIVILEGES ON sajeon.* TO 'username'@'%';

FLUSH PRIVILEGES;

-- 생성된 데이터베이스 확인
SHOW DATABASES;
