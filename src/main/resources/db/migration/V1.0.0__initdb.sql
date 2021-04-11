--- common for all services ---
CREATE SEQUENCE HIBERNATE_SEQUENCE;

--- auth service tables ---
CREATE TABLE user_info (
  login VARCHAR(64) NOT NULL PRIMARY KEY,
  salt VARCHAR(8) NOT NULL,
  password VARCHAR(40) NOT NULL,
  roles VARCHAR(1024) NOT NULL
);

--- create default admin user / could not be created view registration api
INSERT INTO user_info (login, salt, password, roles) VALUES ('admin', '58368', 'b9079b8ec07d1a5e034fbd0dc3736b867a2a64f1', 'user,admin');

-- system account - no password, - just as a 
INSERT INTO user_info (login, salt, password, roles) VALUES ('system', '', '', '');

--- user profile service tables 
CREATE TABLE user_profile (
  userid VARCHAR(64) NOT NULL PRIMARY KEY,
  firstname VARCHAR(64) NOT NULL,
  lastname VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0
);

--- user account service tables
CREATE TABLE account (
  userid VARCHAR(64) NOT NULL PRIMARY KEY,
  amount DECIMAL(15,2) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0
);

--- create system account - for money charge from user
INSERT INTO account (userid, amount, version) VALUES ('system', 0, 0);
INSERT INTO account (userid, amount, version) VALUES ('admin', 0, 0);

--- event store
CREATE TABLE event (
  id SERIAL NOT NULL PRIMARY KEY,
  xreqid VARCHAR(36),
  externalid VARCHAR(36) NOT NULL,
  entity VARCHAR(128) NOT NULL,
  type VARCHAR(32) NOT NULL,
  data VARCHAR(4096),
  userid VARCHAR(64),
  creationdate TIMESTAMP NOT NULL
);

--- order service tables 

--- normalized structure for order with items 1:N
CREATE TABLE order_info (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  userid VARCHAR(64) NOT NULL,
  creationdate TIMESTAMP NOT NULL,
  description VARCHAR(1024),
  state VARCHAR(16) NOT NULL
);

CREATE TABLE order_item (
  orderid VARCHAR(36) NOT NULL,
  itemid VARCHAR(36) NOT NULL,
  itemname VARCHAR(128) NOT NULL,
  quantity INTEGER NOT NULL,
  price DECIMAL(15,2) NOT NULL,
  CONSTRAINT pk PRIMARY KEY (orderid, itemid),
  CONSTRAINT fk1 FOREIGN KEY (orderid) REFERENCES order_info (id) ON DELETE CASCADE
);

-- denormalized structure for order overview
CREATE TABLE order_overview (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  userid VARCHAR(64) NOT NULL,
  creationdate TIMESTAMP NOT NULL,
  description VARCHAR(1024),
  state VARCHAR(16) NOT NULL,
  price DECIMAL(15,2) NOT NULL
);


-- notification tables
CREATE TABLE notification (
  id SERIAL NOT NULL PRIMARY KEY,
  userid VARCHAR(36) NOT NULL,
  email VARCHAR(64) NOT NULL,
  subject VARCHAR(64) NOT NULL,
  body VARCHAR(1024) NOT NULL,
  createdat TIMESTAMP NOT NULL
);

-- transaction tables
CREATE TABLE transaction (
  id SERIAL NOT NULL PRIMARY KEY,
  state VARCHAR(16) NOT NULL,
  debitaccount VARCHAR(64) NOT NULL,
  creditaccount VARCHAR(64) NOT NULL,
  orderid VARCHAR(36) NOT NULL,
  amount DECIMAL(15,2) NOT NULL,
  createdat TIMESTAMP NOT NULL
);
