-- 顧客管理の「お住まいの場所」と、部位別の特徴を利用するためのDDLです。
ALTER TABLE m_customer_detail
    ADD COLUMN location varchar(100) NULL AFTER job;

CREATE TABLE m_customer_feature (
  customer_id varchar(20) NOT NULL,
  feature_no int NOT NULL,
  part varchar(50) NOT NULL,
  feature varchar(255) NOT NULL,
  delete_flag char(1) NOT NULL DEFAULT '0',
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  revision bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (customer_id, feature_no),
  CONSTRAINT fk_customer_feature_customer FOREIGN KEY (customer_id) REFERENCES m_customer (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
