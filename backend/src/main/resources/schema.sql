CREATE TABLE IF NOT EXISTS user (
    id INTEGER AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'GUEST') NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user_id PRIMARY KEY (id),
    CONSTRAINT uc_user_email UNIQUE (email)
)^;

CREATE TABLE IF NOT EXISTS finance_record (
    id INTEGER AUTO_INCREMENT NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(255) NOT NULL,
    finance_type ENUM('INCOME', 'EXPENSE') NOT NULL,
    category ENUM('HOUSE_RENT', 'UTILITY_BILL', 'DOMESTIC_GAS', 'GROCERIES', 'MEDICINE', 'ENTERTAINMENT', 'TRANSPORT', 'TELECOMMUNICATION', 'SPECIAL_EXPENSES', 'OTHER_EXPENSES', 'SPECIAL_INCOME', 'OTHER_INCOME') NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_finance_record_id PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS logbook (
    id INTEGER AUTO_INCREMENT NOT NULL,
    caption VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_logbook_id PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS user_logbook (
    user_id INTEGER NOT NULL,
    logbook_id INTEGER NOT NULL,
    CONSTRAINT pk_user_logbook_id PRIMARY KEY(user_id, logbook_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT fk_logbook_id FOREIGN KEY (logbook_id) REFERENCES logbook(id) ON DELETE CASCADE
)^;

CREATE TABLE IF NOT EXISTS logbook_frecord (
    logbook_id INTEGER NOT NULL,
    finance_record_id INTEGER NOT NULL,
    CONSTRAINT pk_logbook_frecord_id PRIMARY KEY (logbook_id, finance_record_id),
    CONSTRAINT fk_logbook_id_2 FOREIGN KEY (logbook_id) REFERENCES logbook(id) ON DELETE CASCADE,
    CONSTRAINT fk_finance_record_id FOREIGN KEY (finance_record_id) REFERENCES finance_record(id) ON DELETE CASCADE
)^;

DROP PROCEDURE IF EXISTS create_logbook_with_user^;

CREATE PROCEDURE create_logbook_with_user (
    IN p_user_id INTEGER,
    IN p_caption VARCHAR(255),
    IN p_description VARCHAR(255)
)
BEGIN
    DECLARE new_logbook_id INTEGER;

    INSERT INTO logbook (caption, description) VALUES (p_caption, p_description);
    SET new_logbook_id = LAST_INSERT_ID();

    INSERT INTO user_logbook (user_id, logbook_id) VALUES (p_user_id, new_logbook_id);
END^;

DROP PROCEDURE IF EXISTS create_finance_record_with_logbook^;

CREATE PROCEDURE create_finance_record_with_logbook (
    IN p_logbook_id INTEGER,
    IN p_date DATE,
    IN p_description VARCHAR(255),
    IN p_finance_type VARCHAR(10),
    IN p_category VARCHAR(20)
)
BEGIN
    DECLARE new_finance_record_id INTEGER;

    INSERT INTO finance_record (date, description, finance_type, category) VALUES (p_date, p_description, p_finance_type, p_category);
    SET new_finance_record_id = LAST_INSERT_ID();

    INSERT INTO logbook_frecord (logbook_id, finance_record_id) VALUES (p_logbook_id, new_finance_record_id);
END^;